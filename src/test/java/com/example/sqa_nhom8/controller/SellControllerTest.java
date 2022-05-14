package com.example.sqa_nhom8.controller;

import com.example.sqa_nhom8.entitis.*;
import com.example.sqa_nhom8.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//2
@SpringBootTest
@AutoConfigureMockMvc
class SellControllerTest {

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

    @Autowired
    GoodService goodService;

    Map<String, Object> sessionAttrs;

    @BeforeEach
    public void setup() {
        List<Goods> listMatHang = goodService.getListGood();
        List<Customer> listCustomer = customerService.getAllCustomer();
        List<CartItem> listCartItem = new ArrayList<>();
        Bill bill = new Bill();
        bill.setCustomer(new Customer());

        sessionAttrs = new HashMap<>();
        sessionAttrs.put(Constants.LIST_MAT_HANG, listMatHang);
        sessionAttrs.put(Constants.BILL, bill);
        sessionAttrs.put(Constants.LIST_KHACH_HANG, listCustomer);
        sessionAttrs.put(Constants.LIST_CART, listCartItem);
        sessionAttrs.put("staff", staffService.getStaffById(1));
    }

    @Test
    void getHome() throws Exception {
        List<Goods> listMatHang = goodService.getListGood();
        List<Customer> listCustomer = customerService.getAllCustomer();
        List<CartItem> listCartItem = new ArrayList<>();
        Bill bill = new Bill();
        bill.setCustomer(new Customer());

        mockMvc.perform(get("/banhang/home")
                        .sessionAttr(Constants.MSG_ADD_BILL, false)
                        .sessionAttr("staff", staffService.getStaffById(1)))
                .andDo(print())
                .andExpect(request().sessionAttribute(Constants.LIST_MAT_HANG, listMatHang))
                .andExpect(request().sessionAttribute(Constants.BILL, bill))
                .andExpect(request().sessionAttribute(Constants.LIST_KHACH_HANG, listCustomer))
                .andExpect(request().sessionAttribute(Constants.LIST_CART, listCartItem))
                .andExpect(view().name("seeling"));
    }

    @Test
    void clickKhachHang() throws Exception {
        mockMvc.perform(get("/banhang/clickKhachHang").sessionAttrs(sessionAttrs).param("id", "1"))
                .andDo(print())
                .andExpect(view().name("seeling"));
    }

}