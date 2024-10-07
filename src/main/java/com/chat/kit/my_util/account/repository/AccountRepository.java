package com.chat.kit.my_util.account.repository;

import com.chat.kit.my_util.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByPhoneNumberOrEmail(String phoneNumber, String email);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByPhoneNumber(String phoneNumber);


    @Query("""
        select account
        from Account account
        join account.member m
        where m.nickname = :nickname
    """)
    Optional<Account> findByMemberNickname(@Param("nickname") String nickname);

    @Query("""
        select case when count(account) > 0 then true else false end
        from Account account
        where account.email = :email or account.phoneNumber = :phone
    """)
    Boolean existsByEmailOrPhoneNumber(String email, String phone);

    @Query("""
        select distinct account
        from Account account
        where account.email = :email or account.phoneNumber = :phone
    """)
    List<Account> findByEmailOrPhoneNumber(String email, String phone);
}
