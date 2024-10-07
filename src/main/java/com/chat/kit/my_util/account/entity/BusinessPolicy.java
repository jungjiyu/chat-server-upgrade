package com.chat.kit.my_util.account.entity;

import com.chat.kit.my_util.account.enums.PolicyProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "business_policy")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@Setter
public class BusinessPolicy extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, updatable = false)
    private PolicyProperties policyName;

    @Column(name = "policy_value", nullable = false)
    private Integer value;
}
