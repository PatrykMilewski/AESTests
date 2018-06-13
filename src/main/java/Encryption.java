import java.io.*;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    
    public EncryptedData encryptStream(String data, String password) throws Exception {
        byte[] ivBytes;
        
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        byte[] saltBytes = bytes;
        
        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),saltBytes,4048,256);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        
        //encrypting the word
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        ivBytes =   params.getParameterSpec(IvParameterSpec.class).getIV();
    
        
        
        byte[] serializedData = data.getBytes();
        
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             CipherOutputStream cipherOutputStream = new CipherOutputStream(byteArrayOutputStream, cipher);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(cipherOutputStream)) {
    
            objectOutputStream.write(serializedData);
            
            
            byte[] encrypted = byteArrayOutputStream.toByteArray();
            if (encrypted.length == 0)
                System.out.println("Puste po zaszyfrowaniu!");
            
            return new EncryptedData(byteArrayOutputStream.toByteArray(), saltBytes, ivBytes);
        }
    }
    
    // ok dziala
    public EncryptedData encrypt(String data, String password) throws Exception {
        byte[] ivBytes;
        
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        byte[] saltBytes = bytes;
        
        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),saltBytes,4048,256);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        
        //encrypting the word
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        
        byte[] serializedData = data.getBytes();
        
        byte[] encryptedData = cipher.doFinal(serializedData);
        if (encryptedData.length == 0)
            System.out.println("Puste po zaszyfrowaniu!");
    
        return new EncryptedData(encryptedData, saltBytes, ivBytes);
    }
    
}