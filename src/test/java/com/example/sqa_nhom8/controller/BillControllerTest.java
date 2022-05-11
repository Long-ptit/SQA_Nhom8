package com.example.sqa_nhom8.controller;

import com.example.sqa_nhom8.entitis.Bill;
import com.example.sqa_nhom8.entitis.CartItem;
import com.example.sqa_nhom8.entitis.Staff;
import com.example.sqa_nhom8.service.BillService;
import com.example.sqa_nhom8.service.CartItemService;
import com.example.sqa_nhom8.service.StaffService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
class BillControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BillService billService;

    @Autowired
    StaffService staffService;

    @Autowired
    CartItemService cartItemService;

    @Test
    @DisplayName("Get all bill")
    void getHomeHoaDon() throws Exception {
        List<Bill> bills = billService.getAllBills();
        Staff staff = staffService.getStaffById(2);
        Staff staff1 = new Staff();
        staff.setName("haha");
        this.mockMvc.perform(get("/bill/home").sessionAttr("staff",staff1))
                .andDo(print())
                .andExpect(model().attribute("listBills", bills))
                .andExpect(view().name("home-bill"));
    }

    @Test
    @DisplayName("Get bill by id")
    void getDetailView() throws Exception {
        Bill bill = billService.getBillByIdBill(1L);
        List<CartItem> itemList = cartItemService.getCartItemByIdBill(1);
        Staff staff = staffService.getStaffById(2);

        mockMvc.perform(get("/bill/view").param("id", "1").sessionAttr("staff", staff))
                .andDo(print())
                .andExpect(model().attribute("bill", bill))
                .andExpect(model().attribute("listCart", itemList))
                .andExpect(view().name("detail-bill"));



    }

    @Test
    void search() {
    }
}