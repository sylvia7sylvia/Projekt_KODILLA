package com.kodilla.apiTest;

import org.json.JSONObject;
import org.testng.annotations.Test;

import static com.kodilla.apiTest.Utils.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserRestAssuredTest {

    @Test
    public void test001GivenRegisterUrlWithSuccessRegistration() {
        //given
        int expectedHttpCode = 200;
        int expectedId = 11588;
        boolean expectedIsUserNew = true;
        JSONObject json = new JSONObject()   //json object with registration data
                .put("login", "sylvia7sylvia")
                .put("password", "mojehaslo123");

        //when and then
        given()
                .contentType("application/json")
                .body(json.toString())
                .when().post(registerUrl)
                .then().statusCode(expectedHttpCode)
                .assertThat().body("id", greaterThanOrEqualTo(expectedId))
                .assertThat().body("new", is(expectedIsUserNew));
    }

    @Test
    public void test002GivenLoginUrlWithSuccessLogin() {
        //given
        JSONObject json = jsonObjectUserBuilder(testUser, testUsrPwd);
        int expectedHttpCode = 200;
        String expectedId = "11588";

        //when and then
        given()
                .contentType("application/json")
                .body(json.toString())
                .when().post(loginUrl)
                .then().statusCode(expectedHttpCode)
                .assertThat().body(is(expectedId));
    }

    @Test
    public void test003GivenLoginUrlWithBadLoginAndGoodPassword() { //loggin failed
        //given
        JSONObject json = jsonObjectUserBuilder(testUser + "x", testUsrPwd);

        //when and then
        given()
                .contentType("application/json")
                .body(json.toString())
                .when().post(loginUrl)
                .then().assertThat().body(is("-1"));
    }

    @Test
    public void test004GivenLoginUrlWithGoodLoginBadPassword() {  //loggin failed
        //given
        JSONObject json = jsonObjectUserBuilder(testUser, testUsrPwd + "1");

        //when and then
        given()
                .contentType("application/json")
                .body(json.toString())
                .when().post(loginUrl)
                .then().assertThat().body(is("-1"));
    }

    @Test
    public void test005GivenRegisterUrlRegistrationWithExistingUserAndDifferentPassword() {  //error user exist
        //given
        JSONObject json = jsonObjectUserBuilder(testUser, "testtest");

        //when and then
        given()
                .contentType("application/json")
                .body(json.toString())
                .when().post(registerUrl)
                .then()
                .log().ifStatusCodeIsEqualTo(200)
                .statusCode(403);
        test002GivenLoginUrlWithSuccessLogin();

    }

    @Test
    public void givenUrlWhenSuccessOnGetsResponseAndJsonHasAuthor() {
        //given
        given()

                //when and then
                .when().get(baseUrl + "/titles/?userId=211")
                .then().statusCode(200)
                .assertThat().body("author", hasItems("Jan Kowalski"));
    }

    @Test
    public void getUsrId() {
        //given
        JSONObject json = jsonObjectUserBuilder(testUser, testUsrPwd);
        //when and then
        String response = given().contentType("application/json").body(json.toString())
                .when().post(loginUrl)
                .then().statusCode(200).extract().response().body().asString();
        System.out.println(response);
    }
}
