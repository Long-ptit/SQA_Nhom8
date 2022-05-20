package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.Bill;
import com.example.sqa_nhom8.entitis.CartItem;
import com.example.sqa_nhom8.entitis.Customer;
import com.example.sqa_nhom8.entitis.Staff;
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

    public static final int TOTAL_SIZE_BILL = 5;
    public static final long ID_FINAL = 9;
    public static final long ID_FIRST = 1;


    @Test
    @Rollback
    @Transactional
    void saveItem() {
        Bill bill = new Bill();
        Staff staff = staffRepository.findById(1).get();
        Customer customer = customerRepository.findById(1).get();
        bill.setCustomer(customer);
        bill.setStaff(staff);
        bill.setTotalAmount(1);
        bill.setDiscount(0);
        bill.setActualPrice(0);
        bill.setPricePay(0);
        bill.setPriceBack(0);
        bill.setIsActive(1);
        bill.setDate("10/08/2000");
        Bill billResult = repository.save(bill);
        bill.setId(billResult.getId());
        assertEquals(billResult, bill);
    }

    @Test
    void getAllBills() {
        List<Bill> list = billImp.getAllBills();
        assertEquals(TOTAL_SIZE_BILL, list.size());
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
        Bill expectedBill = repository.getById(ID_FINAL);
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
        long expectedSize = 6;
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
        bill.setTotalPrice(value);
        bill.setDiscount(0);
        bill.setDiscount(0);
        int expectedValue = 100;
        assertEquals(expectedValue, billImp.getCoinWhenSave(bill, new Customer()));
    }

    @Test
    void testSearchBillRong() {
       String key = "";
        assertEquals(TOTAL_SIZE_BILL, billImp.searchBillByPhone(key).size());
    }

    @Test
    void testSearchBillInject() {
        String key = "select";
        assertNull(billImp.searchBillByPhone(key));
    }

    @Test
    void testSearchBillNormal() {
        String key = "0213abc";
        assertNull(billImp.searchBillByPhone(key));
    }

    @Test
    void testSearchBillNormalHasResult() {
        String key = "037";
        int expectedSize = 5;
        assertEquals(expectedSize, billImp.searchBillByPhone(key).size());
    }

}