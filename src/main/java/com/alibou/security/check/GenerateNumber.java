package com.alibou.security.check;

import java.util.Random;

public class GenerateNumber {

    public int generate() {
        Random random = new Random();
        int rand = (int)(Math.random()*9999)+1000;
        return Math.abs(rand);
    }
}
