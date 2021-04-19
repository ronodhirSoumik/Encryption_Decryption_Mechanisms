import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA_Hashing {

    private static byte[] checksum(String filePath, String algorithm) {

        //System.out.println(filePath);

        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        try (InputStream is = new FileInputStream(filePath);
             DigestInputStream dis = new DigestInputStream(is, md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return md.digest();

    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void hash() {

        String algorithm = "SHA3-256";

        // get file path from resources
        String filePath = ClassLoader.getSystemResource("message.txt").getFile();

        byte[] hashInBytes = checksum(filePath, algorithm);
        System.out.println(bytesToHex(hashInBytes));


    }
}

//© https://mkyong.com/java/java-sha-hashing-example/
