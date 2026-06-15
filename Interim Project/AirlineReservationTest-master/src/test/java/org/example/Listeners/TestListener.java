package org.example.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = "reports/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setReportName("Airline App Test Report");
        spark.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", "Microsoft Edge");
        extent.setSystemInfo("Tester", "Vignesh");

        System.out.println("Test Suite Started: " + context.getName());
    }


    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        System.out.println("Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
        System.out.println("✅ PASSED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());
        WebDriver driver = getDriver(result);
        if (driver != null) {
            String screenshotPath = takeScreenshot(driver, result.getMethod().getMethodName());
            try {
                test.get().addScreenCaptureFromPath(screenshotPath,
                        "Screenshot on Failure");
            } catch (Exception e) {
                System.out.println("Could not attach screenshot: " + e.getMessage());
            }
        }
        System.out.println("❌ FAILED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
        System.out.println("⚠️ SKIPPED: " + result.getMethod().getMethodName());
    }


    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        System.out.println("Test Suite Finished: " + context.getName());
        System.out.println("Total Passed : " + context.getPassedTests().size());
        System.out.println("Total Failed : " + context.getFailedTests().size());
        System.out.println("Total Skipped: " + context.getSkippedTests().size());
    }

    private WebDriver getDriver(ITestResult result) {
        try {
            Object testInstance = result.getInstance();
            Class<?> clazz = testInstance.getClass();

            while (clazz != null) {
                try {
                    Field driverField = clazz.getDeclaredField("driver");
                    driverField.setAccessible(true);
                    return (WebDriver) driverField.get(testInstance);
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }

            System.out.println("Driver field not found in class hierarchy");
            return null;

        } catch (Exception e) {
            System.out.println("Could not get driver: " + e.getMessage());
            return null;
        }
    }

    private String takeScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("HH-mm-ss").format(new Date());
        String path = "reports/screenshots/" + testName + "_" + timestamp + ".png";
        try {
            Files.createDirectories(Paths.get("reports/screenshots"));
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
            Files.write(Paths.get(path), screenshot);
        } catch (IOException e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
        return path;
    }
}