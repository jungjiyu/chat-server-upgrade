package com.chat.kit.my_util.account.repository;

import com.chat.kit.my_util.account.entity.BusinessPolicy;
import com.chat.kit.my_util.account.enums.PolicyProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BusinessPolicyRepository extends JpaRepository<BusinessPolicy, Long> {
    Optional<BusinessPolicy> findByPolicyName(@Param("policyName") PolicyProperties policyName);
}

