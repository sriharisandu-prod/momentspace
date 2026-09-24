package com.srihari.memorieshub.auth;



import lombok.Data;

@Data
public class GoogleUserInfo {

    private String id;

    private String email;

    private String name;

    private String picture;

    private String verified_email;
}
