package com.example.sqa_nhom8.repository;

import com.example.sqa_nhom8.entitis.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodRepository extends JpaRepository<Goods, Integer> {
}
