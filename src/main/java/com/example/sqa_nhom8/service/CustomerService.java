package com.example.sqa_nhom8.service;

import com.example.sqa_nhom8.entitis.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    Customer getCustomerById(int id);

    List<Customer> getCustomerByPhone(String phone);

    List<Customer> getCustomers();

    Boolean saveCustoemr(Customer customerm);

    List<Customer> getAllCustomer();

    boolean edtiCustomer(int id, Customer customer);

    void deleteCustomerById(int id);

    Customer getOneCustomerByPhone(String phone);

}
