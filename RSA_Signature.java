import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.util.Arrays;


import javax.crypto.Cipher;

public class RSA_Signature {
    public static KeyPair pair;

    public static void init() throws Exception{
        pair = generateKeyPair();
    }

    public static void createSign() throws Exception {

        byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));

        //Encrypt the message
        encryption(messageBytes, pair.getPrivate());

        //Now decrypt it
        //String decipheredMessage = decrypt(cipherText, pair.getPrivate());

        System.out.println("Signature Saved on signature.txt file");
        //System.out.println(decipheredMessage);
    }

    public static void verification() throws Exception{
        byte[] encryptedMessageHash = Files.readAllBytes(Paths.get("signature.txt"));

        byte[] decryptedMessageHash = decryption(encryptedMessageHash);

        byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] newMessageHash = md.digest(messageBytes);
        
        //Let's check the signature
        boolean isCorrect = Arrays.equals(decryptedMessageHash, newMessageHash);
        System.out.println("Signature correct: " + isCorrect);
    }
    
    private static void encryption(byte[] messageBytes, PrivateKey privateKey) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageHash = md.digest(messageBytes);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] digitalSignature = cipher.doFinal(messageHash);
        Files.write(Paths.get("signature.txt"), digitalSignature);
    }

    private static byte[] decryption(byte[] encryptedMessageHash) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pair.getPublic());
        
        byte[] decryptedMessageHash = cipher.doFinal(encryptedMessageHash);
    
        return decryptedMessageHash;
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
    
        return pair;
    }

}

//© https://niels.nu/blog/2016/java-rsa?fbclid=IwAR3mKaOCJNk5v3ric_xnZJ2H3Rd1W8mgoh0jWiJJ-3K_d6LoEp25jcD9ZuU
//© https://www.baeldung.com/java-digital-signature?fbclid=IwAR3j1OP4785ZVvL0pX5K9YrJ2Bw0iHR0fI5WZ_mOHpoRJ-h1O690u-u16rc