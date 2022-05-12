package com.example.sqa_nhom8.controller;

import com.example.sqa_nhom8.entitis.Bill;
import com.example.sqa_nhom8.entitis.CartItem;
import com.example.sqa_nhom8.entitis.Customer;
import com.example.sqa_nhom8.service.CartItemService;
import com.example.sqa_nhom8.service.CustomerService;
import com.example.sqa_nhom8.service.impl.BillImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    BillImpl billService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/home")
    public String getHomeHoaDon(Model model) {
        List<Bill> bills = billService.getAllBills();
        model.addAttribute("listBills", bills);
        return "home-bill";
    }

    @GetMapping("/view")
    public String getDetailView(Model model, @RequestParam("id") long id) {

        Bill bill = billService.getBillByIdBill(id);
        List<CartItem> itemList = cartItemService.getCartItemByIdBill((int) id);
        System.out.println(itemList.size() + "hiuhiuu");
        model.addAttribute("bill", bill);
        model.addAttribute("listCart", itemList);
        return "detail-bill";
    }

    @GetMapping("/search")
    public String search(@RequestParam("key") String text, Model model) {

        List<Bill> listBills = new ArrayList<>();
        listBills = billService.searchBillByPhone(text);
        if (listBills == null) {
            model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
        } else {
            model.addAttribute("listBills", listBills);

        }
        return "home-bill";
    }

}
