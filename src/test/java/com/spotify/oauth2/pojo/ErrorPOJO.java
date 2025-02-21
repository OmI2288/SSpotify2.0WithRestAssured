package com.spotify.oauth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorPOJO {

    @JsonProperty("error")
    private InnerErrorPOJO error;

    @JsonProperty("error")
    public InnerErrorPOJO getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(InnerErrorPOJO error) {
        this.error = error;
    }
}