package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.Customer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
class CustomerImplTest {

    @Autowired
    private CustomerImpl customerService;



    @Test

    void getCustomerById() {

        Customer c = new Customer( "Bùi Văn Test", "0337570000", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c);
        Assert.assertNotNull(c);

        //excep
        Customer  excp = customerService.getCustomerById(1000);
        assertNull(excp);

        //
        Customer c1 = customerService.getCustomerById(c.getId());
        assertEquals(c.getPhone(), c1.getPhone());

    }

    @Test
    void getCustomerByPhone() {

        Customer c = new Customer( "Bùi Văn Test", "0117570000", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c);

        Customer c1 = new Customer( "Bùi Văn Test1", "0117570001", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c1);
        Customer c2 = new Customer( "Bùi Văn Test2", "0117570002", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c2);
        Customer c3 = new Customer( "Bùi Văn Test3", "0387570003", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c3);

        List<Customer> listPhone = customerService.getCustomerByPhone("011");
        assertEquals(3, listPhone.size());

        listPhone = customerService.getCustomerByPhone("");
        assertEquals(4, listPhone.size());

        listPhone = customerService.getCustomerByPhone("    " );
        assertEquals(0, listPhone.size());

        listPhone = customerService.getCustomerByPhone(c1.getPhone());
        assertEquals(1, listPhone.size());

        listPhone = customerService.getCustomerByPhone("asdasd");
        assertEquals(0, listPhone.size());

        listPhone = customerService.getCustomerByPhone("select * from customere where phone = 0");
        assertEquals(0, listPhone.size());



    }

    @Test
    void getCustomers() {

        Customer c = new Customer( "Bùi Văn Test", "0117570000", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c);

        Customer c1 = new Customer( "Bùi Văn Test1", "0117570001", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c1);
        Customer c2 = new Customer( "Bùi Văn Test2", "0117570002", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c2);
        Customer c3 = new Customer( "Bùi Văn Test3", "0387570003", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c3);

        List<Customer> list = customerService.getCustomers();
        assertEquals(4, list.size());

    }

    @Test
    void saveCustoemr() {
        Customer c = new Customer( "Hà Nội", "0337595004", "Huyen@gmail.com", "Nguen Ngoc Huyen", 0,1);
        customerService.saveCustoemr(c);

        Assert.assertNotNull(c);

        Customer c1 = customerService.getCustomerById(c.getId());
        Assert.assertEquals(c.getPhone(), c1.getPhone());

    }

    @Test
    void getAllCustomer() {

        Customer c = new Customer( "Bùi Văn Test", "0117570000", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c);

        Customer c1 = new Customer( "Bùi Văn Test1", "0117570001", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c1);
        Customer c2 = new Customer( "Bùi Văn Test2", "0117570002", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c2);
        Customer c3 = new Customer( "Bùi Văn Test3", "0387570003", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c3);

        List<Customer> customerList = customerService.getAllCustomer();
        assertEquals(4, customerList.size());

    }

    @Test
    @Rollback
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
    @Rollback
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
    @Rollback
    void getOneCustomerByPhone() {

        Customer c = new Customer( "Test phan delete", "0737595000", "TEST@gmail.com", "test Delte TEst", 0,1);
        customerService.saveCustoemr(c);

        c = customerService.getOneCustomerByPhone("    ");
        assertNull(c);
        c = customerService.getOneCustomerByPhone("12321321312");
        assertNull(c);
        c = customerService.getOneCustomerByPhone("!!#@");
        assertNull(c);
//        c = customerService.getOneCustomerByPhone(c.getPhone());
//        assertNotNull(c);

    }
}