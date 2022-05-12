package com.example.sqa_nhom8.entitis;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int amount;
    private int price;
    private int totalPrice;

    @ManyToOne()
    @JoinColumn(name = "id_goods")
    private Goods goods;

    @ManyToOne()
    @JoinColumn(name = "id_bill")
    private Bill bill;

    public CartItem(String name, int amount, int price, int totalPrice, Goods goods, Bill bill) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.totalPrice = totalPrice;
        this.goods = goods;
        this.bill = bill;
    }

    public CartItem() {

    }

    public CartItem(int totalPrice, int amount) {
        this.totalPrice = totalPrice;
        this.amount = amount;
    }
}
