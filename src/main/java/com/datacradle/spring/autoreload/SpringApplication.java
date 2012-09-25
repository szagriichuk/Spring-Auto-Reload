package com.datacradle.spring.autoreload;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author sergiizagriichuk
 * @version 0.1
 */
public abstract class SpringApplication {
    protected ConfigurableApplicationContext applicationContext;

    protected SpringApplication() {
        loadApplicationContext();
        injectDependencies();
    }

    protected ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Subclasses must override this method to return the locations of their
     * configuration files.
     *
     * @return array of config locations
     */
    protected abstract String[] getConfigLocations();

    /**
     * Loads application context. Override this method to change how the
     * application context is loaded.
     */
    protected void loadApplicationContext() {
        applicationContext = new ClassPathXmlApplicationContext(
                getConfigLocations());
        applicationContext.registerShutdownHook();
    }

    /**
     * Inject dependencies into 'this' instance. Override this method if you
     * need full control over how dependencies are injected.
     */
    protected void injectDependencies() {
        getApplicationContext().getBeanFactory().autowireBeanProperties(
                this, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
    }

    protected <T> T getBeanByNameForType(String name, Class<T> type) {
        return (T) getApplicationContext().getBean(name);
    }
}
