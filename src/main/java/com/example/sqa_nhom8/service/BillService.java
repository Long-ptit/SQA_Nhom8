package com.example.sqa_nhom8.service;

import com.example.sqa_nhom8.entitis.Bill;
import com.example.sqa_nhom8.entitis.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BillService {

    Bill saveItem(Bill item);
    List<Bill> getAllBills();
    Bill getBillByIdBill(long id);
    List<Bill> getBillsByIDCustomer(long id);
    int getPriceAfterSale(Bill bill);
    int getCoinAftefSaveBll(Bill bill, Customer customer);
    int getCoinWhenSave(Bill bill, Customer customer);
    List<Bill> findBill(String key);
}
