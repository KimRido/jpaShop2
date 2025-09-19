package com.rido.shop.repository;

import com.rido.shop.dto.OrderListDto;

import java.util.List;

public interface SalesRepositoryCustom {

    List<OrderListDto> findAllOrders();
}
