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

    public void encryption(String mode, int length) throws Exception{
        Mode = mode;
        keyLength = length;
        givenFile_whenEncrypt_thenSuccess();
    }

    private void givenFile_whenEncrypt_thenSuccess() throws Exception {
        
        SecretKey key = AESUtil.generateKey(Mode);
        String algorithm = "CBC";
        IvParameterSpec ivParameterSpec = AESUtil.generateIv();
        Resource resource = new ClassNameSourceProvider("inputFile/baeldung.txt");
        File inputFile = resource.getFile();
        File encryptedFile = new File("classpath:baeldung.encrypted");
        File decryptedFile = new File("document.decrypted");
        AESUtil.encryptFile(algorithm, key, ivParameterSpec, inputFile, encryptedFile);
        AESUtil.decryptFile(
        algorithm, key, ivParameterSpec, encryptedFile, decryptedFile);
        assertThat(inputFile).hasSameTextualContentAs(decryptedFile);
    }

    private static void encryptFile(String algorithm, SecretKey key, IvParameterSpec iv, File inputFile, File outputFile) throws Exception {
 
        Cipher cipher = Cipher.getInstance(algorithm);
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

    private static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }
}

//© 