package com.github.valhio.filemonitor.services;

import com.github.valhio.filemonitor.exceptions.Exceptions;
import com.sun.nio.file.ExtendedWatchEventModifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

@Service
public class RawDirectoryTreeMonitorService extends MonitorService {


    protected RawDirectoryTreeMonitorService() throws IOException {
    }

    // registers all subdirectories of root directory
    // https://stackoverflow.com/questions/18701242/how-to-watch-a-folder-and-subfolders-for-changes
    @Override
    public void execute(final Path rootDirectory) {
        WatchEvent.Kind<?>[] standardWatchEventKinds =
                new WatchEvent.Kind<?>[]{ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE};

        try {
            Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    dir.register(getWatchService(), standardWatchEventKinds, ExtendedWatchEventModifier.FILE_TREE);
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
        getNextWatchKey();

        // Returns: true if the watch key is valid and has been reset, and false if the watch key could not be reset because it is no longer valid
        // valid: A watch key is valid upon creation and remains until it is cancelled, or its watch service is closed.
        // Basically it checks if the folder (Folder path) still exists. If it does not, it stops monitoring it.
        while (getWatchKey().reset()) {
            List<WatchEvent<?>> events = new ArrayList<>(getWatchKey().pollEvents());

            for (WatchEvent<?> event : events) {
                logEventType(event);
                monitorIfDirectory(event);
            }

            getNextWatchKey();
            validateRootDirectory();
        }
    }
}
