package com.gqa.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPageAlt {

	WebDriver driver;

	public RegistrationPageAlt(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "input-lastname")
	public WebElement tboxLastName;

	public void enterLastName(String value) {
		tboxLastName.sendKeys(value);
	}

}
