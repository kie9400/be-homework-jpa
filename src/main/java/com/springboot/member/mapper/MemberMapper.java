package com.springboot.member.mapper;

import com.springboot.member.dto.MemberPatchDto;
import com.springboot.member.dto.MemberPostDto;
import com.springboot.member.dto.MemberResponseDto;
import com.springboot.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);
    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);
    //MemberResponseDto memberToMemberResponseDto(Member member);
    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members);

//    default MemberResponseDto memberToMemberResponseDto(Member member) {
//        MemberResponseDto memberResponseDto = new MemberResponseDto(
//                member.getMemberStatus(),
//                member.getEmail(),
//                member.getName(),
//                member.getPhone(),
//                member.getMemberId()
//        );
//        memberResponseDto.setStamp(member.getStamp());
//        return memberResponseDto;
//    }
        //      return MemberResponseDto.builder()
//              .memberId(member.getMemberId())
//              .email(member.getEmail())
//              .name(member.getName())
//              .phone(member.getPhone())
//              .memberStatus(member.getMemberStatus())
//              .stampCount(member.getStamp().getStampCount())
//              .build();
//    }
    @Mapping(source = "stamp.stampCount", target = "stampCount")
    MemberResponseDto memberToMemberResponseDto(Member member);
}
