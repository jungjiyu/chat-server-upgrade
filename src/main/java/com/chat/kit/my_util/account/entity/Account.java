package com.chat.kit.my_util.account.entity;

import com.chat.kit.my_util.account.enums.Region;
import com.chat.kit.my_util.account.enums.Role;
import com.chat.kit.my_util.account.util.MemberPolicyProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Account{
    @Id
    @GeneratedValue
    private Long id;

    private String phoneNumber;

    private String email;

    private String password;

    private Boolean term1;

    private Boolean term2;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.EAGER)
    private Memberr member;

    public void changePassword(String changePassword){
        this.password = changePassword;
    }

    public void changeEmail(String changeEmail){
        if(Pattern.matches(MemberPolicyProperties.EMAIL_REGEX, changeEmail)){
            throw new RuntimeException("이메일 형식이 올바르지 않습니다.");
        }
        this.email = changeEmail;
    }

}
