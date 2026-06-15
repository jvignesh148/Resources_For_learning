package org.example.Pages.UserPages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement username;

    @FindBy(xpath = "//input[@type='password']")
    WebElement password;

    @FindBy(css= "button[type='submit']")
    WebElement button;

    @FindBy(xpath = "//button[@class='register-button']")
    WebElement registerButton;

    public void setUsername(String user){
        username.clear();
        username.sendKeys(user);
    }

    public void setPassword(String pass){
        password.clear();
        password.sendKeys(pass);
    }

    public void clickButton(){
        button.click();
    }

    public void clickRegisterButton(){
        registerButton.click();
    }

}
