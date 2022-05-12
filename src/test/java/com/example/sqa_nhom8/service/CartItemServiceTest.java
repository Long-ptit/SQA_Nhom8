package com.example.sqa_nhom8.service;

import com.example.sqa_nhom8.entitis.Bill;
import com.example.sqa_nhom8.entitis.CartItem;
import com.example.sqa_nhom8.entitis.Goods;
import com.example.sqa_nhom8.repository.CartItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(true)
@Transactional
class CartItemServiceTest {

    @Autowired
    CartItemService cartItemService;

    @Autowired
    CartItemRepository cartRepository;

    @Autowired
    GoodService goodService;

    @Autowired
    BillService billService;

    @Test
    @Rollback
    @Transactional
    void testSaveCartSucess() {
        List<Goods> listGood = goodService.getListGood();
        Goods good = listGood.get(0);
        Bill bill = billService.getBillByIdBill(1);
        CartItem cartItem = new CartItem(
                good.getName(),
                1,
                good.getPrice(),
                0,
                good.getPrice()*1,
                good,
                bill
        );
        CartItem item = cartItemService.saveItem(cartItem);
        CartItem itemExpected = cartRepository.getById(item.getId());
        assertEquals(itemExpected, item);
    }

    //chưa đúng lắm
    @Test
    void testGetCartItemByIdBillSuccess() {
        int id = 1;
        List<CartItem> list = cartItemService.getCartItemByIdBill(id);
        //kiểm tra nếu list khác 0 thì chạy đúng
        boolean check = true;
        for (CartItem item: list) {
            if (item.getBill().getId() != id) {
                check = false;
                break;
            }
        }
        assertEquals(true, check);
    }


    @Test
    void testGetCartItemByIdBillFailure() {
        int id = -1;
        List<CartItem> list = cartItemService.getCartItemByIdBill(id);
        //kiểm tra nếu list khác 0 thì chạy đúng
        assertEquals(0, list.size());
    }

    @Test
    void testGetCartItemByIdBillFirst() {
        CartItem cartItemFirst = cartItemService.getCartItemByIdBill(1).get(0);
        int expectedId = 6;
        //kiểm tra nếu list khác 0 thì chạy đúng
        assertEquals(expectedId, cartItemFirst.getId());
    }

    @Test
    void testGetCartItemByIdBillFinal() {
        int id = 1;
        //trường hợp chỉ có 1 item, vẫn đúng
        List<CartItem> list = cartItemService.getCartItemByIdBill(id);
        CartItem cartItemFinal = list.get(list.size() - 1);
        int expectedId = 6;
        //kiểm tra nếu list khác 0 thì chạy đúng
        assertEquals(expectedId, cartItemFinal.getId());
    }

    //tổng tiền
    @Test
    void testSumOfList() {
        List<CartItem> list = new ArrayList<>();
        list.add(new CartItem(10000, 1));
        list.add(new CartItem(20000, 2));
        list.add(new CartItem(30000, 3));
        int expectedValue = 60000;
        assertEquals(expectedValue, cartItemService.getSumOfListCart(list));
    }

    @Test
    void testSumOfListNoItem() {
        List<CartItem> list = new ArrayList<>();
        int expectedValue = 0;
        assertEquals(expectedValue, cartItemService.getSumOfListCart(list));
    }

    @Test
    void testGetSumQuantityCart() {
        List<CartItem> list = new ArrayList<>();
        int expectedValue = 6;
        list.add(new CartItem(10000, 1));
        list.add(new CartItem(20000, 2));
        list.add(new CartItem(30000, 3));
        assertEquals(expectedValue, cartItemService.getSumQuantityCart(list));
    }

    @Test
    void testGetSumQuantityCartNoItem() {
        List<CartItem> list = new ArrayList<>();
        int expectedValue = 0;
        assertEquals(expectedValue, cartItemService.getSumQuantityCart(list));
    }

    @Test
    void testCheckExistCartItemSuccess() {
        List<CartItem> list = new ArrayList<>();
        //item 1
        CartItem item1 = new CartItem();
        Goods goods = new Goods();
        goods.setId(1);
        item1.setGoods(goods);

        //item 2
        CartItem item2 = new CartItem();
        Goods goods1 = new Goods();
        goods.setId(2);
        item2.setGoods(goods1);
        list.add(item1);
        list.add(item2);
        assertTrue(cartItemService.checkExistCartItem(list,goods));
    }

    @Test
    void testCheckExistCartItemFailure() {
        List<CartItem> list = new ArrayList<>();
        //item 1
        CartItem item1 = new CartItem();
        Goods goods = new Goods();
        goods.setId(1);
        item1.setGoods(goods);

        //item 2
        CartItem item2 = new CartItem();
        Goods goods1 = new Goods();
        goods1.setId(2);
        item2.setGoods(goods1);
        list.add(item1);
        list.add(item2);

        Goods goods3 = new Goods();
        goods3.setId(3);
        assertFalse(cartItemService.checkExistCartItem(list,goods3));
    }

    //bug
    @Test
    @Rollback
    @Transactional
    void testHandleDeleteCartItem() {
        List<CartItem> list = new ArrayList<>();
        CartItem cartItem1 = new CartItem();
        Goods goods1 = new Goods();
        goods1.setId(0);
        cartItem1.setId(0);
        cartItem1.setGoods(goods1);

        CartItem cartItem2 = new CartItem();
        Goods goods2 = new Goods();
        goods2.setId(1);
        cartItem2.setId(1);
        cartItem2.setGoods(goods2);


        list.add(cartItem1);
        list.add(cartItem2);
        System.out.println(list.size());
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.addAll(list);
        System.out.println(cartItemService.handleDeleteCartItem(goods2.getId(), cartItems).size());
        System.out.println(list.size());

    }






}