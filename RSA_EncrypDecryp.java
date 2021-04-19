import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class RSA_EncrypDecryp {
    public static boolean generateKey = false;

    public static void encryption() throws Exception{
        if(generateKey == false){
            init();
        }
        
        String filePath = ClassLoader.getSystemResource("message.txt").getFile();
        String encryptedfilePath = ClassLoader.getSystemResource("encrypMessage.txt").getFile();
        encrypFile(filePath, encryptedfilePath);
        System.out.println("Encrypted file is saved as encrypMessage.txt");
    }

    public static void decryption() throws Exception{
        if(generateKey == false){
            init();
        }
        
        String encryptedfilePath = ClassLoader.getSystemResource("encrypMessage.txt").getFile();
        String output = decrypFile(encryptedfilePath);
        System.out.println(output);
    }

    private static void init() throws Exception{
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048, secureRandom);
        KeyPair pair = kpg.generateKeyPair();

        try (FileOutputStream out = new FileOutputStream("PrivateKey.key")) {
            out.write(pair.getPrivate().getEncoded());
        }
        
        try (FileOutputStream out = new FileOutputStream("PublicKey.key")) {
            out.write(pair.getPublic().getEncoded());
        }

        generateKey = true;
    }

    private static void encrypFile(String filePath, String encryptedFilePath) throws Exception{
        byte[] bytes = Files.readAllBytes(Paths.get("PrivateKey.key"));
        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey pvt = kf.generatePrivate(ks);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pvt);

        FileInputStream in = new FileInputStream(filePath);
        FileOutputStream out = new FileOutputStream(encryptedFilePath);
        processFile(cipher, in, out);
        
    }

    private static void processFile(Cipher cipher, FileInputStream in, FileOutputStream out) throws Exception{
        int len;
        byte[] ibuf = new byte[1024];
        while ((len = in.read(ibuf)) != -1) {
            byte[] obuf = cipher.update(ibuf, 0, len);
            if ( obuf != null ) out.write(obuf);
        }
        byte[] obuf = cipher.doFinal();
        if ( obuf != null ) out.write(obuf);
    }

    private static String decrypFile(String encryptedFilePath) throws Exception{

        byte[] bytes = Files.readAllBytes(Paths.get("PublicKey.key"));
        X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey pub = kf.generatePublic(ks);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pub);
        FileInputStream in = new FileInputStream(encryptedFilePath);
        
        byte[] ibuf = in.readAllBytes();
        
        byte[] obuf = cipher.doFinal(ibuf);

        return new String(obuf);       
    }

}

//© https://www.novixys.com/blog/rsa-file-encryption-decryption-java/?fbclid=IwAR2Nu92vDGPPbz6Oo5GDgxEoWjBAq1G7zTIgc7SkOvGOeJRcmcwXqEhjHDI
//© https://stackoverflow.com/questions/31944374/badpaddingexception-decryption-error