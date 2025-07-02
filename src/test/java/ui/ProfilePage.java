package ui;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProfilePage {

    public ProfilePage openProfile() {
        open("/profile");
        return this;
    }

    public void checkExistenceOfBook(String isbn) {
        $("[href='/profile?book=" + isbn + "']").should(exist);
    }

    public void checkAbsenceOfBook(String isbn) {
        $("[href='profile?book=" + isbn + "']").should(not(exist));
    }

}