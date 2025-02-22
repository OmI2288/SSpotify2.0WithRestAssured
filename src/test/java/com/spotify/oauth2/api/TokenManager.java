package com.spotify.oauth2.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static com.spotify.oauth2.api.GlobalSpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;

public class TokenManager {

    private static String  access_token;
    private static Instant expiry_time;

    public static String getToken(){
        try{
            if(access_token == null || Instant.now().isAfter(expiry_time)) {
                System.out.println("==========Generating New Token==========");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds);
            }else {
                System.out.println("==========Token Is Good To Use==========");
            }
        }catch (Exception e){
            throw    new RuntimeException("Abort!!!! Failed To Get Token");
        }
        return  access_token;
    }

    public static Response renewToken(){
        HashMap<String,String> formParams = new HashMap<String,String>();
        formParams.put("grant_type","refresh_token");


        Response response  =  RestResource.postAccount(formParams);

        if(response.statusCode() != 200){
            throw  new RuntimeException("Abort !!! Renew Token Failed");
        }

        return response;
    }
}
