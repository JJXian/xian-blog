package com.xian.entity;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * @Author: jjxian
 * @CreateTime: 2024-06-03
 */
public class test {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        System.out.print("请输入FinalShell的离线机器码：");
        @SuppressWarnings("resource")
        Scanner reader = new Scanner(System.in);
        String machineCode = reader.nextLine();
        generateKey(machineCode);
    }

    public static void generateKey(String hardwareId) throws NoSuchAlgorithmException {
        String proKey = transform(61305 + hardwareId + 8552);
        String pfKey = transform(2356 + hardwareId + 13593);
        System.out.println("请将此行复制到离线激活中-高级版：" + proKey);
        System.out.println("请将此行复制到离线激活中-专业版：" + pfKey);
    }

    public static String transform(String str) throws NoSuchAlgorithmException {

        @SuppressWarnings("unused")
        String md5 = hashMD5(str);

        return hashMD5(str).substring(8, 24);
    }

    public static String hashMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hashed = digest.digest(str.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashed) {
            int len = b & 0xFF;
            if (len < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(len));
        }
        return sb.toString();
    }
}
