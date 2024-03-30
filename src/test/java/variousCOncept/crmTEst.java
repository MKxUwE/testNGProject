package variousCOncept;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class crmTEst {
	
	WebDriver Driver;
//	diffrent storing teckninque
	@FindBy(how = How.XPATH, using = "//*[@id=\"user_name\"]") WebElement USER_NAME_ELEMENT;
	@FindBy(how = How.XPATH,using = "//*[@id=\"password\"]") WebElement PASSWORD_ELEMENT;
    String browser = null;
	String url ;
	String username ;
	String password ;
	String dashboardvalidationtest = "Dashboard";
	String usernamevalidationtest = "Please enter your user name";
	String passwordvalidationtest = "Please enter your password";

	// ELEMENT LIST - By type
	By USER_NAME_FIELD = By.xpath("//input[@id='user_name']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By SIGNIN_BUTTON_FIELD = By.xpath("//button[@id='login_submit']");
	By DASHBOARD_VALIDATION_FIELD = By.xpath("/html/body/div[1]/section/div/div[2]/div/div/header/div/strong");
	By CUSTOMERS_BUTTON_FIELD = By.xpath("/html/body/div[1]/aside[1]/div/nav/ul[2]/li[2]/a/span");
	By ADDCOSRTUMER_BUTTON_FIELD = By.xpath("//*[@id=\"customers\"]/li[2]/a/span");
	By SUBMITION_BUTTON_FIELD = By.xpath("//*[@id=\"save_btn\"]");
	By COMPANY_DROPDOWN_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[2]/div/select");
	By FULL_NAME_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[1]/div/input");
	By E_MAIL_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[3]/div/input");
	By PHONE_FIELD = By.xpath("//*[@id=\"phone\"]");
	By SELECT_OPTIONS_FIELD = By.xpath("//select[@name=\"company_name\"]");
    @BeforeClass
	public void readConfig() {
		// Inputstream //BufferReader //FileReader //Scanner to read from external file
      try{
    	  InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
    	  Properties prop = new Properties();
    	  prop.load(input);
    	  browser = prop.getProperty("browser");
    	  System.out.println("Browser used :" + browser);
    	  url = prop.getProperty("url");
    	  username = prop.getProperty("username");
    	  password = prop.getProperty("password");
      }catch(Exception e) {
    	  e.printStackTrace();
      }
	}

	@BeforeMethod
	public void init() {
        
		if(browser.equalsIgnoreCase("chrome")) {
			
		
     	System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
		Driver = new ChromeDriver();
		}else if(browser.equalsIgnoreCase("Edge")) {
		System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
		Driver = new EdgeDriver();
		}else {
			System.out.println("Enter a valid browser please");
		}
		Driver.manage().deleteAllCookies();
		Driver.get(url);
		Driver.manage().window().maximize();
		
		Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void testLogin() {
//	    USER_NAME_ELEMENT.sendKeys(username);
//		PASSWORD_ELEMENT.sendKeys(password);
		Driver.findElement(USER_NAME_FIELD).sendKeys(username);
		Driver.findElement(PASSWORD_FIELD).sendKeys(password);
		Driver.findElement(SIGNIN_BUTTON_FIELD).click();
		Assert.assertEquals(Driver.findElement(DASHBOARD_VALIDATION_FIELD).getText(), dashboardvalidationtest,"Dashboard page is not available");
	}

//	 @Test
    public void testAlert() {
    	Driver.findElement(SIGNIN_BUTTON_FIELD).click();
    	Assert.assertEquals(Driver.switchTo().alert().getText(), usernamevalidationtest, "alert is not available");
    	Driver.switchTo().alert().accept();
    	Driver.findElement(USER_NAME_FIELD).sendKeys(username);
    	Driver.findElement(SIGNIN_BUTTON_FIELD).click();
    	Assert.assertEquals(Driver.switchTo().alert().getText(), passwordvalidationtest, "alert is not available");
    	Driver.switchTo().alert().accept();
    	
    }
//	 @AfterMethod
		public void teardown() {
			Driver.close();
			Driver.quit();
		}
}
