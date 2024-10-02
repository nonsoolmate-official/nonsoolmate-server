package com.nonsoolmate.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.order.entity.OrderDetail;

public interface OrderRepository extends JpaRepository<OrderDetail, Long> {}
