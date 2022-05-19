package com.example.sqa_nhom8.entitis;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@Entity
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 30, min = 6, message = "Tên không được để trống, và phải có từ 6-30 kí tự!!")
    private String name;
    @Size(min = 10, max = 11, message = "Số điện thoại không được để trống, phải có 10 hoặc 11 số!")
    private String phone;
    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Trường phải là Email!")
    private String email;
    @NotBlank(message = "Địa chỉ không được để trống!")
    private String address;
    private int totalCoins;
    private int isActive;

    public Customer(String name, String phone, String email, String address, int totalCoins, int isActive) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.totalCoins = totalCoins;
        this.isActive = isActive;
    }
}
