package com.example.sqa_nhom8.repository;

import com.example.sqa_nhom8.entitis.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query(value = "select * from tbl_hoa_don where id_customer = ? and is_active = 1", nativeQuery = true)
    List<Bill> getBillByIDCustomer(long id);

    @Query(value = "select * from tbl_hoa_don where customer_phone like %:key% and is_active = 1", nativeQuery = true)
    List<Bill> searchBillByPhone(String key);

    @Query(value = "select * from tbl_hoa_don where is_active = 1", nativeQuery = true)
    List<Bill> getAllBillIsAlive();
}
