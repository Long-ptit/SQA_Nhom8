package com.example.sqa_nhom8.repository;

import com.example.sqa_nhom8.entitis.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "select * from customer where is_active = 1 and phone like %:key%", nativeQuery = true)
    List<Customer> findCustomerByPhone(String key);

    @Query(value = "SELECT * FROM customer where is_active = 1", nativeQuery = true)
    List<Customer> getAllCustomer();

    @Query(value = "select * from customer where phone = ? ", nativeQuery = true)
    Customer getCustomerByPhone(String key);

}
