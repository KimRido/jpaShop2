package com.rido.shop.repository;

import com.rido.shop.domain.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long>, SalesRepositoryCustom {


}
