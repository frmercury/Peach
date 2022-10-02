package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Configuration.browser;

public class WebDriverManagerClass {

    public WebDriverManagerClass() {
        setWebDriver();
    }

    private static WebDriver webDriver;

    private void setWebDriver() {

        switch (browser) {
            case "chrome":
                webDriver = WebDriverManager.chromedriver().create();
                break;
            case "firefox":
                webDriver = WebDriverManager.firefoxdriver().create();
                break;
            case "opera":
                webDriver = WebDriverManager.operadriver().create();
                break;
            default:
                throw new RuntimeException(browser + " browser does not exists. Set a correct one");
        }
    }

    public static WebDriver getWebDriver() {

        if (webDriver == null) {
            new WebDriverManagerClass();
        }
        return webDriver;
    }
}