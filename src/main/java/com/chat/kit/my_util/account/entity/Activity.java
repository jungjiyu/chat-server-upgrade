package com.chat.kit.my_util.account.entity;

import com.chat.kit.my_util.account.enums.ActivityType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Memberr owner;

    @Column(nullable = false)
    private Integer point;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    private String message;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    private Memberr activist;

}
