package main.java.com.asu.bl;

import static main.java.com.asu.networking.HTTPConstants.bearer;

public class AuthenticationServer {
    private static String accessToken = null;
    public static void cacheAccessCode(String accessTokenToBeCached) {
        accessToken = bearer + accessTokenToBeCached;
    }

    public static String getAccessToken() {
        return accessToken;
    }
}
