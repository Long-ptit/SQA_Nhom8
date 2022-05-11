package com.example.sqa_nhom8.service;

import com.example.sqa_nhom8.entitis.Goods;

import java.util.List;


public interface GoodService {

    List<Goods> getListGood();
    Goods getGoodById(int id);
    Goods saveGood(Goods good);

}
