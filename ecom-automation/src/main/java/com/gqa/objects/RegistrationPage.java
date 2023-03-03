package com.gqa.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.dockerjava.api.model.Driver;

public class RegistrationPage {
	WebDriver driver;

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
	}

	By fName = By.id("input-firstname");
	By lName = By.id("input-lastname");
	By email = By.xpath("//input[@id='input-email']");
	By phone = By.xpath("//input[@id='input-telephone']");
	By password = By.xpath("//input[@id='input-password']");
	By passwordConfirm = By.xpath("//input[@id='input-confirm']");
	By chkboxAgree = By.xpath("//input[@name='agree']");
	By buttonContinue = By.xpath("//input[@value='Continue']");

	public void enterFname(String firstName) {
		driver.findElement(fName).sendKeys(firstName);
	}

	public void clearFname(String firstName) {
		driver.findElement(fName).clear();
	}

	public void enterLname(String lastName) {
		driver.findElement(lName).sendKeys(lastName);
	}

	public void enterEmail(String mail) {
		driver.findElement(email).sendKeys(mail);
	}

	public void enterPhone(String phoneNumber) {
		driver.findElement(phone).sendKeys(phoneNumber);
	}

	public void enterPassword(String pwd) {
		driver.findElement(password).sendKeys(pwd);
	}

	public void enterConfirmPasword(String pwd) {
		driver.findElement(passwordConfirm).sendKeys(pwd);
	}

	public void clickTermsAndConditions() {
		driver.findElement(chkboxAgree).click();
	}

	public void clickContinue() {
		driver.findElement(buttonContinue).click();
	}

	public void fillRegForm(String fName, String lName, String email, String phone, String password) {
		enterFname(fName);
		enterLname(lName);
		enterEmail(email);
		enterPhone(phone);
		enterPassword(password);
		enterConfirmPasword(password);

	}

}
