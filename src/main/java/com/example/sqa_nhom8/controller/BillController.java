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

        try {
            String s = text.trim();
            System.out.println("Text: " + s);
            List<Bill> listBills = new ArrayList<>();
            List<Customer> customerList = new ArrayList<>();

            if (s.equals("")) {
                System.out.println("Truong hop 1 s la rong");
                listBills = billService.getAllBills();
                model.addAttribute("listBills", listBills);
            } else if (s.contains("select") || s.contains("or 1=1")
                    || s.contains(" or") || s.contains("where")
                    || s.contains("1=1") || s.contains("or 1=1;–") || s.contains("‘ or ‘abc‘=‘abc‘;–")
                    || s.contains("‘ or ‘ ‘=‘ ‘;–") || s.contains("%")) {
                System.out.println("Truong hop injection");
                System.out.println("Text: " + s);
                model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
            }
            else {
                customerList = customerService.getCustomerByPhone(s);
                System.out.println("Dau Vao la so dien thoai");
                if (customerList.size() == 0) {
                    System.out.println("So dien thoai sai");
                    model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
                } else {
                    System.out.println("Co ban ghi");
                    List<Integer> listID = new ArrayList<>();
                    for(int i=0; i<customerList.size(); i++){
                        listID.add(customerList.get(i).getId());
                    }
                    for (int i=0; i <listID.size(); i++){
                        List<Bill> billsById = billService.getBillsByIDCustomer(listID.get(i));
                            for (Bill b : billsById){
                                listBills.add(b);
                            }
                    }
                    System.out.println("Size Bill : " + listBills.size());
                    if(listBills.size() > 0 ){
                        model.addAttribute("listBills", listBills);
                    }else {
                        model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
                    }

                }

            }
            return "home-bill";

        } catch (Exception e) {
            System.out.println("Loi Parser");
            model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
            return "home-bill";
        }
    }


//@GetMapping("/search")
//public String search(@RequestParam("key") String text, Model model) {
//
//    try {
//        String s = text.trim();
//        System.out.println("Text: " + s);
//        List<Bill> listBills = new ArrayList<>();
//        List<Customer> customerList = new ArrayList<>();
//
//        if (s.equals("")) {
//            System.out.println("Truong hop 1 s la rong");
//            listBills = billService.getAllBills();
//            model.addAttribute("listBills", listBills);
//        } else if (s.contains("select") || s.contains("or 1=1")
//                || s.contains(" or") || s.contains("where")
//                || s.contains("1=1") || s.contains("or 1=1;–") || s.contains("‘ or ‘abc‘=‘abc‘;–")
//                || s.contains("‘ or ‘ ‘=‘ ‘;–") || s.contains("%")) {
//            System.out.println("Truong hop injection");
//            System.out.println("Text: " + s);
//            model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
//        }
//        else {
//            customerList = customerService.getCustomerByPhone(s);
//            System.out.println("Dau Vao la so dien thoai");
//            if (customerList.size() == 0) {
//                System.out.println("So dien thoai sai");
//                model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
//            } else {
//                System.out.println("Co ban ghi");
//                List<Integer> listID = new ArrayList<>();
//                for(int i=0; i<customerList.size(); i++){
//                    listID.add(customerList.get(i).getId());
//                }
//                for (int i=0; i <listID.size(); i++){
//                    List<Bill> billsById = billService.getBillsByIDCustomer(listID.get(i));
//                    if(billsById != null){
//                        for (Bill b : billsById){
//                            listBills.add(b);
//                        }
//                    }
//                }
//                System.out.println("Size Bill : " + listBills.size());
//                if(listBills.size() > 0 ){
//                    model.addAttribute("listBills", listBills);
//                }else {
//                    model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
//                }
//
//            }
//
//        }
//        return "home-bill";
//
//    } catch (Exception e) {
//        System.out.println("Loi Parser");
//        model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
//        return "home-bill";
//    }
}

//    @GetMapping("/search")
//    public String search(@RequestParam("key") String text, Model model) {
//
//        List<Bill> listBill = billService.findBill(text);
//
//        if(listBill == null){
//            model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
//        }else {
//            model.addAttribute("listBills", listBill);
//
//        }
//        return "home-bill";
//    }


//}
