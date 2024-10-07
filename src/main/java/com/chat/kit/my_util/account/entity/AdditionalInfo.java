package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInfo {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "profile_card_id" )
    private ProfileCard profileCard;

    public void update(LocalDate startDate, LocalDate endDate, String content) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
    }
}
