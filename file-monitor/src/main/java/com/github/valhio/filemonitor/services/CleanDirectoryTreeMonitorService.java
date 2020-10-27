package com.github.valhio.filemonitor.services;

import com.github.valhio.filemonitor.exceptions.Exceptions;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import static java.nio.file.StandardWatchEventKinds.*;

@Service
public class CleanDirectoryTreeMonitorService extends MonitorService {

    CleanDirectoryTreeMonitorService() throws IOException {
        super();
    }

    @Override
    public void execute(Path path) {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    dir.register(getWatchService(), ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            getLogger().error(Exceptions.DIR_NOT_FOUND);
        }
        startWatcher();
    }

    @Override
    public void startWatcher() {
        boolean isValid = true;
        // Returns: true if the watch key is valid and has been reset, and false if the watch key could not be reset because it is no longer valid
        // valid: A watch key is valid upon creation and remains until it is cancelled, or its watch service is closed.
        // Basically it checks if the folder (Folder path) still exists. If it does not, it stops monitoring it.
        while (isValid) {
            getNextWatchKey();
            validateRootDirectory();

            List<WatchEvent<?>> events = new ArrayList<>(getWatchKey().pollEvents());

            for (WatchEvent<?> event : events) {
                logEventType(event);
                monitorIfDirectory(event);
            }

            isValid = getWatchKey().reset();
        }
    }
}
