package com.kodilla.bookapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddNewUserTest {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\selenium-drivers\\Chrome\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        PO_Login po_login = new PO_Login(driver);
        po_login.navigate();
        driver.findElement(By.xpath("//button[@id='register-btn']")).click();

        WebElement log = driver.findElement(By.id("login"));
        log.sendKeys("Dawid");

        WebElement pwd = driver.findElement(By.name("password"));
        String p = "lightnir1984";
        pwd.sendKeys(p);

        WebElement pwdRep = driver.findElement(By.name("password-repeat"));
        pwdRep.sendKeys(p);

        WebElement signup = driver.findElement(By.id("register-btn"));
        signup.click();
    }
}
