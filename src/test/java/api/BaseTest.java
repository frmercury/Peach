package api;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import pages.WebDriverManagerClass;

import java.net.MalformedURLException;
import java.util.Locale;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.open;
import static helpers.Loader.loadProperty;
import static java.lang.String.format;
import static java.lang.System.err;
import static java.lang.System.getProperty;

public abstract class BaseTest {

    protected static WebDriver driver;

    protected Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite(alwaysRun = true, description = "Driver props init")

    public void initProps() {

        DesiredCapabilities caps = new DesiredCapabilities();

        //browser settings
        browser = getProperty("browser");
        baseUrl = loadProperty("app.url");
        browserSize = "1920x1080";

        if (getProperty("env", "daily").toLowerCase(Locale.ROOT).equals("stage")) {
            caps.setCapability("browser", getProperty("browser").toLowerCase(Locale.ROOT));
        }

        browserCapabilities = caps;
    }

    @BeforeMethod
    protected synchronized void initWebBrowser(Object[] params) throws MalformedURLException {


        DesiredCapabilities newCaps = new DesiredCapabilities();

        if (getProperty("env", "daily").toLowerCase(Locale.ROOT)
                .equals("stage")) {
            switch (browser.toLowerCase(Locale.ROOT)) {
                case "chrome" -> {
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--incognito");
                    options.addArguments("--log-level=3");
                    options.addArguments("--ignore-certificate-errors");
                    newCaps.setCapability(ChromeOptions.CAPABILITY, options);
                }
                case "firefox" -> {
                    FirefoxProfile profile = new FirefoxProfile();
                    FirefoxOptions options = new FirefoxOptions();
                    profile.setPreference("browser.private.browsing.autostart", true);
                    options.setProfile(profile);
                    newCaps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
                }
                default -> newCaps.setCapability("mode", "incognito");
            }
        } else {
                browserCapabilities.merge(newCaps);
            }
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
