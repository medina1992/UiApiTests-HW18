package api;

import models.AddBookModel;
import models.DeleteBookModel;
import models.IsbnModel;
import models.LoginResponseModel;
import tests.TestData;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.BaseSpecs.requestSpec;
import static specs.BaseSpecs.responseSpec;

public class BookRequests {

    private final LoginResponseModel loginResponse;

    // Выполняем логин один раз при создании объекта
    public BookRequests() {
        this.loginResponse = AuthorizationRequests.login(TestData.credentials);
    }

    public void deleteAllBooks() {
        given(requestSpec)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .queryParam("UserId", loginResponse.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(204));
    }

    public void addBook(AddBookModel bookList) {
        IsbnModel isbn = new IsbnModel("9781491904244");
        List<IsbnModel> isbnList = new ArrayList<>();
        isbnList.add(isbn);

        bookList.setUserId(loginResponse.getUserId());
        bookList.setCollectionOfIsbns(isbnList);

        given(requestSpec)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .body(bookList)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(201));
    }

    public void deleteBook(String isbn) {
        DeleteBookModel deleteBook = new DeleteBookModel();
        deleteBook.setUserId(loginResponse.getUserId());
        deleteBook.setIsbn(isbn);

        given(requestSpec)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .body(deleteBook)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(responseSpec(204));
    }
}