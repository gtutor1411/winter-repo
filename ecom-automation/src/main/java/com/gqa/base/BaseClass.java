package com.gqa.base;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseClass {

	public static void takeSnapshot(WebDriver driver, String ssName, ExtentTest test) {
		try {
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileHandler.copy(file, new File(System.getProperty("user.dir") + "/Reports/" + ssName + ".png"));
			test.log(LogStatus.INFO, "Snapshot-->" + test.addScreenCapture(ssName + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void generateRandomEmail() {
		try {

		} catch (Exception e) {

		}

	}

	public static String generateRandomNumber() {
		try {
			return "123456";
		} catch (Exception e) {
			return "123456";
		}

	}

	public static boolean reportBasedOnElement(WebDriver driver, ExtentTest test, String xpath) {
		try {
			driver.findElement(By.xpath(xpath));
			test.log(LogStatus.PASS, "Element found");
			BaseClass.takeSnapshot(driver, "ss4", test);
			return true;
		} catch (Exception nse) {
			test.log(LogStatus.FAIL, "Element Not found");
			BaseClass.takeSnapshot(driver, "ss5", test);
			return false;
		}
	}

}
