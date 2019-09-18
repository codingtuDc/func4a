package func4j;

import java.security.MessageDigest;

public class MD5 {

    static char hexDigits[] = {'0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'a',
            'b',
            'c',
            'd',
            'e',
            'f'};

    public static String md5(String content) {
        return md5(content, 1);
    }

    public static String md5(String content, int times) {
        byte[] md = md5Bytes(content, times);

        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);

    }

    public static byte[] md5Bytes(String content, int times) {
        try {
            MessageDigest md5 = getMd5();
            byte[] data = content.getBytes("utf-8");
            for (int i = 0; i < times; i++) {
                data = md5.digest(data);
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    private static MessageDigest getMd5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return null;
        }
    }

}
