package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagInfo {
    @Id
    @GeneratedValue
    private Long id;

    private Integer startIndex;
    private Integer endIndex;
    @OneToOne
    @JoinColumn(name = "comment_id")
    private AbstractComment abstractComment;

}