package pro.sky.ShelterTelegramBot.utils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class URLPathGenerator {
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateURLPath(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(ALPHA_NUMERIC_STRING.length());
            sb.append(ALPHA_NUMERIC_STRING.charAt(index));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            URL url = new URL("http://example.com/" + generateURLPath(10));
            System.out.println("Generated URL: " + url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}