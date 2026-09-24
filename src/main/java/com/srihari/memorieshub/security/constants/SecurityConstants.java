package com.srihari.memorieshub.security.constants;


public class SecurityConstants {

    public static final String JWT_SECRET =
            "mySuperSecretKeyForMemoriesHubApplication123456789";

    public static final long JWT_EXPIRATION =
            86400000; // 24 hours

    private SecurityConstants() {
    }
}
