package tests;

import api.AuthorizationRequests;
import api.BookRequests;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import ui.ProfilePage;

import java.util.Map;

public class BaseTest {

    public BookRequests bookRequests = new BookRequests();
    public ProfilePage profile = new ProfilePage();

    @BeforeAll
    static void setUp() {
        // Browser config
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl = "https://demoqa.com";

        // Selenoid config
        String SELENOID_URL = System.getProperty("selenoid.url");
        String SELENOID_LOGIN = System.getProperty("selenoid.login");
        String SELENOID_PASSWORD = System.getProperty("selenoid.password");
        if (SELENOID_URL != null) {
            Configuration.remote = String.format("https://%s:%s@%s/wd/hub",
                    SELENOID_LOGIN, SELENOID_PASSWORD, SELENOID_URL);
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }

        // RestAssured config
        RestAssured.baseURI = "https://demoqa.com";
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

}