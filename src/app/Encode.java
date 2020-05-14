package app;

import java.util.Base64;

public class Encode {
    public static String encode(String mess) {
        String en = "hj32da88hardCode" + Base64.getEncoder().encodeToString(mess.getBytes()) + "v0oibe+nh/o";
        return en;
    }

}