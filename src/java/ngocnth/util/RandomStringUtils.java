
package ngocnth.util;

import java.io.Serializable;
import java.util.Random;

public class RandomStringUtils implements Serializable {
    
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGIT = "0123456789";
    private static final String ALPHA_NUMERIC = ALPHA.toUpperCase() + DIGIT;
    
    private static Random generator = new Random();
    
    public static String randomAlphanummeric (int count) {
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int index = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(index);
            sb.append(ch);
        }
        return sb.toString();
    }
    
    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }
    
}
