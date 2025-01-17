package com.springboot.order.service;

import com.springboot.coffee.service.CoffeeService;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.entity.Stamp;
import com.springboot.member.service.MemberService;
import com.springboot.order.entity.Order;
import com.springboot.order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final MemberService memberService;
    private final CoffeeService coffeeService;
    private final OrderRepository orderRepository;

    public OrderService(MemberService memberService,
                        CoffeeService coffeeService,
                        OrderRepository orderRepository) {
        this.memberService = memberService;
        this.coffeeService = coffeeService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        // 회원이 존재하는지 확인
        Member findMember = memberService.findVerifiedMember(order.getMember().getMemberId());
        int stampCount = 0;

        // TODO 커피가 존재하는지 조회하는 로직이 포함되어야 합니다.
        order.getOrderCoffees().stream()
                .forEach(orderCoffee -> coffeeService.findVerifiedCoffee(orderCoffee.getCoffee().getCoffeeId()));

        //왜 Stamp stamp = order.getMember().getStamp();는 stampcount가 추가가 안될까?
        //현재 요청받아올때 order가 가지고있는 멤버객체에는 오로지 memberId만 가지고있다.
        //그러므로 memberService에서 이 memberId를 가지고있는 멤버 객체를 가져와
        //그 멤버가 가지고있는 stamp 객체를 가져와야하는 것
        Stamp stamp = findMember.getStamp();


       stamp.setStampCount(stamp.getStampCount() + addStampCount(order));
        return orderRepository.save(order);
    }

    // 메서드 추가
    public int addStampCount(Order order){
        int stampCount = 0;
        stampCount = order.getOrderCoffees().stream()
                .mapToInt(orderCoffee -> orderCoffee.getQuantity())
                .sum();

        return stampCount;
    }

    public Order updateOrder(Order order) {
        Order findOrder = findVerifiedOrder(order.getOrderId());

        Optional.ofNullable(order.getOrderStatus())
                .ifPresent(orderStatus -> findOrder.setOrderStatus(orderStatus));
        findOrder.setModifiedAt(LocalDateTime.now());
        return orderRepository.save(findOrder);
    }

    public Order findOrder(long orderId) {
        return findVerifiedOrder(orderId);
    }

    public Page<Order> findOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size,
                Sort.by("orderId").descending()));
    }

    public void cancelOrder(long orderId) {
        Order findOrder = findVerifiedOrder(orderId);
        int step = findOrder.getOrderStatus().getStepNumber();

        // OrderStatus의 step이 2 이상일 경우(ORDER_CONFIRM)에는 주문 취소가 되지 않도록한다.
        if (step >= 2) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        }
        findOrder.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);
        findOrder.setModifiedAt(LocalDateTime.now());
        orderRepository.save(findOrder);
    }

    private Order findVerifiedOrder(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order findOrder =
                optionalOrder.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrder;
    }
}
