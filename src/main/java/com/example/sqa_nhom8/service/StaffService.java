package com.example.sqa_nhom8.service;

import com.example.sqa_nhom8.entitis.Staff;
import org.springframework.stereotype.Service;

@Service
public interface StaffService {

    Boolean checkAcount(String username, String password);

    Staff getStaffByUserNameAndPassword(String username, String password);

    Staff getStaffById(int id);

}
