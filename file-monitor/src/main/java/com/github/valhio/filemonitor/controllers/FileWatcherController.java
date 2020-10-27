package com.github.valhio.filemonitor.controllers;

import com.github.valhio.filemonitor.services.MonitorService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import java.nio.file.Path;

@Controller
@Slf4j
@Setter
@PropertySource("classpath:fileWatcher.properties")
public class FileWatcherController implements Runnable {
    private MonitorService monitorService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${directory.path}")
    private String directoryPath;

    @Autowired
    private Environment env;

    public void run() {
        logger.info(String.format("Monitored directory: %s", directoryPath));

        monitorService.execute(Path.of(env.getProperty("directory.path")));
    }

    public void stop(){
        monitorService.stop();
    }
}
