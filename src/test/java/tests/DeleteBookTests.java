package tests;

import models.AddBookModel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import helpers.WithLogin;

import static io.qameta.allure.Allure.step;

public class DeleteBookTests extends tests.BaseTest {

    @Test
    @WithLogin
    @DisplayName("Удаление книги из профиля")
    void deleteBookTest() {
        String isbn = "9781491904244";

        step("Удаляем все книги", () ->
                bookRequests.deleteAllBooks());

        step("Добавляем книгу", () ->
                bookRequests.addBook(new AddBookModel()));

        step("Открыть UI и убедиться, что книга добавлена", () -> {
            profile.openProfile()
                    .checkExistenceOfBook(isbn);
        });

        step("Удаляем книгу", () ->
                bookRequests.deleteBook(isbn));

        step("Открыть UI и убедиться, что книга отсутствует", () -> {
            profile.openProfile()
                    .checkAbsenceOfBook(isbn);
        });
    }
}
