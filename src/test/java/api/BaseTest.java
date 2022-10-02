package api;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import pages.WebDriverManagerClass;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.browser;

import static com.codeborne.selenide.Selenide.open;
import static helpers.Loader.loadProperty;

public abstract class BaseTest {

    protected static WebDriver driver;

    protected Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite(alwaysRun = true, description = "Driver props init")

    public void initProps() {
        browser = loadProperty("browser");
        baseUrl = loadProperty("app.url");
    }

    @BeforeTest(alwaysRun = true, description = "Browser session created")
    protected void initWebDriver() {
        open(baseUrl);
        log.info("Browser is opened");
    }

    @AfterTest(alwaysRun = true, description = "End browser session")
    public void closeWebBrowser() {
        log.info("Browser is closed");
        driver.quit();
    }
}
