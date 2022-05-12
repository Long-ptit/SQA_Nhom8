package com.example.sqa_nhom8.controller;

import com.example.sqa_nhom8.entitis.Customer;
import com.example.sqa_nhom8.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    @GetMapping("/list-customer")
    public String getCustomers(Model model, HttpSession session) {
        if ((boolean) session.getAttribute(Constants.MSG_EDIT_SUCCESS)) {
            model.addAttribute("messageKhachHangSua", "Sửa khách hàng thành công");
            session.setAttribute(Constants.MSG_EDIT_SUCCESS, false);
        }

        if ((boolean) session.getAttribute(Constants.MSG_DELETE_CUSTOMER_SUCCESS)) {
            model.addAttribute("messageKhachHangXoa", "Xóa khách hàng thành công");
            session.setAttribute(Constants.MSG_DELETE_CUSTOMER_SUCCESS, false);
        }
        List<Customer> customerList = new ArrayList<>();
        customerList = customerService.getAllCustomer();
        // đây là id của khách lẻ, thì không cho hiển thị ra
        model.addAttribute("listCustomer", customerList);
        return "get-customers";
    }

    @GetMapping("/add")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "add-customer";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("customer") Customer customer,
                       Errors errors, Model model,HttpSession session) {


        if (errors.hasErrors()) {
            System.out.println(errors.getObjectName()
            );
            return "add-customer";
        } else {

            if(customerService.saveCustoemr(customer) == true){
                //cần 1 hàm check
                session.setAttribute(Constants.MSG_ADD_SUCCESS, true);
                return "redirect:/home";
            }else {
                model.addAttribute("f", "Đã có khách hàng sử dụng số điện thoại trên!");

                return "add-customer";
            }

        }
    }

    @RequestMapping("/edit")
    public String editCustomer(@RequestParam("id") int id, Model model) {
        System.out.println(id);
        Customer c = customerService.getCustomerById(id);
        model.addAttribute("customer", c);
        return "edit-customer";
    }



    @PostMapping("/save-edit")
    public String saveEdit(@Valid @ModelAttribute("customer") Customer customer, Errors errors, Model model,HttpSession session) {
        System.out.println( "SDT: " + customer.getPhone());

        if (errors.hasErrors()) {
            System.out.println("co loi");
            return "edit-customer";
        } else {

            if(customerService.edtiCustomer(customer.getId(), customer) == false){
                model.addAttribute("f", "Đã có khách hàng sử dụng số điện thoại trên!");
                return "edit-customer";
            }else {
                customerService.edtiCustomer(customer.getId(), customer);
                //cần 1 hàm check
                session.setAttribute(Constants.MSG_EDIT_SUCCESS, true);
                return "redirect:/customer/list-customer";
            }
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id, HttpSession session) {
        System.out.println("delete" + id);
        customerService.deleteCustomerById(id);
        //check id còn tồn tại hay không
        session.setAttribute(Constants.MSG_DELETE_CUSTOMER_SUCCESS, true);
        return "redirect:/customer/list-customer";
    }

    @GetMapping("/search")
    public String search(@RequestParam("key") String text, Model model) {

        List<Customer> customerList = new ArrayList<>();

        customerList = customerService.searchListByPhone(text);
        if(customerList == null){
            model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
        }else {
            model.addAttribute("listCustomer", customerList);
        }

        return "get-customers";
//
    }

}
