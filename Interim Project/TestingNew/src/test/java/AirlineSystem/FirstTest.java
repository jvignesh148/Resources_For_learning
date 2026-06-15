package AirlineSystem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.sql.SQLOutput;
import java.util.List;

public class FirstTest {

    public static  WebDriver driver;

    public static void main(String[] args) throws InterruptedException {

        driver = new EdgeDriver(); // lauch browser

        driver.get("http://localhost:4200/"); // open browser
        driver.manage().window().maximize();
        System.out.println(driver.getTitle()); // getting title
        driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/div[1]/input")).sendKeys("selvaganesh@gmail.com");

        driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/div[2]/input")).sendKeys("Selva@2004");

        driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/button[1]")).click();

        Thread.sleep(2000);

        driver.findElement(By.cssSelector("body > app-root > app-home-page > div > section.hero-section > div.hero-inner > div.search-card > form > div > div.field-group.from-field > input")).sendKeys("MAA");

        driver.findElement(By.cssSelector("body > app-root > app-home-page > div > section.hero-section > div.hero-inner > div.search-card > form > div > div.field-group.to-field > input")).sendKeys("BOM");
//        LoginPageTest.loginPage();
//        driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/button[2]")).click();
//        driver.findElement(By.linkText("ABOUT US")).click();
//        List<WebElement> elements = driver.findElements(By.tagName("a"));
//        for(WebElement ele : elements) {
//            System.out.println(ele.getText());
//        }
    }
}
