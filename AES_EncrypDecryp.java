import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AES_EncrypDecryp {
    public static String Mode;
    public static int keyLength;
    public static SecretKey key;

    public static void encryption(String mode, int length) throws Exception{
        Mode = mode;
        keyLength = length; 
        key = generateKey();

        //String algorithm = "CBC";
        String filePath = ClassLoader.getSystemResource("message.txt").getFile();
        String encryptedfilePath = ClassLoader.getSystemResource("AESEncrypMessage.txt").getFile();

        if(Mode == "CFB"){
            IvParameterSpec ivParameterSpec = generateIv();
            encryptFileCFB(ivParameterSpec, filePath, encryptedfilePath);
        }

        else encryptFileECB(filePath, encryptedfilePath);

        System.out.println("Encrypted file is saved as encrypMessage.txt. Mode: "+ Mode +" Key Length: "+ keyLength);
    }

    public static void decryption() throws Exception{

        String encryptedfilePath = ClassLoader.getSystemResource("AESEncrypMessage.txt").getFile();

        if(Mode == "CFB"){
            IvParameterSpec ivParameterSpec = generateIv();
            System.out.println(decryptFileCFB(ivParameterSpec, encryptedfilePath));
        }
        else System.out.println(decryptFileECB(encryptedfilePath));
        
    }

    private static void encryptFileCFB(IvParameterSpec iv, String inputFile, String outputFile) throws Exception {
 
        Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }

        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }
        inputStream.close();
        outputStream.close();
    
    }
    private static void encryptFileECB(String inputFile, String outputFile) throws Exception {
 
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }

        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }
        inputStream.close();
        outputStream.close();
    
    }

    private static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keyLength);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }
    private static String decryptFileCFB(IvParameterSpec ivParameterSpec, String encryptedfilePath) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        FileInputStream in = new FileInputStream(encryptedfilePath);
        
        byte[] ibuf = in.readAllBytes();
        
        byte[] obuf = cipher.doFinal(ibuf);

        return new String(obuf);   
    }

    private static String decryptFileECB(String encryptedfilePath) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, key);
        FileInputStream in = new FileInputStream(encryptedfilePath);
        
        byte[] ibuf = in.readAllBytes();
        
        byte[] obuf = cipher.doFinal(ibuf);

        return new String(obuf); 
    }
        
    
}

//© https://www.baeldung.com/java-aes-encryption-decryption?fbclid=IwAR3eYH4d8m4ufJn7hwwWq-9HENowH4xiHFXYP_ObqWQUulVYtSy1dGoCoHA