package com.daewooenc.pips.admin.web.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Jasypt {

    public static void main(String[] args){

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("kHG+8KTJnkBq73vTx0PleUdD7NUqMo+gRXwm9RBYMGA=");
        encryptor.setAlgorithm("PBEWITHMD5ANDDES");

        while(true) {
            String str;
            String flag;
            Scanner scanner = new Scanner(System.in);

            System.out.println("1: 암호화 / 2: 복호화");
            flag = scanner.next();
            if(flag.equals("1")){
                System.out.println("암호화할 문자열:");
                str = scanner.next();
                System.out.println("");

                String encrypt = encryptor.encrypt(str);

                System.out.println("encrypt: " + encrypt);

            }else if(flag.equals("2")){
                System.out.println("복호화할 문자열:");

                str = scanner.next();
                String decrypt = encryptor.decrypt(str);

                System.out.println("decrypt: " + decrypt);

            }else {
                System.out.println("1,2 중에서 재입력");
                continue;
            }
        }
    }

}
