package com.nonsoolmate.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.order.entity.OrderDetail;

public interface OrderRepository extends JpaRepository<OrderDetail, Long> {

  @Query(
      "SELECT o FROM OrderDetail o "
          + "JOIN FETCH o.member "
          + "JOIN FETCH o.product "
          + "JOIN FETCH o.couponMember "
          + "WHERE o.isPayment = false")
  List<OrderDetail> findAllByIsPaymentFalse();

  Optional<OrderDetail> findByMemberAndIsPaymentFalse(Member member);
}
