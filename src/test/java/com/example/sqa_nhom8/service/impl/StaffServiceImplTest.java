package com.example.sqa_nhom8.service.impl;

import com.example.sqa_nhom8.entitis.Staff;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class StaffServiceImplTest {

    @Autowired
    private StaffServiceImpl staffService;

    @Test
    void checkAcount() {

        boolean  c = staffService.checkAcount("nhanvien", "nhanvien");
        assertTrue(c);
        c = staffService.checkAcount("abcxyz", "abcxyz");
        assertFalse(c);

    }

    @Test
    void getStaffByUserNameAndPassword() {

        Staff s = staffService.getStaffByUserNameAndPassword("admin", "admin");
        assertNotNull(s);
        s=staffService.getStaffByUserNameAndPassword("admin13213", "admin123123");
        assertNull(s);
    }

    @Test
    void getStaffById() {

        Staff s = staffService.getStaffById(1);
        assertNotNull(s);
        assertEquals("0337595001",s.getPhone()  );

        s = staffService.getStaffById(1000);
        assertNull(s);

    }
}