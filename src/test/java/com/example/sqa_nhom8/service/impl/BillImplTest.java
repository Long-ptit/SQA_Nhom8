package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.Bill;
import com.example.sqa_nhom8.entitis.Customer;
import com.example.sqa_nhom8.repository.BillRepository;
import com.example.sqa_nhom8.repository.CustomerRepository;
import com.example.sqa_nhom8.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;



//12
@SpringBootTest
@AutoConfigureMockMvc
@Rollback
@Transactional
class BillImplTest {

    @Autowired
    BillImpl billImp;

    @Autowired
    BillRepository repository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    StaffRepository staffRepository;

    @Test
    @Rollback
    @Transactional
    void saveItem() {
        Bill bill = new Bill();
        bill.setDate("hahaha");
        repository.save(bill);
    }

    @Test
    void getAllBills() {
        List<Bill> list = billImp.getAllBills();
        int expectedSize = 2;
        assertEquals(expectedSize, list.size());
    }

    @Test
    void testFirstValueGetAllBills() {
        List<Bill> list = billImp.getAllBills();
        Bill bill = list.get(0);
        Bill expectedBill = repository.getById(1L);
        assertEquals(bill, expectedBill);
    }

    @Test
    void testSecondValueGetAllBills() {
        List<Bill> list = billImp.getAllBills();
        Bill bill = list.get(1);
        Bill expectedBill = repository.getById(3L);
        assertEquals(bill, expectedBill);
    }

    @Test
    void testFinalValueGetAllBills() {
        List<Bill> list = billImp.getAllBills();
        Bill bill = list.get(list.size() - 1);
        Bill expectedBill = repository.getById(3L);
        assertEquals(bill, expectedBill);
    }

    @Test
    void testGetBillByIdBillSuccess() {
        long idExpected = 1L;
        Bill bill = billImp.getBillByIdBill(idExpected);
        assertEquals(idExpected, bill.getId());
    }

    @Test
    void testGetBillByIdBillFailure() {
        assertNull(billImp.getBillByIdBill(-1L));
    }

    @Test
    void testGetBillsByIDCustomerSuccess() {
        long expectedSize = 2;
        List<Bill> list = billImp.getBillsByIDCustomer(1);
        assertEquals(expectedSize, list.size());
    }

    @Test
    void testGetBillsByIDCustomerSuccessNoValue() {
        long expectedSize = 0;
        List<Bill> list = billImp.getBillsByIDCustomer(-1);
        assertEquals(expectedSize, list.size());
    }

    @Test
    void getPriceAfterSale() {
        long priceExpected = 900;
        Bill bill = new Bill();
        bill.setTotalPrice(1000);
        bill.setDiscount(10);
        long priceActual = billImp.getPriceAfterSale(bill);
        assertEquals(priceExpected, priceActual);
    }

    @Test
    void getCoinAftefSaveBll() {
        long priceExpected = 1000;
        Bill bill = new Bill();
        bill.setActualPrice(300000);
        bill.setCoinsPay(2000);
        Customer customer = new Customer();
        customer.setTotalCoins(0);
        long coinActual = billImp.getCoinAftefSaveBll(bill, customer);
        assertEquals(priceExpected, coinActual);
    }

    @Test
    void getCoinWhenSave() {
        int value = 10000;
        Bill bill = new Bill();
        bill.setActualPrice(value);
        int expectedValue = 100;
        assertEquals(expectedValue, billImp.getCoinWhenSave(bill, new Customer()));
    }
}