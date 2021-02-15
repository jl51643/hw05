package hr.fer.oprpp1.hw05.crypto;

/**
 * Util class for converting bytes to hexadecimal and vice versa
 */
public class Util {

    /**
     * Returns hexadecimal string from given array of bytes
     *
     * @param byteArray array of bytes to convert
     * @returnceturns hexadecimal string from given array of bytes
     */
    public static String byteToHex(byte[] byteArray) {

        String hex = "";
        for (byte digit : byteArray) {
            int b = digit;
            String hexDigit = "";
            if (b < 0) {
                b = 256 + b;
            }
            while (b != 0) {
                int reminder = b % 16;
                switch (reminder) {
                    case 10 -> hexDigit = 'a' + hexDigit;
                    case 11 -> hexDigit = 'b' + hexDigit;
                    case 12 -> hexDigit = 'c' + hexDigit;
                    case 13 -> hexDigit = 'd' + hexDigit;
                    case 14 -> hexDigit = 'e' + hexDigit;
                    case 15 -> hexDigit = 'f' + hexDigit;
                    default -> hexDigit = reminder + hexDigit;
                }
                b = b / 16;
            }
            if (hexDigit.length() == 1)
                hexDigit = "0" + hexDigit;
            if (hexDigit.length() == 0)
                hexDigit = "00" + hexDigit;
            hex += hexDigit;
        }

        return hex;
    }

    /**
     * Returns array of bytes from hexadecimal string
     *
     * @param keyText hexadecimal string
     * @return returns array of bytes from hexadecimal string
     */
    public static byte[] hexToByte(String keyText) {
        if (keyText.length() % 2 != 0)
            throw new IllegalArgumentException("Odd-sized text");

        byte[] byteArray;// = new byte[keyText.length()/2];
        String bin = "";
        for (char c : keyText.toCharArray()) {
            bin += hexToBin(c);
        }

        byteArray = binToByte(bin);
        return byteArray;
    }

    /**
     * Returns array of bites from binary string
     *
     * @param s binary string
     * @return returns array of bites from binary string
     */
    private static byte[] binToByte(String s) {
        byte[] bytes = new byte[s.length()/8];


        int index = 0;
        while (!s.equals("")) {

            String byteDigit = s.substring(0,8);
            if (byteDigit.charAt(0) == '1') {
                byteDigit = negate(byteDigit);
            }

            //binToDec
            byte number = 0;
            for (int i = byteDigit.length()-1, potention = 0; i >= 0; i--, potention++)
                number += Integer.parseInt(String.valueOf(s.charAt(i))) * Math.pow(2, potention);

            bytes[index++] = number;
            s = s.substring(8);
        }

        return bytes;
    }

    /**
     * Returns binary string as negated number
     *
     * @param byteDigit binary number
     * @return returns binary string as negated number
     */
    private static String negate(String byteDigit) {
        String negated = "";
        for (char c : byteDigit.toCharArray()) {
            if (c == '0')
                c = '1';
            else c = '0';
            negated += c;
        }
        return negated;
    }

    /**
     * Returns binary string from given hexadecimal string
     * @param digit hexadecimal string
     * @return returns binary string from given hexadecimal string
     */
    private static String hexToBin(char digit) {
        switch (digit) {
            case '0' -> {
                return "0000";
            }
            case '1' -> {
                return "0001";
            }
            case '2' -> {
                return "0010";
            }
            case '3' -> {
                return "0011";
            }
            case '4' -> {
                return "0100";
            }
            case '5' -> {
                return "0101";
            }
            case '6' -> {
                return "0110";
            }
            case '7' -> {
                return "0111";
            }
            case '8' -> {
                return "1000";
            }
            case '9' -> {
                return "1001";
            }
            case 'a','A' -> {
                return "1010";
            }
            case 'b','B' -> {
                return "1011";
            }
            case 'c','C' -> {
                return "1100";
            }
            case 'd','D' -> {
                return "1101";
            }
            case 'e','E' -> {
                return "1110";
            }
            case 'f','F' -> {
                return "1111";
            }
            default -> {
                throw new IllegalArgumentException("Illegal symbol " + digit);
            }
        }
    }
}
