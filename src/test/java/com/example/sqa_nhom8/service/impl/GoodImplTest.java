package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.Goods;
import com.example.sqa_nhom8.repository.GoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
@Transactional
@Rollback
class GoodImplTest {

    @Autowired
    GoodRepository goodRepository;

    @Autowired
    GoodImpl goodImp;

    @Test
    @Rollback(true)
    @Transactional
    void testSaveGood() {
        Goods goods = new Goods();
        goods.setName("long dep trai");
        goods.setPrice(10);
        goods.setIsActive(1);;
        Goods goods1 =  goodImp.saveGood(goods);
    }


    @Test
    void testGetListGood() {
        int expectedSize = 6;
        assertEquals(expectedSize, goodImp.getListGood().size());
    }

    @Test
    void testGetFirstValueListGood() {
        List<Goods> list = goodImp.getListGood();
        Goods good = list.get(0);
        Goods goods1 = goodRepository.getById(1);
        assertEquals(goods1, good);
    }

    @Test
    void testGetSecondValueListGood() {
        List<Goods> list = goodImp.getListGood();
        Goods good = list.get(1);
        Goods goods1 = goodRepository.getById(2);

        assertEquals(goods1, good);
    }

    @Test
    void testGetFinalValueListGood() {
        List<Goods> list = goodImp.getListGood();
        Goods good = list.get(list.size() - 1);
        Goods goods1 = goodRepository.getById(6);

        assertEquals(goods1, good);
    }

    @Test
    void testGetMidValueListGood() {
        List<Goods> list = goodImp.getListGood();
        Goods good = list.get(2);
        Goods goods1 = goodRepository.getById(3);
        assertEquals(goods1, good);
    }

    @Test
    void getGoodByIdSuccess() {
       int expectedId = 1;
        assertEquals(expectedId, goodImp.getGoodById(1).getId());
    }

    @Test
    void getGoodByIdFailure() {
        assertNull(goodImp.getGoodById(-1));
    }





}