package com.kodilla.bookapp;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected WebDriver driver;

    protected String url;

    public BasePage(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
    }

    public void navigate(){
        this.driver.get(url);
    }
}
