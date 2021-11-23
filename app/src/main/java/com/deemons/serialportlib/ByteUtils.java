package com.deemons.serialportlib;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class ByteUtils {
    private static final String HEX = "0123456789ABCDEF";

    public static int bytesToInt(byte b, byte b2) {
        return (b & 255) | ((b2 & 255) << 8);
    }

    public static long bytesToLong(byte b, byte b2, byte b3, byte b4) {
        return (long) ((b & 255) | ((b2 & 255) << 8) | ((b3 & 255) << 16) | ((b4 & 255) << 24));
    }

    public static byte charToByte(char c) {
        return (byte) HEX.indexOf(c);
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4));
        }
        return bArr;
    }

    public static String byteToUnsignedHex(int i) {
        String hex = Integer.toHexString(i);
        while(hex.length() < 2){
            hex = "0" + hex;
        }
        return hex;
    }

    public static String intArrToHex(int[] arr) {
        StringBuilder builder = new StringBuilder(arr.length * 8);
        for (int b : arr) {
            builder.append(byteToUnsignedHex(b));
        }
        return builder.toString();
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length);
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString.toUpperCase());
        }
        return sb.toString();
    }

    public static String[] bytesToHexStringArray(byte[] bArr) {
        String[] sb = new String[bArr.length];

        for (int i=0;i<= bArr.length;i++) {
            String hexString = Integer.toHexString(bArr[i] & 255);
            if (hexString.length() < 2) {
                sb[i]="0";
            }
            sb[i]=hexString.toUpperCase();
        }
        return sb;
    }

    public static long bytesToLong(byte[] bArr) {
        if (bArr.length != 4) {
            return -1;
        }
        return bytesToLong(bArr[0], bArr[1], bArr[2], bArr[3]);
    }

    public static byte[] longToBytes(long j) {
        return new byte[]{(byte) ((int) (j & 255)), (byte) ((int) ((j >> 8) & 255)), (byte) ((int) ((j >> 16) & 255)), (byte) ((int) ((j >> 24) & 255))};
    }

    public static byte[] intToBytes(int i) {
        byte[] bArr = new byte[4];
        bArr[3] = (byte) ((i >> 24) & 255);
        bArr[2] = (byte) ((i >> 16) & 255);
        bArr[1] = (byte) ((i >> 8) & 255);
        bArr[0] = (byte) (i & 255);
        return bArr;
    }

    public static byte[] getBytes(char[] cArr) {
        Charset forName = Charset.forName("UTF-8");
        CharBuffer allocate = CharBuffer.allocate(cArr.length);
        allocate.put(cArr);
        allocate.flip();
        return forName.encode(allocate).array();
    }

    public static byte getByte(char c) {
        Charset forName = Charset.forName("UTF-8");
        CharBuffer allocate = CharBuffer.allocate(1);
        allocate.put(c);
        allocate.flip();
        return forName.encode(allocate).array()[0];
    }

    public static char[] getChars(byte[] bArr) {
        Charset forName = Charset.forName("UTF-8");
        ByteBuffer allocate = ByteBuffer.allocate(bArr.length);
        allocate.put(bArr);
        allocate.flip();
        return forName.decode(allocate).array();
    }

    public static char getChar(byte b) {
        Charset forName = Charset.forName("UTF-8");
        ByteBuffer allocate = ByteBuffer.allocate(1);
        allocate.put(b);
        allocate.flip();
        return forName.decode(allocate).array()[0];
    }

    public static byte[] stringToBytes(String str) {
        return stringToBytes(str, 0);
    }

    public static byte[] stringToBytes(String str, int i) {
        byte[] bytes = getBytes((str == null || str.length() == 0) ? new char[0] : str.toCharArray());
        if (i < bytes.length) {
            i = bytes.length;
        }
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < bytes.length; i2++) {
            bArr[i2] = bytes[i2];
        }
        return bArr;
    }

    public static byte[] copy(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    public static byte[] reverse(byte[] bArr) {
        for (int i = 0; i <= (bArr.length / 2) - 1; i++) {
            byte b = bArr[i];
            bArr[i] = bArr[(bArr.length - i) - 1];
            bArr[(bArr.length - i) - 1] = b;
        }
        return bArr;
    }

    public static String bytesToAscii(byte[] bArr, int i, int i2) {
        String str;
        if (bArr == null || bArr.length == 0 || i < 0 || i2 <= 0 || i >= bArr.length || bArr.length - i < i2) {
            return null;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        try {
            str = new String(bArr2, "ISO-8859-1");
        } catch (UnsupportedEncodingException unused) {
            str = null;
        }
        return str;
    }

    public static String bytesToAscii(byte[] bArr, int i) {
        return bytesToAscii(bArr, 0, i);
    }

    public static String bytesToAscii(byte[] bArr) {
        return bytesToAscii(bArr, 0, bArr.length);
    }

    public static byte[] stringToAsciiBytes(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}