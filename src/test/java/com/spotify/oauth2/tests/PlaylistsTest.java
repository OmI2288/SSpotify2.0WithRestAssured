package com.spotify.oauth2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistsTest {

    RequestSpecification requestSpecification ;
    ResponseSpecification responseSpecification;

   public String _token = "BQDS8czHJAHbV07MzTZhoDVvi2erSbsJ2vlTp5Vk0UxEnLAk9Xb0M47w8PbgmOdGvYNjiNG5gtqbzk7qYQ8WTqGfiakTh-x5vYykDI7BdkG4LK6STuxf4y53Z_MQm5OdCbumce7fH8eA9AbPXewvPFAD8gHmvHwF4yI7M94HpfIcWQm4pm55LpZFKwaIFeUvyZ9Dvz4gWoDlY2FKBCWQCzTyF54YQIJlJ7f5FszhJtSRzrMusriLEaNZaEUaUO3S0_6xAvKzmLpAJCv3aBbnGojkNtHD-XGkUYvLJnAcnYQvwWMN";


    @BeforeClass
    public void setUp(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization", "Bearer "+ _token)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        responseSpecification = responseSpecBuilder.build();
    }


    @Test
    public void User_Should_Be_Able_To_Create_Playlist(){
       String payload = "{\n" +
               "    \"name\": \"New Playlist\",\n" +
               "    \"description\": \"New playlist description\",\n" +
               "    \"public\": false\n" +
               "}" ;

        given(requestSpecification)
                .body(payload)
        .when()
                .post("/users/31t6kgngig6kyyjtsvnkjpcbuh5q/playlists")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(201)
                .body("name",equalTo("New Playlist"),
                        "description",equalTo("New playlist description"),
                        "public",equalTo(false)
                        );
    }

    @Test
    public void User_Should_Be_Able_To_Get_Playlist(){
        given(requestSpecification)
        .when()
                .get("playlists/66C3OxklZl8LMDDH6Wh6zb")
        .then().spec(responseSpecification)
                .assertThat()
                .body("name",equalTo("New Playlist"),
                        "description", equalTo("New playlist description"));
    }

   @Test
    public void Should_Be_Able_To_Update_Playlist(){
        String payload =  "{\n" +
                "    \"name\": \"Updated Playlist Name\",\n" +
                "    \"description\": \"Updated playlist description\",\n" +
                "    \"public\": false\n" +
                "}";
        given(requestSpecification)
                .body(payload)
        .when()
                .put("/playlists/66C3OxklZl8LMDDH6Wh6zb")
        .then()
                .assertThat();// No response get so no validations.
   }

   @Test
    public void user_should_not_be_be_able_to_create_playlist_without_name(){
        String payload = "{\n" +
                "    \"name\": \"\",\n" +
                "    \"description\": \"New playlist description\",\n" +
                "    \"public\": false\n" +
                "}" ;

        given(requestSpecification)
                .body(payload)
                .when()
                .post("/users/31t6kgngig6kyyjtsvnkjpcbuh5q/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(400)
                .body("error.status",equalTo(400),
                        "error.message",equalTo("Missing required field: name")
                );
    }

    @Test
    public void user_should_not_be_be_able_to_create_playlist_with_invalid_accesstoken(){
        String payload = "{\n" +
                "    \"name\": \"NEw Playlist\",\n" +
                "    \"description\": \"New playlist description\",\n" +
                "    \"public\": false\n" +
                "}" ;

        given()
                .baseUri("https://api.spotify.com")
                .basePath("/v1")
                .header("Authorization", "Bearer "+ "12212")
                .body(payload)
        .when()
                .post("/users/31t6kgngig6kyyjtsvnkjpcbuh5q/playlists")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(401)
                .body("error.status",equalTo(401),
                        "error.message",equalTo("Invalid access token")
                );
    }
}

