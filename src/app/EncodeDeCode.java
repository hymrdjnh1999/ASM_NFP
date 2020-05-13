package app;

import java.util.Base64;

public class EncodeDeCode {
    public static String encode(String mess) {
        String en = "hj32da88hardCode" + Base64.getEncoder().encodeToString(mess.getBytes()) + "v0oibe+nh/o";
        return en;
    }

    public static String decode(String mess) {
        mess = mess.replace("v0oibe+nh/o", "");
        mess = mess.replace("hj32da88hardCode", "");
        return new String(Base64.getDecoder().decode(mess));
    }
}