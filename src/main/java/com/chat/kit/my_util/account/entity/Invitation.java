package com.chat.kit.my_util.account.entity;

import com.chat.kit.my_util.account.enums.InvitationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Invitation {

    @Transient
    private static final int EXPIRATION_DAY = 3;

    @Id
    @GeneratedValue
    private Long id;

    private String code;


    private LocalDateTime expiredTime;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Memberr publisher;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne
    private AnonymousUser subscriber;

    @PrePersist
    protected void setExpiredTime(){
        expiredTime = LocalDateTime.now().plusDays(EXPIRATION_DAY);
        status = InvitationStatus.BEFORE_JOIN;
    }

    public void validateUsedInvitationCode(){
        if(subscriber != null){
            throw new RuntimeException("이미 사용된 초대 코드입니다.");
        }
    }

    public boolean isExpired() {
        return expiredTime.isBefore(LocalDateTime.now());
    }

    public void addSubscriber(AnonymousUser anonymousUser) {
        this.subscriber = anonymousUser;
        anonymousUser.addInvitation(this);
    }

    public void setJoined(){
        status = InvitationStatus.AFTER_JOIN;
        expiredTime = LocalDateTime.now();
    }
}

