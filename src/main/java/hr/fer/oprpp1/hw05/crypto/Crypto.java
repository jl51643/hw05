package hr.fer.oprpp1.hw05.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

/**
 * Encrypts/decrypts file and makes checksum
 */
public class Crypto {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

        if (args.length == 0) {
            System.out.println("Inappropriate number of arguments: " + args.length);
            return;
        }

        Scanner sc = new Scanner(System.in);

        switch (args[0]) {
            case "checksha" -> {
                if (args.length != 2) {
                    System.out.println("Inappropriate number of arguments: " + args.length);
                    return;
                }
                String digest = checksha(args[1]);
                System.out.println("Please provide expected sha-256 digest for " + args[1]);
                String expected = sc.nextLine();
                if (digest.equals(expected))
                    System.out.println("Digesting completed. Digest of " + args[1] + " matches expected digest.");
                else
                    System.out.println("Digesting completed. Digest of " + args[1] + " does not match the expected digest. Digest was: " + digest);
            }
            case "encrypt", "decrypt" -> {
                if (args.length != 3) {
                    System.out.println("Inappropriate number of arguments: " + args.length);
                    return;
                }
                System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
                String password = sc.nextLine();
                System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
                String vector = sc.nextLine();
                if (args[0].equals("encrypt")) {
                    encrypt_decrypt(args[1], args[2], password, vector, Cipher.ENCRYPT_MODE);
                    System.out.println("Encryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
                } else {
                    encrypt_decrypt(args[1], args[2], password, vector, Cipher.DECRYPT_MODE);
                    System.out.println("Encryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
                }
            }

        }

    }

    /**
     * Encrypts or decrypts file
     *
     * @param inputFile input file
     * @param outputFile output file
     * @param password key for encrypting/decrypting
     * @param vector vector for encrypting/decrypting
     * @param cipherMode encrypting/decrypting mode
     * @throws NoSuchPaddingException if encrypting/decrypting problem occurs
     * @throws NoSuchAlgorithmException if encrypting/decrypting problem occurs
     * @throws InvalidAlgorithmParameterException if encrypting/decrypting problem occurs
     * @throws InvalidKeyException if encrypting/decrypting problem occurs
     * @throws IOException if I/O problem occurs
     * @throws BadPaddingException if encrypting/decrypting problem occurs
     * @throws IllegalBlockSizeException if encrypting/decrypting problem occurs
     */
    private static void encrypt_decrypt(String inputFile, String outputFile, String password, String vector, int cipherMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {

        String keyText = password;
        String ivText = vector;
        SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(cipherMode, keySpec, paramSpec);

        byte[] inputBuffer = new byte[4096];//4kB

        //InputStream is = new FileInputStream(inputFile);
        //OutputStream os = new FileOutputStream(outputFile);
        InputStream is = Files.newInputStream(Paths.get(inputFile));
        OutputStream os = Files.newOutputStream(Paths.get(outputFile));

        while (true) {
            int offset = 0;
            int len = is.read(inputBuffer);

            if (len == -1)
                break;

            byte[] outputBuffer = cipher.update(inputBuffer, offset, len);

            if (outputBuffer != null)
                os.write(outputBuffer);
        }
        os.write(cipher.doFinal());
    }


    /**
     * Returns checksum of file
     *
     * @param inputFile file
     * @return returns checksum of file
     * @throws IOException if an I/O problem occures
     */
    private static String checksha(String inputFile) throws IOException {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[4096];//4kB

        //InputStream is = new FileInputStream(inputFile);
        InputStream is = Files.newInputStream(Paths.get(inputFile));

        while (true) {
            int offset = 0;
            int len = is.read(buffer);
            if (len == -1)
                break;
            digest.update(buffer, offset, len);
            //offset += len;
        }

        return Util.byteToHex(digest.digest());
    }

}
