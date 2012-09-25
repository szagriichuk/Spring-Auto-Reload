package com.datacradle.spring.autoreload;

/**
 * @author Sergii.Zagriichuk
 */
public class AutoreloadProperties {

    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "Autoreload{" +
                "test='" + test + '\'' +
                '}';
    }
}
