package com.example.barcodescannerv3;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor @Setter @Getter
public class Item {
    private String name;
    private String ean;
    private String old_amount;
    private String new_amount;

    public Item() {

    }
}
