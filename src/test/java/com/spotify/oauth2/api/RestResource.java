package com.spotify.oauth2.api;

import com.spotify.oauth2.pojo.PlaylistPOJO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.GlobalSpecBuilder.getRequestSpecification;
import static com.spotify.oauth2.api.GlobalSpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;

public class RestResource {


    public static Response post(Object requestBody,String path,String _token){
        return  given(getRequestSpecification())
                .header("Authorization", "Bearer "+ _token)
                .body(requestBody)
                .when()
                .post(path)// can create a parameter for id as well create overloaded method
                .then()
                .spec(getResponseSpecification())
                .extract().response();
    }

    public static Response postAccount(HashMap<String,String > formParam){
        return  given()
                .baseUri("https://accounts.spotify.com")
                .contentType(ContentType.URLENC)
                .formParams(formParam)
                .when()
                .post("/api/token")
                .then().spec(getResponseSpecification())
                .extract().response();
    }


    public static Response  get(String path,String _token){
        return given(getRequestSpecification())
                .header("Authorization", "Bearer "+ _token)
                .when()
                .get(path)
                .then().spec(getResponseSpecification())
                .extract().response();
    }

    public static Response update(Object requestBody, String path,String _token){

        return given(getRequestSpecification())
                .header("Authorization", "Bearer "+ _token)
                .body(requestBody)
                .when()
                .put(path)
                .then()
                .extract().response();
    }

}
