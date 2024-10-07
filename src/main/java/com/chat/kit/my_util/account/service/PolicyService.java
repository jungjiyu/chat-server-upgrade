package com.chat.kit.my_util.account.service;

import com.chat.kit.my_util.account.entity.BusinessPolicy;
import com.chat.kit.my_util.account.enums.PolicyProperties;
import com.chat.kit.my_util.account.repository.BusinessPolicyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class PolicyService {
    private final BusinessPolicyRepository businessPolicyRepository;

    @CachePut(cacheNames = "policyCache", key = "#policy.name()")
    public BusinessPolicy updatePolicyValue(PolicyProperties policy, int value) {
        //추후 관리자가 정책을 변경할때 사용
        log.info("이 로그는 해당 key에 대한 캐시가 업데이트 되는경우 찍힙니다.");
        BusinessPolicy busPolicy = businessPolicyRepository.findByPolicyName(policy)
                .orElseThrow(() -> new RuntimeException(policy.name() + "에 해당하는 정책을 찾을 수 없습니다."));
        busPolicy.setValue(value);
        return businessPolicyRepository.save(busPolicy);
    }

    @Cacheable(cacheNames = "policyCache", key = "#policy.name()")
    @Transactional(readOnly = true)
    public BusinessPolicy getPolicy(PolicyProperties policy) {
        log.info("이 로그는 해당 key에 대한 캐시가 없는 경우 출력됩니다.");
        return businessPolicyRepository.findByPolicyName(policy)
                .orElseThrow(() -> new RuntimeException(policy.name() + "에 해당하는 정책을 찾을 수 없습니다."));
    }


}