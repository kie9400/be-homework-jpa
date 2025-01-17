package com.springboot.order.dto;

import com.springboot.member.entity.Member;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class OrderCoffeeDto {
    @Positive
    private long coffeeId;

    @Positive
    private int quantity;

//    public Coffee getCoffee() {
//        Member member = new Coffee();
//        member.setMemberI    ㅜ ㅠ ㅍd(memberId);
//        return member;
//    }
}
