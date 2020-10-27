package com.github.valhio.filemonitor;

import com.github.valhio.filemonitor.controllers.FileWatcherController;
import com.github.valhio.filemonitor.services.CleanDirectoryTreeMonitorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

//todo: remove exclude when implementing database integration
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FileMonitorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(FileMonitorApplication.class, args);

        // Check README to see how the different Services work

        FileWatcherController fileWatcherController = run.getBean(FileWatcherController.class);
        fileWatcherController.setMonitorService(run.getBean(CleanDirectoryTreeMonitorService.class));
        fileWatcherController.run();

//        Thread thread = new Thread(fileWatcher);
//        thread.start();

    }

}
