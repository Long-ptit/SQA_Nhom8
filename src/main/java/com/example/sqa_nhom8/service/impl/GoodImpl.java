package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.Goods;
import com.example.sqa_nhom8.repository.GoodRepository;
import com.example.sqa_nhom8.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoodImpl implements GoodService {

    @Autowired
    GoodRepository goodRepository;

    @Override
    public List<Goods> getListGood() {
        return goodRepository.findAll();
    }

    @Override
    public Goods getGoodById(int id) {
        if (goodRepository.findById(id).isPresent()) {
            return goodRepository.getById(id);
        }
        return null;
    }

    @Override
    public Goods saveGood(Goods good) {
        return goodRepository.save(good);
    }
}
