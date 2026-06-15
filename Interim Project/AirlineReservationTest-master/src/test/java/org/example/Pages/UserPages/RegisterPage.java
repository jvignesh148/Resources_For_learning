package org.example.Pages.UserPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.PageFactory;

public class RegisterPage {
    WebDriver driver;

    public RegisterPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@formcontrolname = 'firstName']")
    WebElement firstName;

    @FindBy(xpath = "//input[@formcontrolname = 'lastName']")
    WebElement lastName;

    @FindBy(xpath = "//input[@formcontrolname = 'email']")
    WebElement email;

    @FindBy(xpath = "//input[@type='tel' and @formcontrolname = 'phone']")
    WebElement phoneNo;

    @FindBy(xpath = "//input[@formcontrolname = 'password']")
    WebElement password;

    @FindBy(xpath = "//input[@formcontrolname = 'confirmPassword']")
    WebElement confirmPassword;

    @FindBy(xpath = "//button[@class='eye-btn']")
    WebElement showPassword;

    @FindBy(xpath = "//span[@class='custom-check']")
    WebElement agreeToTerms;

    @FindBy(xpath = "//button[@class='register-button']")
    WebElement signupButton;

    @FindBy(xpath = "//a[@class='signin-link']")
    WebElement loginLink;

    public void setFirstName(String name) {
        firstName.clear();
        firstName.sendKeys(name);
    }

    public void setLastName(String name) {
        lastName.clear();
        lastName.sendKeys(name);
    }

    public void setEmail(String mail) {
        email.clear();
        email.sendKeys(mail);
    }

    public void setPhone(String phone) {
        phoneNo.clear();
        phoneNo.sendKeys(phone);
    }

    public void setPassword(String pass) {
        password.clear();
        password.sendKeys(pass);
    }

    public void setConfirmPassword(String pass) {
        confirmPassword.clear();
        confirmPassword.sendKeys(pass);
    }

    public void checkAgreeTerms() {
            agreeToTerms.click();
            agreeToTerms.click();
            if(!agreeToTerms.isSelected()){
                agreeToTerms.click();
            }
    }

    public void clickRegister() {
        signupButton.click();
    }

    public void clickLoginLink() {
        loginLink.click();
    }

    public void clickShowPassword() {
        showPassword.click();
    }

    public void register(String fn, String ln, String mail,
                         String ph, String pass, String confirmPass) {
        setFirstName(fn);
        setLastName(ln);
        setEmail(mail);
        setPhone(ph);
        setPassword(pass);
        setConfirmPassword(confirmPass);
        checkAgreeTerms();
        clickRegister();
    }

    public boolean isPasswordVisible() {
        return password.getAttribute("type").equals("text");
    }
}
