package com.kodilla.bookapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class NewTitleTest {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\selenium-drivers\\Chrome\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://ta-ebookrental-fe.herokuapp.com/");

        Thread.sleep(2000);

        WebElement login = driver.findElement(By.id("login"));
        login.sendKeys("sylvia7sylvia");

        WebElement pwd = driver.findElement(By.name("password"));
        pwd.sendKeys("mojehaslo123");

        WebElement button = driver.findElement(By.id("login-btn"));
        button.click();

        Thread.sleep(2000);

        WebElement addNew = driver.findElement(By.xpath("//*[@name='add-title-button']"));
        addNew.click();

        driver.findElement(By.xpath("//input[@name='title']")).sendKeys("Harri Potter i Zakon Feniksa");

        driver.findElement(By.xpath("//input[@name='author']")).sendKeys("J.K.Rowling");

        driver.findElement(By.xpath("//input[@name='year']")).sendKeys("2003");

        WebElement addTitle = driver.findElement(By.xpath("//*[@name='submit-button']"));
        addTitle.click();
        //driver.close();

    }
}
