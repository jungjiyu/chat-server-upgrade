package com.chat.kit.my_util.account.dto;

import lombok.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {

    private Long id;

    private String nickname;

    private Long parent_id;

    private Integer level;

    public List<MemberVO> mapToMemberVO(List<Object[]> results) {
        List<MemberVO> memberVOList = new ArrayList<>();
        for (Object[] result : results) {
            memberVOList.add(new MemberVO(
                    ((BigInteger) result[0]).longValue(),   // id
                    (String) result[1],                     // nickname
                    ((BigInteger) result[2]).longValue(),   // parent_id
                    (Integer) result[3]                     // level
            ));
        }
        return memberVOList;


    }
}