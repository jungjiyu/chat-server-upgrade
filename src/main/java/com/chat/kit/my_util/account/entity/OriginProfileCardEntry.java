package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OriginProfileCardEntry extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;


    @Column(columnDefinition = "TEXT")
    private String title;


    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer index;

    private Boolean isVisibleBriefCard;

    @ManyToOne
    @JoinColumn(name = "profile_card_id")
    private OriginProfileCard profileCard;


}