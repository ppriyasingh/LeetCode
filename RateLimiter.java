package com.company;

import java.util.Date;
import java.util.HashMap;

/**
 * Rate Limiter of 2tps
 */
public class Main {
    HashMap<String, Date> lastHit = new HashMap<>();
    HashMap<String, Integer> token = new HashMap<>();
    int RATE_LIMIT_TOKEN = 2;

    // Token Bucket
    public boolean rateLimiter(String user) {

        if(lastHit.containsKey(user) && ( new Date().getSeconds() - lastHit.get(user).getSeconds() < 1)) {
                if((token.get(user) > 0) ) {
                    token.put(user, token.get(user) - 1);
                    return true;
                }
                return false;
        } else {
            lastHit.put(user, new Date());
            token.put(user, RATE_LIMIT_TOKEN - 1);
            return true;
        }
    }

    public void cleanup() {

    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("*** Rate Limiter ***");        // 17:15
        Main mainObj = new Main();

        System.out.println("u1: " + (mainObj.rateLimiter("u1")? "Allow" : "Deny"));
        System.out.println("u1: " + (mainObj.rateLimiter("u1")? "Allow" : "Deny"));
        System.out.println("u1: " + (mainObj.rateLimiter("u1")? "Allow" : "Deny"));
        Thread.sleep( 1011);
        System.out.println("u1: " + (mainObj.rateLimiter("u1")? "Allow" : "Deny"));

        mainObj.cleanup();
    }
}
