package adil.spring.security.util;

import java.util.Random;

public class GenerateNumber {

    public int generate() {
        Random random = new Random();
        int rand = (int)(Math.random()*9999)+1000;
        return Math.abs(rand);
    }
}
