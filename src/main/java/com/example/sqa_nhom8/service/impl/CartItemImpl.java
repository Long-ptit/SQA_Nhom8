package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.CartItem;
import com.example.sqa_nhom8.entitis.Goods;
import com.example.sqa_nhom8.repository.CartItemRepository;
import com.example.sqa_nhom8.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemImpl implements CartItemService {

    @Autowired
    CartItemRepository repository;

    @Override
    public CartItem saveItem(CartItem item) {
       return repository.save(item);
    }

    @Override
    public List<CartItem> getCartItemByIdBill(int id) {
        if (repository.getCartItemByIdBill(id) == null) {
            return new ArrayList<>();
        }
        return repository.getCartItemByIdBill(id);
    }

    @Override
    public int getSumOfListCart(List<CartItem> list) {
        int tongTien = 0;
        for (CartItem item : list) {
            tongTien += item.getTotalPrice();
        }
        return tongTien;
    }

    @Override
    public int getSumQuantityCart(List<CartItem> list) {
        int tongSoLuong = 0;
        for (CartItem item : list) {
            tongSoLuong += item.getAmount();
        }
        return tongSoLuong;
    }

    @Override
    public int getPriceFromCartItem(int quantity, int price) {
        return quantity*price;
    }

    @Override
    public boolean checkExistCartItem(List<CartItem> list, Goods goods) {
        for (CartItem item : list) {
            if (item.getGoods().getId() == goods.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<CartItem> handleDeleteCartItem(long id, List<CartItem> list) {
        List<CartItem> itemList = list;
        for (CartItem item : itemList) {
            if (item.getGoods().getId() == id) {
                itemList.remove(item);
                break;
            }
        }
        return itemList;
    }

    @Override
    public List<CartItem> handleEditCartItem(long id,int soLuong, List<CartItem> list) {
        List<CartItem> itemList = list;
        for (CartItem item : itemList) {
            if (item.getGoods().getId() == id) {
                item.setAmount(soLuong);
                item.setTotalPrice(soLuong*item.getPrice());
                break;
            }
        }
        return itemList;
    }
}
