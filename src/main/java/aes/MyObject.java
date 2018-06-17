package aes;

import java.io.Serializable;

public class MyObject implements Serializable {
    
    String name;
    int value;
    
    public MyObject(String name, int value) {
        this.name = name;
        this.value = value;
    }
    
    
}
