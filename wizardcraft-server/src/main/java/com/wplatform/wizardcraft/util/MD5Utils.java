package com.wplatform.wizardcraft.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String md5(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] buffer = digest.digest(text.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : buffer) {
                int a = b & 0xff;
                String hex = Integer.toHexString(a);
                if (hex.length() == 1) {
                    hex = 0 + hex;
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
