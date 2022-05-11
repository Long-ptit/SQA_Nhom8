package com.example.sqa_nhom8;

import com.example.sqa_nhom8.entitis.Bill;
import com.example.sqa_nhom8.repository.BillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
class SqaNhom8ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    BillRepository billRepository;

    @Test
    @Transactional
    @Rollback
    void testSaveItem() {
        Bill bill = new Bill();
        bill.setCoinsPay(0);
        bill.setDiscount(0);
        bill.setPricePay(1000);
        bill.setPriceBack(500);
        bill.setTotalAmount(1);
        bill.setDate("hay zau");
        billRepository.save(bill);
    }

}
