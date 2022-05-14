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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//9
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

        List<Customer> list  = customerService.getAllCustomer();


        //create
        Customer c = new Customer( "Bùi Văn Test", "0117570000", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c);
        Customer c1 = new Customer( "Bùi Văn Test1", "0117570001", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c1);
        Customer c2 = new Customer( "Bùi Văn Test2", "0117570002", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c2);
        Customer c3 = new Customer( "Bùi Văn Test3", "0397570003", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c3);

        List<Customer> listSauThem  = customerService.getAllCustomer();
        int sl =listSauThem.size();

        //test nhap gan dung
        List<Customer> listPhone = new ArrayList<>();
        listPhone = customerService.getCustomerByPhone("0117");
        assertEquals(3, listPhone.size());

        listPhone = customerService.getCustomerByPhone("");
        assertEquals(sl, listPhone.size() );

        listPhone = customerService.getCustomerByPhone("    " );
        assertEquals(sl, listPhone.size());

        listPhone = customerService.getCustomerByPhone(c1.getPhone());
        assertEquals(1, listPhone.size());

        listPhone = customerService.getCustomerByPhone("asdasd");
        assertEquals(0, listPhone.size());

        listPhone = customerService.getCustomerByPhone("select * from customere where phone = 0");
        assertEquals(0, listPhone.size());


    }

    //get tat ca cac danh sach customer
    @Test
    void getCustomers() {

        List<Customer> listC  = customerService.getCustomers();
        int slBanDau = listC.size();

        Customer c = new Customer( "Bùi Văn Test", "0117570000", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c);
        Customer c1 = new Customer( "Bùi Văn Test1", "0117570001", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c1);
        Customer c2 = new Customer( "Bùi Văn Test2", "0117570002", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c2);
        Customer c3 = new Customer( "Bùi Văn Test3", "0387570003", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c3);

        List<Customer> list = customerService.getCustomers();
        assertEquals(4 + slBanDau, list.size());

    }

    @Test
    void saveCustoemr() {
        Customer c = new Customer( "Hà Nội", "0337595004", "Huyen@gmail.com", "Nguen Ngoc Huyen", 0,1);
        customerService.saveCustoemr(c);

        Assert.assertNotNull(c);

        Customer c1 = customerService.getCustomerById(c.getId());
        Assert.assertEquals(c.getPhone(), c1.getPhone());

        //don't save sdt co san
        Customer c2 = new Customer( "Nguen Ngoc Huyen", "0337595004", "Huyen2@gmail.com", "Haf Noi", 0,1);
        Boolean check =  customerService.saveCustoemr(c2);
        assertFalse(check);

    }

    //get Tat ca khach hang dang su dung he thong
    @Test
    void getAllCustomer() {

        List<Customer> l = customerService.getAllCustomer();
        int sl = l.size();

        Customer c = new Customer( "Bùi Văn Test", "0117570000", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c);
        Customer c1 = new Customer( "Bùi Văn Test1", "0117570001", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c1);
        Customer c2 = new Customer( "Bùi Văn Test2", "0117570002", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c2);
        Customer c3 = new Customer( "Bùi Văn Test3", "0387570003", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c3);

        List<Customer> customerList = customerService.getAllCustomer();
        assertEquals(sl + 4 , customerList.size());

    }

    @Test
    void edtiCustomer() {
        Customer c = new Customer( "Nhữ Hoàng Anh", "0387159970", "TEST@gmail.com", "test TEst", 0,1);
        customerService.saveCustoemr(c);

        String ten = "Tester Bui";
        String sdt = "0387159972";
        String email = "TestXong@gmail.com";
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



    //Xoa mem
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

        Customer c = new Customer( "Nhữ Hoàng Anh", "0387159970", "TEST@gmail.com", "test TEst", 0,1);
        customerService.saveCustoemr(c);

        Customer c1 = customerService.getOneCustomerByPhone("    ");
        assertNull(c1);
        Customer c2 = customerService.getOneCustomerByPhone("12321321312");
        assertNull(c2);
        Customer c3 = customerService.getOneCustomerByPhone("!!#@");
        assertNull(c3);
        Customer c4 = customerService.getOneCustomerByPhone(c.getPhone());
        assertNotNull(c4);

    }

    @Test
    //search ng dang dung
    void searchListByPhone() {

        List<Customer> l = customerService.getAllCustomer();
        int sl = l.size();

        Customer c = new Customer( "Bùi Văn Test", "0117570000", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c);
        Customer c1 = new Customer( "Bùi Văn Test1", "0117570001", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c1);
        Customer c2 = new Customer( "Bùi Văn Test2", "0117570002", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c2);
        Customer c3 = new Customer( "Bùi Văn Test3", "0118570003", "Test@gmail.com", "Ha noi", 0,1);
        customerService.saveCustoemr(c3);

        List<Customer> list = customerService.getAllCustomer();
        int tong  = list.size();
        // search ""
        List<Customer> listSearch  = new ArrayList<>();

        List<Customer> tongList = customerService.getAllCustomer();
        int numSearch = tongList.size();

        listSearch  = customerService.searchListByPhone("");
        numSearch = listSearch.size();
        assertEquals(tong, numSearch);

        // search "      "
        listSearch = customerService.searchListByPhone("     ");
        numSearch = listSearch.size();
        assertEquals(tong, numSearch);

        // search "adsadasdsad"
        listSearch = customerService.searchListByPhone("asdasdas");
        assertNull(listSearch);

        // search "@#!#@#"
        listSearch = customerService.searchListByPhone("!@$@#@");
        assertNull(listSearch);

        listSearch  = customerService.searchListByPhone("0");
        numSearch = listSearch.size();
        assertEquals(tong, numSearch);

        listSearch  = customerService.searchListByPhone("0117");
        numSearch = listSearch.size();
        assertEquals(3, numSearch);

        listSearch  = customerService.searchListByPhone("0118570003");
        numSearch = listSearch.size();
        assertEquals(1, numSearch);

    }
}