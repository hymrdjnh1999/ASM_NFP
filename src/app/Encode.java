package app;

import java.util.Base64;

public class Encode {
    public static String encode(String mess) {
        mess = "hj32da88hardCode" + mess + "==v0oibe+nh/o";
        String en = Base64.getEncoder().encodeToString(mess.getBytes());
        return en;
    }

}