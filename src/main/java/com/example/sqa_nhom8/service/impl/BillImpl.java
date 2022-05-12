package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.Bill;
import com.example.sqa_nhom8.entitis.Customer;
import com.example.sqa_nhom8.repository.BillRepository;
import com.example.sqa_nhom8.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillImpl implements BillService {

    @Autowired
    BillRepository repository;

    @Autowired
    CustomerImpl customerService;
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

    @Override
    public List<Bill> findBill(String text) {
        try {
            String s = text.trim();
            System.out.println("Text: " + s);
            List<Bill> listBills = new ArrayList<>();
            List<Customer> customerList = new ArrayList<>();

            if (s.equals("")) {
                System.out.println("Truong hop 1 s la rong");
                listBills = getAllBills();
                return listBills;
            } else if (s.contains("select") || s.contains("or 1=1")
                    || s.contains(" or") || s.contains("where")
                    || s.contains("1=1") || s.contains("or 1=1;–") || s.contains("‘ or ‘abc‘=‘abc‘;–")
                    || s.contains("‘ or ‘ ‘=‘ ‘;–") || s.contains("%")) {
                System.out.println("Truong hop injection");
                System.out.println("Text: " + s);
                return null;
//                model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
            }
            else {
                customerList = customerService.getCustomerByPhone(s);
                System.out.println("Dau Vao la so dien thoai");
                if (customerList.size() == 0) {
                    System.out.println("So dien thoai sai");
                    return null;
//                    model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
                } else {
                    System.out.println("Co ban ghi");
                    List<Integer> listID = new ArrayList<>();
                    for(int i=0; i<customerList.size(); i++){
                        listID.add(customerList.get(i).getId());
                    }
                    for (int i=0; i <listID.size(); i++){
                        List<Bill> billsById = getBillsByIDCustomer(listID.get(i));
                        if(billsById != null){
                            for (Bill b : billsById){
                                listBills.add(b);
                            }
                        }
                    }
                    System.out.println("Size Bill : " + listBills.size());
                    if(listBills.size() > 0 ){
                        return listBills;
                    }else {
                        return null;
                    }

                }

            }
    }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
