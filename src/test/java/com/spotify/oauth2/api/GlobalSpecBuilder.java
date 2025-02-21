package com.spotify.oauth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class GlobalSpecBuilder {

    static RequestSpecification requestSpecification ;
    static ResponseSpecification responseSpecification;



    public static RequestSpecification getRequestSpecification(){
        System.out.println("<<-------------Request Specification------------->>");
          return new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

    }

    public static RequestSpecification getAccountRequestSpecification(){
        System.out.println("<<-------------Account Request Specification------------->>");
        return new RequestSpecBuilder()
                .setBaseUri("https://accounts.spotify.com")
                .setContentType(ContentType.URLENC)
                .log(LogDetail.ALL)
                .build();

    }

    public static ResponseSpecification getResponseSpecification(){
        System.out.println("<<-------------Response Specification------------->>");
         return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }


}
