package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.Customer;
import com.example.sqa_nhom8.repository.CustomerRepository;
import com.example.sqa_nhom8.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(int id) {
        Optional<Customer> optional = customerRepository.findById(id);
        if (optional.isPresent()) {
            Customer c = optional.get();
            System.out.println(c.getId());
            return c;
        } else {
            System.out.println("NUll roi");
            return null;

        }

    }

    @Override
    public List<Customer> getCustomerByPhone(String phone) {
        phone = phone.trim();
        List<Customer> list = customerRepository.findCustomerByPhone(phone);
        return list;
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> list = customerRepository.findAll();
        return list;
    }

    @Override
    public Boolean saveCustoemr(Customer customer) {

        List<Customer> customerList = getCustomerByPhone(customer.getPhone());

        if (customerList.size() == 0) {
            customer.setIsActive(1);
            customer.setTotalCoins(0);
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = customerRepository.getAllCustomer();
        return customerList;
    }

    @Override
    public boolean edtiCustomer(int id, Customer customer) {

        List<Customer> customerList = getCustomerByPhone(customer.getPhone());

        Optional<Customer> optional = customerRepository.findById(id);

        if (optional.isPresent()) {
            Customer cDB = optional.get();
            System.out.println(cDB.getPhone() + " " + cDB.getId());

            if (customer.getPhone().trim().equals(cDB.getPhone().trim())) {
                System.out.println("TH_1 - so dien thoai k bi sua");
                cDB.setName(customer.getName());
                cDB.setPhone(customer.getPhone());
                cDB.setAddress(customer.getAddress());
                cDB.setEmail(customer.getEmail());
                return true;
            } else if (customer.getPhone().trim() != cDB.getPhone().trim() && customerList.size() == 0) {
                System.out.println("TH_2");
                cDB.setName(customer.getName());
                cDB.setPhone(customer.getPhone());
                cDB.setAddress(customer.getAddress());
                cDB.setEmail(customer.getEmail());
                return true;
            } else {
                System.out.println("TH_3");
                return false;
            }
        } else {
            System.out.println("TH_4");
            return false;
        }


    }

    @Override
    public void deleteCustomerById(int id) {
        Optional<Customer> optional = customerRepository.findById(id);
        if (optional.isPresent()) {
            Customer c = optional.get();
            c.setIsActive(0);
            customerRepository.save(c);
        }
    }

    @Override
    public Customer getOneCustomerByPhone(String phone) {
        phone = phone.trim();
        Customer c = customerRepository.getCustomerByPhone(phone);
        if (c != null) {
            return c;
        } else
            return null;
    }

    @Override
    public List<Customer> searchListByPhone(String text) {
        try {
            String s = text.trim();
            System.out.println("Text: " + s);
            List<Customer> customerList = new ArrayList<>();

            if (s.equals("")) {
                System.out.println("Truong hop 1 s la rong");
                customerList = getAllCustomer();
                return customerList;
            } else if (s.contains("select") || s.contains("or 1=1")
                    || s.contains(" or") || s.contains("where")
                    || s.contains("1=1") || s.contains("or 1=1;–") || s.contains("‘ or ‘abc‘=‘abc‘;–")
                    || s.contains("‘ or ‘ ‘=‘ ‘;–") || s.contains("%")) {
                System.out.println("Truong hop injection");
                System.out.println("Text: " + s);
                return null;
            } else {
                customerList = getCustomerByPhone(s);
                System.out.println("Size list: " + customerList.size());
                if (customerList.size() == 0) {
                    return null;
//                    model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
                } else {
                    return customerList;
                    //model.addAttribute("listCustomer", customerList);
                }
            }

        } catch (Exception e) {
            System.out.println("Loi Parser");
//            model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
            return null;
        }
    }


}
