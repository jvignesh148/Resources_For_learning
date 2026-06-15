package AirlineSystem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageTest {

    static WebDriver driver = FirstTest.driver;
    public static void loginPage() {
        driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/div[1]/input")).sendKeys("selvaganesh@gmail.com");

        driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/div[2]/input")).sendKeys("Selva@2004");

        driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/button[1]")).click();

    }
}
