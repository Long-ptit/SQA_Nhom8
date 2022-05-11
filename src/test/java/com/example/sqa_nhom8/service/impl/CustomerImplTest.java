package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.Customer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerImplTest {

    @Autowired
    private CustomerImpl customerService;



    @Test
    void getCustomerById() {
        //excep
        Customer c = customerService.getCustomerById(1000);
        assertNull(c);

        //
        Customer c1 = customerService.getCustomerById(1);
        assertNotNull(c1);
        assertEquals("0337595002", c1.getPhone());

    }

    @Test
    void getCustomerByPhone() {

        List<Customer> listPhone = customerService.getCustomerByPhone("033");
        assertEquals(2, listPhone.size());

        listPhone = customerService.getCustomerByPhone("");
        assertEquals(3, listPhone.size());

        listPhone = customerService.getCustomerByPhone("   ");
        assertEquals(3, listPhone.size());

        listPhone = customerService.getCustomerByPhone("0337595002");
        assertEquals(1, listPhone.size());


    }

    @Test
    void getCustomers() {

        List<Customer> list = customerService.getCustomers();
        assertEquals(3, list.size());

    }

    @Test
    @Rollback(false)
    void saveCustoemr() {
        Customer c = new Customer( "Hà Nội", "0337595004", "Huyen@gmail.com", "Nguen Ngoc Huyen", 0,1);
        customerService.saveCustoemr(c);

        Assert.assertNotNull(c);

        Customer c1 = customerService.getCustomerById(c.getId());
        Assert.assertEquals(c.getPhone(), c1.getPhone());

    }

    @Test
    void getAllCustomer() {

        List<Customer> customerList = customerService.getAllCustomer();
        assertEquals(3, customerList.size());

    }

    @Test
    void edtiCustomer() {
        Customer c = new Customer( "Hà Nội", "0737595004", "TEST@gmail.com", "test Ngoc TEst", 0,1);
        customerService.saveCustoemr(c);

        String ten = "Tester Bui";
        String sdt = "0737595111";
        String email = "1TEST@gmail.com";
        String dc = "Ha Noi";

        c.setName(ten);
        c.setPhone(sdt);
        c.setEmail(email);
        c.setAddress(dc);

        customerService.edtiCustomer(c.getId(), c);

        Customer customer = customerService.getCustomerById(c.getId());

        Assert.assertEquals(ten, customer.getName());
        Assert.assertEquals(sdt, customer.getPhone());
        Assert.assertEquals(email, customer.getEmail());
        Assert.assertEquals(dc, customer.getAddress());


    }



    @Test
    void deleteCustomerById() {

        Customer c = new Customer( "Test phan delete", "0737595000", "TEST@gmail.com", "test Delte TEst", 0,1);
        customerService.saveCustoemr(c);
        assertNotNull(c);

        Customer c1 = customerService.getCustomerById(c.getId());
        assertEquals(c.getId(), c1.getId());

        customerService.deleteCustomerById(c1.getId());
        assertEquals(0, c1.getIsActive());

    }

    @Test
    void getOneCustomerByPhone() {

        Customer c = customerService.getOneCustomerByPhone("    ");
        assertNull(c);
        c = customerService.getOneCustomerByPhone("12321321312");
        assertNull(c);
        c = customerService.getOneCustomerByPhone("!!#@");
        assertNull(c);
        c = customerService.getOneCustomerByPhone("0337595002");
        assertNotNull(c);

    }
}