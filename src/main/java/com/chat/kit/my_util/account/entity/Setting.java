package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setting {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Memberr member;


    private Boolean pushAlarm;


    @PrePersist
    public void prePersist() {
        this.pushAlarm = false;
    }

    //추후 추가
}
