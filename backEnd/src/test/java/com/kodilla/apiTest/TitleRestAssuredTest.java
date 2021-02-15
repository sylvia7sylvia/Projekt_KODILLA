package com.kodilla.apiTest;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.kodilla.apiTest.Utils.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TitleRestAssuredTest {
    String testTitle;
    String testAuthor;
    int testYear;
    int testTitleId;

    @Test
    public void test011GivenTitleUrlWhenPostNewTitleThenResponseHasTitleId() {
        //given
        testTitle = "Harry Potter i Zakon Feniksa";
        testAuthor = "J.R.Rowling";
        int testYear = 2003;
        int expStatus = 200;
        int oldTestId = 11595;
        //building Json object
        String jsonString = jsonStringTitleBuilder(testUsrId, testTitle, testAuthor, testYear);

        //when
        Response response = given()
                .contentType("application/json")
                .body(jsonString)
                .when()
                .post(titleUrl)
                //then
                .then().statusCode(expStatus)
                .extract().response();

        testTitleId = Integer.parseInt(response.getBody().asString());
        Assert.assertTrue(testTitleId > oldTestId);
        System.out.println(testTitleId);
    }

    @Test
    public void test012GivenTitleUrlWhenGetTitlesListThenResponseHasTitleFromTest011() {
        //given
        testTitle = "Harry Potter i Zakon Feniksa";
        testAuthor = "J.R.Rowling";
        int testYear = 2003;

        //when + then
        given().queryParam("userId", testUsrId)
                .when()
                .get(titleUrl)
                .then().statusCode(200)
                .assertThat()
                .body("id", hasItem(testTitleId))  //created in t201
                .body("title", hasItem(testTitle))
                .body("author", hasItem(testAuthor))
                .body("year", hasItem(testYear));
    }

    @Test
    public void test013GivenTitleUrlWhenPostNewTitleWithEmptyTitleField() {
        //given
        String jsonString = jsonStringTitleBuilder(11588, "", "Jan Kowalski", 2000);

        //when + then
        given().contentType("application/json")
                .body(jsonString)
                .post(titleUrl)
                .then().statusCode(greaterThan(300));
    }

    @Test
    public void test014GivenTitleUrlWhenPostNewTitleWithEmptyAuthorField() {
        //given
        String jsonString = jsonStringTitleBuilder(11588, "Title", "", 2000);
        //when + then
        given().contentType("application/json")
                .body(jsonString)
                .post(titleUrl)
                .then().statusCode(greaterThan(300));
    }

    @Test
    public void test015GivenTitleUrlWhenPostNewTitleWithEmptyYearField() {
        //given
        String jsonString = jsonStringTitleBuilder(11588, "Title", "Jan Kowalski", 0);

        //when + then
        given().contentType("application/json")
                .body(jsonString)
                .post(titleUrl)
                .then().statusCode(greaterThan(300));
    }

    @Test(priority = 1)
    public void test014GivenTitleUrlWhenPutTitle() {
        //given
        String testTitle = "Harry Potter i Kamien Filozoficzny";
        String testAuthor = "J.K.Rowling";
        int testYear = 1997;
        int titleId = testTitleId;
        JSONObject json = new JSONObject()
                .put("userId", testUsrId)
                .put("id", titleId)
                .put("title", testTitle)
                .put("author", testAuthor)
                .put("year", testYear);
        int expStatus = 200;

        //when and then
        given().contentType("application/json")
                .body(json.toString())
                .when().put(titleUrl)
                .then().statusCode(expStatus)
                .assertThat()
                .body("id", is(titleId))
                .body("title", is(testTitle))
                .body("author", is(testAuthor))
                .body("year", is(testYear));
    }

    @Test
    public void test015GivenTitleUrlWhenDeleteTitle() {
        //given
        String expResponse = "true";
        int titleIdToRemove = 11590;
        int expStatus = 200;

        //when and then
        given().queryParam("userId", testUsrId)
                .queryParam("id", titleIdToRemove)
                .when().delete(titleUrl)
                .then().statusCode(expStatus)
                .assertThat().body(is(expResponse));

        //when and then
        given().queryParam("userId", testUsrId)
                .when()
                .get(titleUrl)
                .then().statusCode(200)
                .assertThat()
                .body("id", not(titleIdToRemove));

    }
}
