package edu.asu.msse.gnayak2.bl;

import static edu.asu.msse.gnayak2.networking.HTTPConstants.bearer;

public class AuthenticationServer {
    private static String accessToken = null;
    public static void cacheAccessCode(String accessTokenToBeCached) {
        accessToken = bearer + accessTokenToBeCached;
    }

    public static String getAccessToken() {
        return accessToken;
    }
}
