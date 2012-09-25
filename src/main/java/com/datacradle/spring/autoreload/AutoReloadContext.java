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
//        autoreloadProperties = getBeanByNameForType("properties", AutoreloadProperties.class);
        System.out.println(autoreloadProperties.toString());
    }

}
