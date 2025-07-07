package tests;

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
        // === Print system props for debug ===
        System.out.println("=== CONFIG CHECK ===");
        System.out.println("Selenoid URL: " + System.getProperty("selenoid.url"));
        System.out.println("Login: " + System.getProperty("selenoid.login"));
        System.out.println("Password: " + System.getProperty("selenoid.password"));
        System.out.println("Browser version: " + System.getProperty("BROWSER_VERSION"));

        // Browser config
        String browserVersion = System.getProperty("BROWSER_VERSION", "137.0");
        Configuration.browser = "chrome";
        Configuration.browserVersion = browserVersion;
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl = "https://demoqa.com";

        //Selenoid config
        String SELENOID_URL = System.getProperty("selenoid.url");

        if (SELENOID_URL != null && !SELENOID_URL.isBlank()) {

            Configuration.remote = String.format("http://%s/wd/hub", SELENOID_URL);
        }


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        // API config
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
