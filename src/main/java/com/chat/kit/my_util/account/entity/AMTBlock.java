package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "amt_block")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AMTBlock extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blocker_id")
    private Memberr blocker;

    @ManyToOne
    @JoinColumn(name = "blocked_id")
    private Memberr blocked;
}