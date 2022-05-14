package com.example.sqa_nhom8.controller;

import com.example.sqa_nhom8.entitis.Staff;
import com.example.sqa_nhom8.service.BillService;
import com.example.sqa_nhom8.service.CartItemService;
import com.example.sqa_nhom8.service.CustomerService;
import com.example.sqa_nhom8.service.StaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//8
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

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


    @Test
    void login() throws Exception {
        mockMvc.perform(get("/login")
                        .sessionAttr("incorect", ""))
                .andDo(print())
                .andExpect(model().attribute("staff", new Staff()))
                .andExpect(model().attribute("incorect", ""))
                .andExpect(view().name("login"));
    }

    @Test
    void getHomeNhanVien() throws Exception {
        Staff staff = staffService.getStaffById(1);
        mockMvc.perform(get("/home")
                        .sessionAttr("staff", staff)
                        .sessionAttr(Constants.MSG_ADD_SUCCESS, false))
                .andDo(print())
                .andExpect(view().name("home-staff"));
    }

    @Test
    void getHomeNhanVienWhenAddSuccess() throws Exception {
        Staff staff = staffService.getStaffById(1);
        mockMvc.perform(get("/home")
                        .sessionAttr("staff", staff)
                        .sessionAttr(Constants.MSG_ADD_SUCCESS, true))
                .andDo(print())
                .andExpect(model().attribute("messageKhachHangThem", "Thêm khách hàng thành công" ))
                .andExpect(view().name("home-staff"));
    }

    @Test
    void getHomeAdmin() throws Exception {
        Staff staff = staffService.getStaffById(2);
        mockMvc.perform(get("/home")
                        .sessionAttr("staff", staff))
                .andDo(print())
                .andExpect(view().name("home-admin"));
    }

    @Test
    void homeStaffNhanVienSuccess() throws Exception {
        Staff staff = staffService.getStaffById(1);
        mockMvc.perform(post("/home-staff").flashAttr("staff", staff))
                .andDo(print())
                .andExpect(request().sessionAttribute("staff", staff))
                .andExpect(view().name("home-staff"));
    }

    @Test
    void homeStaffAdminSuccess() throws Exception {
        Staff staff = staffService.getStaffById(2);
        mockMvc.perform(post("/home-staff").flashAttr("staff", staff))
                .andDo(print())
                .andExpect(request().sessionAttribute("staff", staff))
                .andExpect(view().name("home-admin"));
    }

    @Test
    void homeStaffFailure() throws Exception {
        Staff staff = staffService.getStaffById(2);
        staff.setPassword("hiih");
        mockMvc.perform(post("/home-staff").flashAttr("staff", staff))
                .andDo(print())
                .andExpect(request().sessionAttribute("incorect", "Tài khoản hoặc mật khẩu không khớp!"))
                .andExpect(view().name("redirect:login"));
    }



    @Test
    void logout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(request().sessionAttributeDoesNotExist("staff"))
                .andExpect(view().name("redirect:login"));
    }
}