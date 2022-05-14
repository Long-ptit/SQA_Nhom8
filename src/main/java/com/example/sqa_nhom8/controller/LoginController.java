package com.example.sqa_nhom8.controller;

import com.example.sqa_nhom8.entitis.Staff;
import com.example.sqa_nhom8.repository.StaffRepository;
import com.example.sqa_nhom8.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {


    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/login")
    public String login(Model model, HttpSession session){
        Staff staff = new Staff();
        model.addAttribute("staff", staff);
        String s = (String) session.getAttribute("incorect");
        session.removeAttribute("incorect");
        model.addAttribute("incorect", s);
        System.out.println("done");
        return "login";
    }

    @GetMapping("/home")
    public String Home(HttpSession session,Model model){
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff.getRole().equals("nhanvien")) {
            if ((boolean) session.getAttribute(Constants.MSG_ADD_SUCCESS)) {
                model.addAttribute("messageKhachHangThem", "Thêm khách hàng thành công");
                session.setAttribute(Constants.MSG_ADD_SUCCESS, false);
            }
            return "home-staff";
        }
        return "home-admin";
    }



    @PostMapping("/home-staff")
    public String HomeStaff(@ModelAttribute("staff") Staff staff,  Model model,
                            HttpSession session){

        String us = staff.getUsername();
        String ps = staff.getPassword();

        Boolean check = staffService.checkAcount(us, ps);
        Staff s = staffService.getStaffByUserNameAndPassword(us, ps);
        System.out.println(check);
        if(check == true){
            session.setAttribute(Constants.MSG_ADD_SUCCESS, false);
            session.setAttribute(Constants.MSG_EDIT_SUCCESS, false);
            session.setAttribute(Constants.MSG_ADD_BILL, false);
            session.setAttribute(Constants.MSG_DELETE_CUSTOMER_SUCCESS, false);
            session.setAttribute("staff", s);
            if (s.getRole().equals("nhanvien")) {
                return "home-staff";
            } else {
                return "home-admin";
            }
        }else {
            session.setAttribute("incorect", "Tài khoản hoặc mật khẩu không khớp!");
            return "redirect:login";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model){

        Staff s = new Staff();
        model.addAttribute("staff", s);
        session.removeAttribute("staff");

        return "redirect:login";
    }

}
