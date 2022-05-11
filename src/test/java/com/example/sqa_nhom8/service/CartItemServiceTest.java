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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(true)
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
        int id = 1;
        CartItem cartItemFirst = cartItemService.getCartItemByIdBill(id).get(0);
        CartItem item = new CartItem();
        item.setId(1);
        item.setAmount(12);
        item.setDiscount(0);
        item.setName("Nước khoáng lavie");
        item.setPrice(4500);
        item.setTotalPrice(54000);
        item.setBill(billService.getBillByIdBill(1));
        item.setGoods(goodService.getGoodById(3));
        //kiểm tra nếu list khác 0 thì chạy đúng
        assertEquals(item, cartItemFirst);
    }

    @Test
    void testGetCartItemByIdBillFinal() {
        int id = 1;
        //trường hợp chỉ có 1 item, vẫn đúng
        List<CartItem> list = cartItemService.getCartItemByIdBill(id);
        CartItem cartItemFirst = list.get(list.size() - 1);
        CartItem item = new CartItem();
        item.setId(1);
        item.setAmount(12);
        item.setDiscount(0);
        item.setName("Nước khoáng lavie");
        item.setPrice(4500);
        item.setTotalPrice(54000);
        item.setBill(billService.getBillByIdBill(1));
        item.setGoods(goodService.getGoodById(3));
        //kiểm tra nếu list khác 0 thì chạy đúng
        assertEquals(item, cartItemFirst);
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

    @Test
    void testHandleDeleteCartItem() {
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
        System.out.println("Size trước khi gọi hàm: " + list.size());
       cartItemService.handleDeleteCartItem(1, list);
        System.out.println("Size sau khi gọi hàm: " +list.size());
        int expectedSize = list.size() - 1;
        assertEquals(list.size() - 1, list.size());
//
//        for (CartItem item: listAfterDelete) {
//            assertNotEquals(1, item.getId());
//        }

    }






}