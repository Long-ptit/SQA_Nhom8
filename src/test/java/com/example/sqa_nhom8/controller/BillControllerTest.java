package com.example.sqa_nhom8.controller;

import com.example.sqa_nhom8.entitis.Bill;
import com.example.sqa_nhom8.entitis.CartItem;
import com.example.sqa_nhom8.entitis.Customer;
import com.example.sqa_nhom8.entitis.Staff;
import com.example.sqa_nhom8.service.BillService;
import com.example.sqa_nhom8.service.CartItemService;
import com.example.sqa_nhom8.service.CustomerService;
import com.example.sqa_nhom8.service.StaffService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//10
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

    @Autowired
    CustomerService customerService;

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
    void testSeachInjectionUpperCaseContain() throws Exception {
//        Staff staff = new Staff();
//        staff.setName("Haha");
        Staff staff = staffService.getStaffById(2);
        List<Bill> listBills = billService.getAllBills();
        mockMvc.perform(get("/bill/search").param("key", "11SELECT11").sessionAttr("staff", staff))
                .andDo(print())
                .andExpect(model().attribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!"))
                .andExpect(view().name("home-bill"));
    }
    @Test
    void testSeachNotKey() throws Exception {
//        Staff staff = new Staff();
//        staff.setName("Haha");
        Staff staff = staffService.getStaffById(2);
        List<Bill> listBills = billService.getAllBills();
        mockMvc.perform(get("/bill/search").param("key", "").sessionAttr("staff", staff))
                .andDo(print())
                .andExpect(model().attribute("listBills", listBills))
                .andExpect(view().name("home-bill"));
    }

    @Test
    void testSeachInjection() throws Exception {
//        Staff staff = new Staff();
//        staff.setName("Haha");
        Staff staff = staffService.getStaffById(2);
        List<Bill> listBills = billService.getAllBills();
        mockMvc.perform(get("/bill/search").param("key", "select").sessionAttr("staff", staff))
                .andDo(print())
                .andExpect(model().attribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!"))
                .andExpect(view().name("home-bill"));
    }

    @Test
    void testSeachInjectionContain() throws Exception {
//        Staff staff = new Staff();
//        staff.setName("Haha");
        Staff staff = staffService.getStaffById(2);
        List<Bill> listBills = billService.getAllBills();
        mockMvc.perform(get("/bill/search").param("key", "1select111").sessionAttr("staff", staff))
                .andDo(print())
                .andExpect(model().attribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!"))
                .andExpect(view().name("home-bill"));
    }

    @Test
    void testSeachInjectionUpperCase() throws Exception {
//        Staff staff = new Staff();
//        staff.setName("Haha");
        Staff staff = staffService.getStaffById(2);
        List<Bill> listBills = billService.getAllBills();
        mockMvc.perform(get("/bill/search").param("key", "SELECT").sessionAttr("staff", staff))
                .andDo(print())
                .andExpect(model().attribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!"))
                .andExpect(view().name("home-bill"));
    }

    @Test
    void testSeachPhoneNotTrue() throws Exception {
//        Staff staff = new Staff();
//        staff.setName("Haha");
        Staff staff = staffService.getStaffById(2);
        List<Bill> listBills = billService.getAllBills();
        mockMvc.perform(get("/bill/search").param("key", "374732742379242347").sessionAttr("staff", staff))
                .andDo(print())
                .andExpect(model().attribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!"))
                .andExpect(view().name("home-bill"));
    }

    @Test
    void testSeachPhoneListBillIsNone() throws Exception {
//        Staff staff = new Staff();
//        staff.setName("Haha");
        Staff staff = staffService.getStaffById(2);
        List<Bill> listBills = billService.getAllBills();

        mockMvc.perform(get("/bill/search").param("key", "0987739878").sessionAttr("staff", staff))
                .andDo(print())
                .andExpect(model().attribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!"))
                .andExpect(view().name("home-bill"));
    }

    @Test
    void testSeachPhoneListBillSuccess() throws Exception {
//        Staff staff = new Staff();
//        staff.setName("Haha");
        String key = "03";
        Staff staff = staffService.getStaffById(2);
        List<Customer> customerList = customerService.getCustomerByPhone(key);
        List<Bill> listBills = new ArrayList<>();
        List<Integer> listID = new ArrayList<>();
        for(int i=0; i<customerList.size(); i++){
            listID.add(customerList.get(i).getId());
        }
        for (int i=0; i <listID.size(); i++){
            List<Bill> billsById = billService.getBillsByIDCustomer(listID.get(i));
            if(billsById != null){
                for (Bill b : billsById){
                    listBills.add(b);
                }
            }
        }
        mockMvc.perform(get("/bill/search").param("key", key).sessionAttr("staff", staff))
                .andDo(print())
                .andExpect(model().attribute("listBills", listBills))
                .andExpect(view().name("home-bill"));
    }


}