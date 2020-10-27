package com.github.valhio.filemonitor.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Component
@Getter
@Slf4j
public abstract class MonitorService {
    private final WatchService watchService;
    private WatchKey watchKey;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${directory.path}")
    private String rootDirectory;

    protected MonitorService() throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
    }

    public abstract void execute(Path path);

    public abstract void startWatcher();

    protected void monitorIfDirectory(WatchEvent<?> event) {
        if (event.kind() == ENTRY_CREATE) {
            monitorDirectory(event);
        }
    }

    protected void monitorDirectory(WatchEvent<?> event) {
        Path path = getFullFilePath(event);
        if (Files.isDirectory(path)) {
            execute(path);
        }
    }

    protected void getNextWatchKey() {
        try {
            watchKey = watchService.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void validateRootDirectory() {
        if (!Files.isDirectory(Path.of(rootDirectory))) {
            log.error("Root folder deleted.");
            stop();
        }
    }

    protected void logEventType(WatchEvent<?> event) {
        var fileName = event.context();
        logger.warn("{} {} : {}\n", event.kind().name(), fileName, getFullFilePath(event));
    }


    private Path getFullFilePath(WatchEvent<?> e) {
        Path dir = getPathToFile();
        return dir.resolve(e.context().toString());
    }

    private Path getPathToFile() {
        return Path.of(String.valueOf(watchKey.watchable()));
    }

    public void stop() {
//        thread.interrupt();
        try {
            this.watchService.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
