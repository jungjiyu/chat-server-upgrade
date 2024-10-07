package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class PublicProfileCardEntry extends BaseTime {

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
    @JoinColumn(name = "public_profile_card_id")
    private PublicProfileCard publicProfileCard;

}