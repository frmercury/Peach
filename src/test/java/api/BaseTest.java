package api;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
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

        //browser settings
        browser = getProperty("browser");
        baseUrl = loadProperty("app.url");
    }

    @BeforeMethod
    protected void initWebBrowser() {

        DesiredCapabilities newCaps = new DesiredCapabilities();

            switch (browser.toLowerCase(Locale.ROOT)) {
                case "chrome" -> {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--start-maximized");
                    newCaps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
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
        browserCapabilities = newCaps;
        open(baseUrl);
    }

    @AfterMethod(alwaysRun = true, description = "End browser session")
    public void closeWebBrowser() {
        log.info("Browser is closed");
        driver.quit();
    }
}
