package aes;

import aes.ciphers.AESEncryptedObject;
import aes.ciphers.RSAAlgorithmData;
import aes.ciphers.RSACipherData;
import aes.ciphers.RSAEncryptedObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;

public class Main {

    static Cryptography cryptography = new Cryptography();
    static String password = "123456";

    public static void main(String args[]) throws Exception {
    
        File notEncryptedFile = new File("C:\\Work\\file.txt");
        File encryptedFile = new File("C:\\Work\\file.enc");
    
        RSACipherData rsaCipherData = new RSACipherData(new RSAAlgorithmData());
        KeyPair keyPair = rsaCipherData.getKeyPair();
        
        try {
            
            MyObject myObject = new MyObject("Nazwa obiektu", 987654321);
            AESEncryptedObject<MyObject> aesEncryptedObject = cryptography.encryptObject(myObject, password, EncryptionBlockModeType.CBC);
            RSAEncryptedObject<MyObject> rsaEncryptedObject = cryptography.encryptObject(myObject, keyPair.getPublic());
            MyObject decryptedObjectAES = cryptography.decryptObject(aesEncryptedObject, password);
            MyObject decryptedObjectRSA = cryptography.decryptObject(rsaEncryptedObject, keyPair.getPrivate());
            System.out.println(decryptedObjectAES.name);
            System.out.println(decryptedObjectAES.value);
            System.out.println(decryptedObjectRSA.name);
            System.out.println(decryptedObjectRSA.value);
        } catch (Exception e) {
            e.getStackTrace();
        }
        
        
        try (FileInputStream fileInputStream = new FileInputStream(notEncryptedFile);
             FileOutputStream fileOutputStream = new FileOutputStream(encryptedFile)) {
            cryptography.encryptStream(fileInputStream, fileOutputStream, keyPair.getPublic());
        }
        
        System.in.read();
        
        try (FileInputStream fileInputStream = new FileInputStream(encryptedFile);
             FileOutputStream fileOutputStream = new FileOutputStream(notEncryptedFile)) {
    
            cryptography.decryptStream(fileInputStream, fileOutputStream, keyPair.getPrivate());
        }
        
        System.in.read();
        
        try (FileInputStream fileInputStream = new FileInputStream(notEncryptedFile);
             FileOutputStream fileOutputStreamAppend = new FileOutputStream(encryptedFile)) {
            cryptography.encryptStream(fileInputStream, fileOutputStreamAppend, password, EncryptionBlockModeType.ECB);
        }

        System.in.read();
        
        try (FileInputStream fileInputStreamEncryptedData = new FileInputStream(encryptedFile);
             FileOutputStream fileOutputStreamDecryptedData = new FileOutputStream(notEncryptedFile)) {
            cryptography.decryptStream(fileInputStreamEncryptedData, fileOutputStreamDecryptedData, password);
        }
    }
}
