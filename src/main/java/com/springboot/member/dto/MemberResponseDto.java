package com.springboot.member.dto;

import com.springboot.member.entity.Member;
import com.springboot.member.entity.Stamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Builder
@Getter
public class MemberResponseDto {
    private long memberId;
    private String email;
    private String name;
    private String phone;
    private Member.MemberStatus memberStatus;   // 추가된 부분
    private int stampCount;

    public MemberResponseDto(Member.MemberStatus memberStatus, String phone, String name, String email, long memberId) {
        this.memberStatus = memberStatus;
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.memberId = memberId;
    }

    public void setStamp(Stamp stamp){
        this.stampCount = stamp.getStampCount();
    }
    // 추가된 부분
    public String getMemberStatus() {
        return memberStatus.getStatus();
    }
}
