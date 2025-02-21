package com.spotify.oauth2.api.applicationAPI;
import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.PlaylistPOJO;
import io.restassured.response.Response;
import static com.spotify.oauth2.api.TokenManager.getToken;
public class PlaylistAPI {

    public static Response post(PlaylistPOJO requestBody){
        return RestResource.post(requestBody,"/users/31t6kgngig6kyyjtsvnkjpcbuh5q/playlists",getToken());
    }

    //overloaded post method for access token
    public static Response post(PlaylistPOJO requestBody, String access_token){
        return RestResource.post(requestBody,"/users/31t6kgngig6kyyjtsvnkjpcbuh5q/playlists",access_token);
    }

    public static Response  get(String playlistId){
       return  RestResource.get("playlists/"+playlistId, getToken());
    }

    public static Response update(PlaylistPOJO requestBody, String playlistId){

        return  RestResource.update(requestBody,"/playlists/"+playlistId,getToken());
    }


}
