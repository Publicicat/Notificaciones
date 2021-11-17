package com.publicicat.pgram;

public class MyRestApiConstructor {

    private String id;
    private String token;
    private String yogato;
    private String otrogato;

    public MyRestApiConstructor(String id, String token, String yogato, String otrogato) {
        this.id = id;
        this.token = token;
        this.yogato = yogato;
        this.otrogato = otrogato;
    }

    public MyRestApiConstructor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getYogato() {
        return yogato;
    }

    public void setYogato(String yogato) {
        this.yogato = yogato;
    }

    public String getOtrogato() {
        return otrogato;
    }

    public void setOtrogato(String otrogato) {
        this.otrogato = otrogato;
    }
}

