package app;

import java.util.Base64;

public class EncodeDeCode {
    public static String encode(String mess) {
        String en = Base64.getEncoder().encodeToString(mess.getBytes()) + "voibenho";
        return en;
    }

    public static String decode(String mess) {
        mess = mess.replace("voibenho", "");
        return new String(Base64.getDecoder().decode(mess));
    }
}