package com.example.sqa_nhom8.controller;

import com.example.sqa_nhom8.entitis.Bill;
import com.example.sqa_nhom8.entitis.Customer;
import com.example.sqa_nhom8.entitis.Staff;
import com.example.sqa_nhom8.service.BillService;
import com.example.sqa_nhom8.service.CartItemService;
import com.example.sqa_nhom8.service.CustomerService;
import com.example.sqa_nhom8.service.StaffService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//12
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BillService billService;

    @Autowired
    StaffService staffService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    CustomerService customerService;

    Map<String, Object> sessionAttrs;

    @BeforeEach
    public void setup() {
        sessionAttrs = new HashMap<>();
        Staff staff = staffService.getStaffById(2);
        sessionAttrs.put(Constants.MSG_EDIT_SUCCESS, false);
        sessionAttrs.put(Constants.MSG_DELETE_CUSTOMER_SUCCESS, false);
        sessionAttrs.put("staff", staff);
    }

    @Test
    void getCustomers() throws Exception {

        Staff staff = staffService.getStaffById(2);
        List<Customer> customerList = new ArrayList<>();
        customerList = customerService.getAllCustomer();
        mockMvc.perform(get("/customer/list-customer")
                        .sessionAttrs(sessionAttrs))
                .andDo(print())
                .andExpect(model().attribute("listCustomer", customerList))
                .andExpect(view().name("get-customers"));

    }

    @Test
    void testGetCustomerWhenDeleteCustomer() throws Exception {

        Staff staff = staffService.getStaffById(2);
        List<Customer> customerList = new ArrayList<>();
        customerList = customerService.getAllCustomer();
        mockMvc.perform(get("/customer/list-customer")
                        .sessionAttr("staff", staff)
                        .sessionAttr(Constants.MSG_EDIT_SUCCESS, false)
                        .sessionAttr(Constants.MSG_DELETE_CUSTOMER_SUCCESS, true))
                .andDo(print())
                .andExpect(model().attribute("listCustomer", customerList))
                .andExpect(request().sessionAttribute(Constants.MSG_DELETE_CUSTOMER_SUCCESS, false))
                .andExpect(model().attribute("messageKhachHangXoa", "Xóa khách hàng thành công"))
                .andExpect(view().name("get-customers"));
    }

    @Test
    void testGetCustomerWhenEditCustomer() throws Exception {

        Staff staff = staffService.getStaffById(2);
        List<Customer> customerList = new ArrayList<>();
        customerList = customerService.getAllCustomer();
        mockMvc.perform(get("/customer/list-customer")
                        .sessionAttr("staff", staff)
                        .sessionAttr(Constants.MSG_EDIT_SUCCESS, true)
                        .sessionAttr(Constants.MSG_DELETE_CUSTOMER_SUCCESS, true))
                .andDo(print())
                .andExpect(model().attribute("listCustomer", customerList))
                .andExpect(request().sessionAttribute(Constants.MSG_EDIT_SUCCESS, false))
                .andExpect(model().attribute("messageKhachHangSua", "Sửa khách hàng thành công"))
                .andExpect(view().name("get-customers"));
    }

    @Test
    void addCustomer() throws Exception {

        Staff staff = staffService.getStaffById(2);
        mockMvc.perform(get("/customer/add")
                        .sessionAttr("staff", staff))
                .andExpect(model().attribute("customer", new Customer()))
                .andExpect(view().name("add-customer"));
    }

    @Test
    void saveFailure() throws Exception {

        Customer customer = new Customer();
        Customer fake = customerService.getCustomerById(1);
        customer.setName(fake.getName());
        customer.setEmail(fake.getEmail());
        customer.setAddress(fake.getEmail());
        customer.setTotalCoins(fake.getTotalCoins());
        customer.setPhone(fake.getPhone());
        Staff staff = staffService.getStaffById(2);
        mockMvc.perform(post("/customer/save")
                        .sessionAttr("staff", staff)
                .flashAttr("customer", customer))
                .andDo(print())
                .andExpect(model().attribute("f", "Đã có khách hàng sử dụng số điện thoại trên!"))
                .andExpect(view().name("add-customer"));
    }

    @Test
    @Transactional
    @Rollback
    void saveSuccess() throws Exception {

        Customer customer = new Customer();
        Customer fake = customerService.getCustomerById(1);
        customer.setName("Long đẹp trai");
        customer.setEmail(fake.getEmail());
        customer.setAddress(fake.getEmail());
        customer.setTotalCoins(fake.getTotalCoins());
        customer.setPhone(fake.getPhone());
        customer.setPhone("0349020966");
        Staff staff = staffService.getStaffById(2);
        mockMvc.perform(post("/customer/save")
                        .sessionAttrs(sessionAttrs)
                        .flashAttr("customer", customer))
                .andDo(print())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @Rollback
    @Transactional
    void testSaveditCustomerFailure() throws Exception {

        Customer customer = new Customer();
        Customer fake = customerService.getCustomerById(1);
        customer.setName("I'm Android Developer");
        customer.setEmail(fake.getEmail());
        customer.setAddress(fake.getEmail());
        customer.setTotalCoins(fake.getTotalCoins());
        customer.setPhone(fake.getPhone());
        customer.setPhone("0349020963");
        Staff staff = staffService.getStaffById(2);
        mockMvc.perform(post("/customer/save-edit")
                        .sessionAttrs(sessionAttrs)
                        .flashAttr("customer", customer))
                .andDo(print())
                .andExpect(model().attribute("f", "Đã có khách hàng sử dụng số điện thoại trên!"))
                .andExpect(view().name("edit-customer"));
    }

    @Test
    @Rollback
    @Transactional
    void testSaveditCustomerFailureByPhone() throws Exception {

        Customer customer = new Customer();
        customer.setName("Long đẹp trai");
        Staff staff = staffService.getStaffById(2);
        mockMvc.perform(post("/customer/save-edit")
                        .sessionAttrs(sessionAttrs)
                        .flashAttr("customer", customer))
                .andDo(print())
                .andExpect(view().name("edit-customer"));
    }

    @Test
    void saveEdit() {
    }

    @Test
    void delete() {
    }

    @Test
    void search() {
    }
}