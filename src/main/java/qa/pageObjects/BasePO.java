package qa.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import qa.base.TestBase;

public class BasePO {

    protected static WebDriver driver;

    protected static WebDriverWait webDriverWait;

    public BasePO() {
        driver = TestBase.driver;
        webDriverWait = TestBase.webDriverWait;
    }

    /**
     * Driver inputs given text to element specified by given css selector
     * @param locator
     * @param text
     */
    public void input(String locator, String text) {
        driver.findElement(By.cssSelector(locator)).clear();
        driver.findElement(By.cssSelector(locator)).sendKeys(text);
    }

    /**
     * Selects given value from select drop down element specified by given locator
     * @param locator
     * @param text
     */
    public void select(String locator, String text) {
        new Select(driver.findElement(By.cssSelector(locator))).selectByVisibleText(text);
    }

    /**
     * Driver presses button specified by given locator
     * @param locator
     */
    public void pressButton(String locator) {
        driver.findElement(By.cssSelector(locator)).click();
    }

    /**
     * Returns text of element specified by given locator
     * @return
     */
    public String getText(String locator) {
        return driver.findElement(By.cssSelector(locator)).getText();
    }

    /**
     * Waits for element specified from given locator, to be visible on page
     * @param locator
     */
    public void waitForElementVisibility(String locator) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
    }

    /**
     * Refreshes page
     */
    public void refreshPage() {
        driver.navigate().refresh();
    }

}
