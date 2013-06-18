/*
 * Copyright (c) 2011, The Board of Trustees of the Leland Stanford Junior 
 * University, creator Daniel L. Rubin. 
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this 
 * list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.stanford.hakan.aim3api.utility;

//import java.security.MessageDigest;
import java.util.Random;

//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
/**
 *
 * @author Hakan
 */
public class GenerateId {
//    static private String hexEncode(byte[] aInput) {
//        StringBuilder result = new StringBuilder();
//        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
//        for (int idx = 0; idx < aInput.length; ++idx) {
//            byte b = aInput[idx];
//            result.append(digits[ (b & 0xf0) >> 4]);
//            result.append(digits[ b & 0x0f]);
//        }
//        return result.toString();
//    }
//
//    static public String getUUID() {
//        try {
//            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
//
//            //generate a random number
//            String randomNum = new Integer(prng.nextInt()).toString();
//
//            //get its digest
//            MessageDigest sha = MessageDigest.getInstance("SHA-1");
//            byte[] result = sha.digest(randomNum.getBytes());
//            return hexEncode(result);
//        } catch (NoSuchAlgorithmException ex) {
//            return "";
//        }
//    }

    static public String getUUID() {
        String res = "";
        int idLenght = 40;
        Random randomGenerator = new Random();
        char[] bag = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 't', 'u', 'v', 'y', 'z', 'w', 'x', 'q'};
        for (int i = 0; i < idLenght; i++) {
            int randomInt = randomGenerator.nextInt(bag.length);
            res = res.concat(String.valueOf(bag[randomInt]));
        }
        StringBuilder sb = new StringBuilder(res);
//        Date currentTime = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        String strDate = sdf.format(currentTime);
//        List<Integer> listControl = new ArrayList<Integer>();
//        for (int i = 0; i < strDate.length(); i++) {
//            int randomInt = randomGenerator.nextInt(sb.length());
//            while (listControl.contains(randomInt)) {
//                randomInt = randomGenerator.nextInt(sb.length());
//            }
//            listControl.add(randomInt);
//            sb = sb.insert(randomInt, strDate.charAt(i));
//        }
        return sb.toString();
    }
}
