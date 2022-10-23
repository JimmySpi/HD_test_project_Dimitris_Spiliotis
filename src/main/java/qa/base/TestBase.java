package qa.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Properties;


public class TestBase {

    public static WebDriver driver;
    public static WebDriverWait webDriverWait;
    public static Properties properties;
    protected static ChromeOptions chromeOptions;
    protected static FirefoxOptions firefoxOptions;
    protected static Logger log = LoggerFactory.getLogger("Logger");
    public static String BASE_URL;

    @BeforeSuite()
    public void beforeSuite(ITestContext ctx) {
        try {
            properties = new Properties();
            String propertiesFileName = ctx.getSuite().getName().replace(" ","_").toLowerCase();
            FileInputStream propFile = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/java/qa/config/"+propertiesFileName+".properties");
            properties.load(propFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("properties file not found..");
        } catch (Exception e) {
            System.out.println("exception with processing or opening properties file..");
        }

        log.info("Run Suite: "+ctx.getSuite().getName());
        init();
    }

    private static void init() {

        String browserName = properties.getProperty("browser");
        driver = getDriver(browserName);
        log.info(browserName+" is configured.");

        driver.manage().timeouts().implicitlyWait( Duration.ofSeconds(Long.parseLong(properties.getProperty("implicit_wait"))));
        driver.manage().timeouts().pageLoadTimeout( Duration.ofSeconds(Long.parseLong(properties.getProperty("page_load_timeout"))));

        BASE_URL = "file:///"+System.getProperty("user.dir") + "/src/files/" + properties.getProperty("url");

        log.info("Base url configured: "+BASE_URL);
        driver.get(BASE_URL);

        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(properties.getProperty("implicit_wait"))));
    }

    private static WebDriver getDriver(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/drivers/chromedriver.exe");
            chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            return new ChromeDriver(chromeOptions);
        }
        else if (browserName.equalsIgnoreCase("firefox")) {
            //...
        }
        //else if any other browser we might need
        return null;
    }

    @AfterSuite
    public void afterSuite() {
        driver.close();
    }

}
