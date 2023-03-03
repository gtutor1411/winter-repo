package com.gqa.objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegPage {

	WebDriver driver;
	private static WebElement element = null;

	public RegPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement tboxEmail() {
		element = driver.findElement(By.xpath("//input[@id='input-email']"));
		return element;
	}

	public WebElement tableGrid(String i) {
		element = driver.findElement(By.xpath("//td[" + i + "]"));
		return element;
	}
	
	
	public static WebElement password(WebDriver driver) {
		element = driver.findElement(By.xpath("//input[@id='input-email']"));
		return element;
	}

}
