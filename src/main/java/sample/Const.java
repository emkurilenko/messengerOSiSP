package sample;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Const {
    final public static String URL = "http://localhost:8080/mess/";

    public static InputStream convertStringToIS(String string){
        return new ByteArrayInputStream(Charset.forName("UTF-16").encode(string).array());
    }
}
