package com.spotify.oauth2.tests;
import com.spotify.oauth2.api.applicationAPI.PlaylistAPI;
import com.spotify.oauth2.pojo.ErrorPOJO;
import com.spotify.oauth2.pojo.PlaylistPOJO;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistsTestRefactored {


    @Test
    public void User_Should_Be_Able_To_Create_Playlist(){
        PlaylistPOJO playlistPOJO = new PlaylistPOJO()
                .setName("New Playlist 1")
                .setDescription("This is my playlist description")
                .setPublic(false);

        Response response    = PlaylistAPI.post(playlistPOJO);
        assertThat(response.statusCode(),equalTo(201));

        //Deserialize
        PlaylistPOJO deserializedPlaylistPOJO = response.as(PlaylistPOJO.class);
        assertThat(deserializedPlaylistPOJO.getName(),equalTo(playlistPOJO.getName()));
        assertThat(deserializedPlaylistPOJO.getDescription(),equalTo(playlistPOJO.getDescription()));
        assertThat(deserializedPlaylistPOJO.getPublic(),equalTo(playlistPOJO.getPublic()));


    }

    @Test
    public void User_Should_Be_Able_To_Get_Playlist(){
      Response response = PlaylistAPI.get("66C3OxklZl8LMDDH6Wh6zb");
      assertThat(response.statusCode(),equalTo(200));

      PlaylistPOJO responsePlaylist = response.as(PlaylistPOJO.class);

      assertThat(responsePlaylist.getName(),equalTo("New Playlist 1"));
      assertThat(responsePlaylist.getDescription(),equalTo("Updated playlist description"));

    }

   @Test
    public void Should_Be_Able_To_Update_Playlist(){
       PlaylistPOJO requestBody = new PlaylistPOJO()
               .setName("New Playlist 1")
               .setDescription("This is my playlist description");

       Response response = PlaylistAPI.update(requestBody,"66C3OxklZl8LMDDH6Wh6zb");
       assertThat(response.statusCode(),equalTo(200));

   }

   @Test
    public void user_should_not_be_be_able_to_create_playlist_without_name(){
        PlaylistPOJO request = new PlaylistPOJO()
                    .setName("")
                    .setDescription("ddddd")
                    .setPublic(false);

        Response response = PlaylistAPI.post(request);
        assertThat(response.statusCode(), equalTo(400));

        ErrorPOJO responseError = response.as(ErrorPOJO.class);
        assertThat(responseError.getError().getMessage(),equalTo("Missing required field: name"));
        assertThat(responseError.getError().getStatus(),equalTo(400));

    }

    @Test
    public void user_should_not_be_be_able_to_create_playlist_with_invalid_accesstoken(){
        PlaylistPOJO request = new PlaylistPOJO()
                .setName("asdsd")
                .setDescription("ddddd")
                .setPublic(false);

        Response response = PlaylistAPI.post(request,"121212");
        assertThat(response.statusCode(),equalTo(401));

        ErrorPOJO responseError = response.as(ErrorPOJO.class);
        assertThat(responseError.getError().getStatus(),equalTo(401));
        assertThat(responseError.getError().getMessage(),equalTo("Invalid access token"));



    }
}

