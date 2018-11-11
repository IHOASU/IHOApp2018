package edu.asu.msse.gnayak2.bl;

/**
 * Created by dixith on 11/11/18.
 */
public class AuthenticationServer {
    String accessToken = null;
    public void cacheAccessCode(String accessTokenToBeCached) {
        accessToken = accessTokenToBeCached;
    }
}
