package com.datacradle.spring.autoreload;

/**
 * @author Sergii.Zagriichuk
 */
public class AutoReloadContext {
    private AutoreloadProperties autoreloadProperties;

    public void setProperties(AutoreloadProperties properties) {
        this.autoreloadProperties = properties;
    }

    public void printPropertiesToConsole() {
        System.out.println(autoreloadProperties.toString());
    }

}
