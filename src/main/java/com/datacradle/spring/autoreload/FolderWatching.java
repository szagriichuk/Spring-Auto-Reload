package com.datacradle.spring.autoreload;

import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * @author Sergii.Zagriichuk
 */
public class FolderWatching {

    private WatchService watcher;

    private Path lanchFile;

    private String watchFileName;

    private String pathToFolder;


    public void setPathToFolder(String pathToFolder) {
        this.pathToFolder = pathToFolder;
    }

    public void setWatchFileName(String watchFileName) {
        this.watchFileName = watchFileName;
    }

    private ConfigurableApplicationContext configurableApplicationContext;

    private AutoReloadEvent autoReloadEvent;

    public void setAutoReloadEvent(AutoReloadEvent autoReloadEvent) {
        this.autoReloadEvent = autoReloadEvent;
    }

    public void setConfigurableApplicationContext(ConfigurableApplicationContext configurableApplicationContext) {
        this.configurableApplicationContext = configurableApplicationContext;
    }

    @PostConstruct
    public void init() {

        lanchFile = Paths.get(pathToFolder);

        createWatchService();

        registerDirEvents();

    }

    private void registerDirEvents() {

        try {
            lanchFile.register(watcher, ENTRY_MODIFY, ENTRY_CREATE);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void createWatchService() {

        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    public void watchFolder() {

        boolean isExit;

        for (; ; ) {

            WatchKey key;

            try {
                key = watcher.take();
            } catch (InterruptedException x) {

                System.out.println(x.getMessage());

                break;
            }

            for (WatchEvent<?> event : key.pollEvents()) {

                WatchEvent.Kind<?> kind = event.kind();

                WatchEvent<Path> ev = (WatchEvent<Path>) event;

                Path filename = ev.context();


                if (kind == ENTRY_MODIFY && watchFileName.equals(filename.toFile().getName())) {

                    isExit = true;

                    try {
                        configurableApplicationContext.refresh();
                        autoReloadEvent.autoReloadDone();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                        isExit = false;
                    }

                    if (isExit)
                        break;
                }

            }

            boolean valid = key.reset();

            if (!valid) {
                break;
            }
        }
    }
}
