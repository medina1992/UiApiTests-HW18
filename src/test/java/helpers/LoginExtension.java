package helpers;

import api.AuthorizationRequests;
import models.LoginResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import tests.TestData;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        LoginResponseModel loginResponse = AuthorizationRequests.login(TestData.credentials);

        boolean isTokenValid = AuthorizationRequests.checkToken(loginResponse.getToken(), loginResponse.getUserId());
        if (!isTokenValid) {
            loginResponse = AuthorizationRequests.login(TestData.credentials); // пере-логин
        }
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
    }
}