package com.example.sqa_nhom8.repository;

import com.example.sqa_nhom8.entitis.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

    @Query(value = "select * from staff where username = ?1 and password = ?2 and is_active=1", nativeQuery = true)
    Staff findUser(String username, String password);

}
