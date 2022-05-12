package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.Staff;
import com.example.sqa_nhom8.repository.StaffRepository;
import com.example.sqa_nhom8.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Boolean checkAcount(String username, String password) {
        Staff staff = staffRepository.findUser(username, password);

        if(staff!=null){
            return true;
        }
        return false;
    }

    @Override
    public Staff getStaffByUserNameAndPassword(String username, String password) {
        Staff staff = staffRepository.findUser(username, password);
        return staff;
    }

    @Override
    public Staff getStaffById(int id) {
        Optional<Staff> o = staffRepository.findById(id);
        if(o.isPresent()){
            return  o.get();
        }
        return null;
    }
}
