package com.example.bookstore.helpers;

import java.util.Random;

public class CodeGenerator {

    public static String generateCode(){
        Random random=new Random();
        return String.valueOf(random.nextInt(1000, 9999));
    }
}
