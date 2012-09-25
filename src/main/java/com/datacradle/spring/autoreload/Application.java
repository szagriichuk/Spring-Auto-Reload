package com.datacradle.spring.autoreload;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class Application extends SpringApplication implements AutoReloadEvent {

    @Autowired
    private FolderWatching folderWatching;

    @Autowired
    private AutoReloadContext autoReloadContext;

    private ExecutorService executorService = Executors.newFixedThreadPool(3);


    public static void main(String[] args) {

        Application application = new Application();

        application.startWatchingFolder();

        application.autoReloadContext.printPropertiesToConsole();

    }

    private void startWatchingFolder() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
//                folderWatching = getBeanByNameForType("folderWatching", FolderWatching.class);
                folderWatching.setConfigurableApplicationContext(applicationContext);
                folderWatching.setAutoReloadEvent(Application.this);
                folderWatching.watchFolder();
            }
        });

    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"app-config.xml"};
    }

    @Override
    public void autoReloadDone() {
        autoReloadContext = getBeanByNameForType("autoReloadContext", AutoReloadContext.class);
        autoReloadContext.printPropertiesToConsole();
    }
}
