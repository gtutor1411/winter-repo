package com.gqa.tests;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v85.systeminfo.SystemInfo;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gqa.base.BaseClass;
import com.gqa.data.DataClass;
import com.gqa.objects.RegPage;
import com.gqa.objects.RegistrationPage;
import com.gqa.objects.RegistrationPageAlt;
import com.gqa.utils.DataStreamer;
import com.gqa.utils.ExcelUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EcomTest extends BaseClass {
	WebDriver driver;
	ExtentReports report;
	Properties prop;
	String baseUrl;
	Logger log = LogManager.getLogger(EcomTest.class);
	String rootPath;
	ExcelUtil envSheet;

	@BeforeTest
	public void setup() {

		try {
			// env configuration
			log.info("Initiating logs for Tests");
			rootPath = System.getProperty("user.dir");
			FileInputStream fis = new FileInputStream(rootPath + "/config.properties");
			prop = new Properties();
			prop.load(fis);
			baseUrl = prop.getProperty("base.url");
			envSheet = new ExcelUtil(rootPath + DataClass.PATH_TESTDATA, "ENV");
			String runEnv = envSheet.getCellValue(0, 1);
			System.out.println("Running tests in " + runEnv);

			// Report Configuration
			report = new ExtentReports(rootPath + "/Reports/Results.html");
			log.info("Initiating Reports");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterTest
	public void tearDown() {
		report.flush();
	}

	// ***************************************************************************************//
	@BeforeMethod
	public void initBrowser() {
		// browser config
		String browser = prop.getProperty("browser");
		if (browser.equalsIgnoreCase("CH")) {
			log.info("Initiating tests on CH browser");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		if (browser.equalsIgnoreCase("ED")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
	}

	@AfterMethod
	public void handleBrowsers() {
		driver.quit();
	}

	// **********************************************Tests*****************************************//

	@Test(priority = 0, enabled = false)
	public void titleTest() {
		boolean result = true;
		ExtentTest test = report.startTest("Verify Title of Ecom Site");
		try {
			test.log(LogStatus.INFO, "Navigating to Ecommerce website");
			driver.get("https://naveenautomationlabs.com/opencart/");
			if (driver.getTitle().equalsIgnoreCase("Your Store")) {
				test.log(LogStatus.PASS, "Title is as expected-Test Passed");
			} else {
				result = false;
				test.log(LogStatus.FAIL, "Title is NOT as expected-Test Failed");
			}
			assertTrue(result);
			report.endTest(test);
		} catch (Exception e) {
			e.printStackTrace();
			report.endTest(test);
		}

	}

	@Test(priority = 1, enabled = false)
	public void footerLinksTest() {
		boolean result = true;
		ExtentTest test = report.startTest("Verify footer links of Ecom Site");
		try {
			test.log(LogStatus.INFO, "Navigating to Ecommerce website");
			driver.get("https://naveenautomationlabs.com/opencart/");
			try {
				driver.findElement(By.linkText("Contact Us"));
				test.log(LogStatus.PASS, "Footer links found as expected");
			} catch (Exception nse) {
				result = false;
				test.log(LogStatus.FAIL, "Footer Links not found");
			}
			assertTrue(result);
			report.endTest(test);
		} catch (Exception e) {
			e.printStackTrace();
			report.endTest(test);
		}

	}

	@Test(enabled = true, dataProvider = "registrationData")
	public void registerTest(String fName, String lName, String email, String phone, String password) {
		boolean result1 = true;
		boolean result2 = true;
		RegistrationPage registrationPage = new RegistrationPage(driver);
		RegistrationPageAlt regPageAlt = new RegistrationPageAlt(driver);
		RegPage regPage = new RegPage(driver);

		ExtentTest test = report.startTest("Verify Registering a new user");
		try {
			// navigation
			test.log(LogStatus.INFO, "Navigating to test url");
			driver.get(baseUrl + "account/register");
			// action
			test.log(LogStatus.INFO, "Enter user details in the reg form");
			// regPage.fillRegForm(fName, lName, email, phone, password);
			registrationPage.enterFname(fName);
			regPageAlt.enterLastName(lName);
			regPage.tboxEmail().sendKeys(email);
			
			BaseClass.takeSnapshot(driver, "ss1", test);
			test.log(LogStatus.INFO, "Accepting terms and conditions");
			driver.findElement(By.xpath("//input[@name='agree']")).click();
			test.log(LogStatus.INFO, "Clicking on submit button");
			driver.findElement(By.xpath("//input[@value='Continue']")).click();

			// test+ extent test reporting
			String successMessage = driver.findElement(By.xpath(".//h1")).getText();
			if (successMessage.equalsIgnoreCase("Your Account Has Been Created!")) {
				test.log(LogStatus.PASS, "Account creation message found");
				BaseClass.takeSnapshot(driver, "ss2", test);
			} else {
				test.log(LogStatus.FAIL, "Account creation message NOT found");
				result1 = false;
				BaseClass.takeSnapshot(driver, "ss3", test);
			}

			try {
				driver.findElement(By.xpath("//p[contains(text(),'Your new account has been success')]"));
				test.log(LogStatus.PASS, "Congrats found");
				BaseClass.takeSnapshot(driver, "ss4", test);
			} catch (Exception nse) {
				test.log(LogStatus.FAIL, "Congrats NOT found");
				BaseClass.takeSnapshot(driver, "ss5", test);
				result2 = false;

				test.log(LogStatus.ERROR, nse);
				log.error(" Error occured !!!!!");
			}

			// TestNG Reporting
			assertTrue(result1 && result2);

		} catch (Exception e) {
			BaseClass.takeSnapshot(driver, "ssx", test);
			e.printStackTrace();
		}

	}

	@Test(enabled = false, dataProvider = "loginData")
	public void loginTest(String userName, String password) {
		boolean result1 = true;
		ExtentTest test = report.startTest("Verify user login");
		try {
			test.log(LogStatus.INFO, "Navigating to test url");
			driver.get(baseUrl + "account/login");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// ***********Data Providers***************
	@DataProvider(name = "registrationData")
	public Object[][] registrationData() {
		DataStreamer datStream = new DataStreamer();
		Object[][] returnData = datStream.data("RegistrationData");
		return returnData;
	}

	@DataProvider(name = "loginData")
	public Object[][] loginData() {
		DataStreamer datStream = new DataStreamer();
		Object[][] returnData = datStream.data("LoginData");
		return returnData;

	}

}
