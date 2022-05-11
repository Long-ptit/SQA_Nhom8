package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.Bill;
import com.example.sqa_nhom8.entitis.Customer;
import com.example.sqa_nhom8.repository.BillRepository;
import com.example.sqa_nhom8.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillImpl implements BillService {

    @Autowired
    BillRepository repository;

    @Override
    public Bill saveItem(Bill item) {
        Customer customer = item.getCustomer();
        //kích hoạt
        item.setIsActive(1);
        item.setCustomerName(customer.getName());
        item.setCustomerPhone(customer.getPhone());
        item.setCustomerEmail(customer.getEmail());
        item.setCustomerAddress(customer.getAddress());
        return repository.save(item);
    }

    @Override
    public List<Bill> getAllBills() {
        return repository.getAllBillIsAlive();
    }

    @Override
    public Bill getBillByIdBill(long id) {
        Optional<Bill> optionalBill = repository.findById(id);
        if (optionalBill.isPresent()) {
            return optionalBill.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Bill> getBillsByIDCustomer(long id) {
        return repository.getBillByIDCustomer(id);
    }

//    @Override
//    public Bill deleteBill(long id) {
//        Bill bill = repository.getById(id);
//        bill.setIsActive(0);
//        return repository.save(bill);
//    }

    @Override
    public int getPriceAfterSale(Bill bill) {
        return bill.getTotalPrice() * (100 - bill.getDiscount()) / 100;
    }

    @Override
    public int getCoinAftefSaveBll(Bill bill, Customer customer) {
          return customer.getTotalCoins() - bill.getCoinsPay() + (int) (bill.getActualPrice()*0.01);
    }

    @Override
    public int getCoinWhenSave(Bill bill, Customer customer) {
        return (int) (bill.getActualPrice()*0.01);
    }
}
