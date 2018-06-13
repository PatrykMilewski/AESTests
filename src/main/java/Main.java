import java.io.IOException;

public class Main {
    
    static Decryption decryption = new Decryption();
    static Encryption encryption = new Encryption();
    static String password = "123456";
    static String data = "raz dwa trzy";
    
    public static void main(String args[]) throws IOException {
    
        try {
            dziala();
            nieDziala();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.in.read();
    }
    
    private static void dziala() throws Exception {
        EncryptedData encrypted = encryption.encrypt(data, password);
        EncryptedData decrypted = decryption.decrypt(encrypted, "123456");
        if (data.equals(decrypted.getOriginalObject()))
            
            System.out.println("Zgadza sie.");
        else
            System.out.println("Nie zgadza sie.");
    }
    
    private static void nieDziala() throws Exception {
        EncryptedData encrypted = encryption.encryptStream(data, password);
        EncryptedData decrypted = decryption.decrypt(encrypted, "123456");
        
        if (data.equals(decrypted.getOriginalObject()))
            System.out.println("Zgadza sie.");
        else
            System.out.println("Nie zgadza sie.");
    }
    
    
    
    
}
