package com.example.sqa_nhom8.controller;

import com.example.sqa_nhom8.entitis.*;
import com.example.sqa_nhom8.service.BillService;
import com.example.sqa_nhom8.service.CartItemService;
import com.example.sqa_nhom8.service.CustomerService;
import com.example.sqa_nhom8.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/banhang")
public class SellController {

    @Autowired
    CartItemService cartItemService;

    @Autowired
    CustomerService customerService;

    @Autowired
    GoodService goodService;

    @Autowired
    BillService billService;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @GetMapping("/home")
    public String getHome(HttpSession session, Model model) {
        List<Goods> listMatHang = goodService.getListGood();
        List<Customer> listCustomer = customerService.getAllCustomer();
        List<CartItem> listCartItem = new ArrayList<>();
        Bill bill = new Bill();
        //bill.setCustomer(customerService.getCustomerById(1));
        //lưu list vào session
        bill.setCustomer(new Customer());
        if ((Boolean) session.getAttribute(Constants.MSG_ADD_BILL)) {
            model.addAttribute("message", "Thêm đơn hàng thành công");
            session.setAttribute(Constants.MSG_ADD_BILL, false);
        }


        session.setAttribute(Constants.LIST_MAT_HANG, listMatHang);
        session.setAttribute(Constants.BILL, bill);
        session.setAttribute(Constants.LIST_KHACH_HANG, listCustomer);
        session.setAttribute(Constants.LIST_CART, listCartItem);
        return "seeling";
    }

    @GetMapping("/clickKhachHang")
    public String clickKhachHang(HttpSession session, @RequestParam("id") int id) {
        Bill bill = (Bill) session.getAttribute(Constants.BILL);
        bill.setCustomer(customerService.getCustomerById(id));
        session.setAttribute(Constants.BILL, bill);
        return "seeling";
    }

    @PostMapping("/addsanpham")
    public String addSanPham(Model model,
                             HttpSession session,
                             @Valid @RequestParam(value = "key", required = false) String key,
                             @RequestParam("soLuong") int intSoLuong) {

        if (key == null) {
            model.addAttribute("error_chon", "Xin vui long chon 1 san pham");
            return "seeling";
        }
        List<CartItem> cartList = (List<CartItem>) session.getAttribute(Constants.LIST_CART);
        Goods matHang = goodService.getGoodById(Integer.parseInt(key));
        boolean check = cartItemService.checkExistCartItem(cartList, matHang);
        //setData for Item
        if (check) {
            System.out.println("Mặt hàng đã tồn tại");
            for (CartItem item : cartList) {
                if (item.getGoods().getId() == matHang.getId()) {
                    item.setAmount(item.getAmount() + intSoLuong);
                    item.setTotalPrice(cartItemService.getPriceFromCartItem(item.getAmount(), matHang.getPrice()));
                    break;
                }
            }
        } else {
            CartItem itemCart = new CartItem();
            itemCart.setName(matHang.getName());
            itemCart.setPrice(matHang.getPrice());
            itemCart.setTotalPrice(matHang.getPrice() * intSoLuong);
            itemCart.setTotalPrice(cartItemService.getPriceFromCartItem(intSoLuong, matHang.getPrice()));
            itemCart.setAmount(intSoLuong);
            itemCart.setGoods(matHang);
            cartList.add(itemCart);
        }
        int soLuongTong = 0;
        int tongTien = 0;
        soLuongTong = cartItemService.getSumQuantityCart(cartList);
        tongTien = cartItemService.getSumOfListCart(cartList);
        Bill bill = (Bill) session.getAttribute(Constants.BILL);
        bill.setTotalPrice(tongTien);
        bill.setTotalAmount(soLuongTong);
        session.setAttribute(Constants.BILL, bill);
        session.setAttribute(Constants.LIST_CART, cartList);
        return "seeling";
    }

    @GetMapping(path = "/xoaSanPham")
    public String deleteSanPham(@RequestParam("id") long id, HttpSession session) {
        List<CartItem> cartList = (List<CartItem>) session.getAttribute(Constants.LIST_CART);
        cartList = cartItemService.handleDeleteCartItem(id, cartList);
        int soLuongTong;
        int tongTien;
        soLuongTong = cartItemService.getSumQuantityCart(cartList);
        tongTien = cartItemService.getSumOfListCart(cartList);
        Bill bill = (Bill) session.getAttribute(Constants.BILL);
        bill.setTotalPrice(tongTien);
        bill.setTotalAmount(soLuongTong);
        session.setAttribute(Constants.BILL, bill);
        session.setAttribute(Constants.LIST_CART, cartList);
        return "seeling";
    }


    @PostMapping(path = "/suaSanPham")
    public String editSanPham(@RequestParam("id") long id, @RequestParam("soLuongSua") int soLuongSua, HttpSession session) {
        List<CartItem> cartList = (List<CartItem>) session.getAttribute(Constants.LIST_CART);
        cartList = cartItemService.handleEditCartItem(id, soLuongSua, cartList);
        int soLuongTong;
        int tongTien;
        soLuongTong = cartItemService.getSumQuantityCart(cartList);
        tongTien = cartItemService.getSumOfListCart(cartList);
        Bill bill = (Bill) session.getAttribute(Constants.BILL);
        bill.setTotalPrice(tongTien);
        bill.setTotalAmount(soLuongTong);
        session.setAttribute(Constants.BILL, bill);
        session.setAttribute(Constants.LIST_CART, cartList);
        return "seeling";
    }


    @PostMapping(path = "/confirm")
    public String gotoConfirm(HttpSession session, @RequestParam("giamGia") int giamGia,
                              @Valid @RequestParam(value = "xu", required = false) String xu,
                              @RequestParam("id") int id, Model model) {

        List<CartItem> cartList = (List<CartItem>) session.getAttribute(Constants.LIST_CART);
        Bill bill = (Bill) session.getAttribute(Constants.BILL);
        if (bill.getCustomer().getName() == null) {
            model.addAttribute("error_khach_hang", "Vui lòng thêm một khách hàng");
            return "seeling";
        }
        if (cartList.size() == 0) {
            model.addAttribute("error_khach_hang", "Vui lòng thêm một mặt hàng");
            return "seeling";
        }
        bill.setDiscount(giamGia);
        int tongTien = bill.getTotalPrice();
        int tienSauGiamGia = 0;
        int tienSauKM = billService.getPriceAfterSale(bill);
        if (xu != null) {
            tienSauGiamGia = tienSauKM - customerService.getCustomerById(id).getTotalCoins();
            if (tienSauGiamGia < 0) {
                tienSauGiamGia = 0;
                bill.setCoinsPay(tienSauKM);
            } else {
                bill.setCoinsPay(customerService.getCustomerById(id).getTotalCoins());
            }
        } else {
            tienSauGiamGia = tienSauKM;
            bill.setCoinsPay(0);
        }
        bill.setActualPrice(tienSauGiamGia);
        bill.setCustomer(customerService.getCustomerById(id));
        bill.setDiscount(giamGia);
        Customer customer = customerService.getCustomerById(bill.getCustomer().getId());
        bill.setDiscount(giamGia);
        session.setAttribute(Constants.BILL, bill);
        session.setAttribute("xuNhan", billService.getCoinWhenSave(bill, customer));
        return "confirm-selling";
    }

    @PostMapping(path = "/saveHoaDon")
    public String saveHoaDon(
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session,
            @RequestParam("tienThua") int tienThua,
            @RequestParam("tienKhachTra") int tienKhachTraInt
    ) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<CartItem> cartList = (List<CartItem>) session.getAttribute(Constants.LIST_CART);
        Staff nhanVien = (Staff) session.getAttribute("nhanvien");
        Bill billBanHang = (Bill) session.getAttribute(Constants.BILL);
        billBanHang.setDate(dateFormat.format(new Date()));
        billBanHang.setStaff(nhanVien);
        billBanHang.setPriceBack(tienThua);
        billBanHang.setPricePay(tienKhachTraInt);
        Customer customer = customerService.getCustomerById(billBanHang.getCustomer().getId());
        customer.setTotalCoins(billService.getCoinAftefSaveBll(billBanHang, customer));
        //
        customerService.edtiCustomer(customer.getId(), customer);
        Staff staff = (Staff) session.getAttribute("staff");
        billBanHang.setStaff(staff);
        Bill billBH = billService.saveItem(billBanHang);
        if (billBH == billService.getBillByIdBill(billBH.getId())) {
            for (CartItem item : cartList) {
                item.setBill(billBH);
                cartItemService.saveItem(item);
            }
            session.setAttribute(Constants.MSG_ADD_BILL, true);
            return "redirect:/banhang/home";
        } else {
            return "redirect:/banhang/home";
        }
    }

}
