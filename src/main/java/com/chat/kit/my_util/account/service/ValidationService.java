package com.chat.kit.my_util.account.service;

import com.chat.kit.my_util.account.entity.Account;
import com.chat.kit.my_util.account.entity.Invitation;
import com.chat.kit.my_util.account.entity.Memberr;
import com.chat.kit.my_util.account.enums.PolicyProperties;
import com.chat.kit.my_util.account.repository.AccountRepository;
import com.chat.kit.my_util.account.repository.InvitationRepository;
import com.chat.kit.my_util.account.repository.MemberRepositoryy;
import com.chat.kit.my_util.account.util.UserDetailsImpl;
import com.chat.kit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final MemberRepositoryy memberRepository;
    private final InvitationRepository invitationRepository;
    private final AccountRepository accountRepository;
    private final PolicyService policyService;

    private final AuthService authService;
    /**
     * @Throw BusinessLogicException
     */

    public Memberr checkExistMemberByNickname(String nickname) {
        Optional<Memberr> hasMember = memberRepository.findByNickname(nickname);
        return hasMember.orElseThrow(() -> new RuntimeException( "해당하는 사용자가 없습니다."));
    }

    public boolean isCurrentAuthenticatedUser(Memberr member) {
        UserDetailsImpl userDetailServiceImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userDetailServiceImpl.getId();
        Optional<Account> account = accountRepository.findById(userDetailServiceImpl.getId());
        return account.isPresent() && account.get().getMember().getId().equals(member.getId());
    }

    public Memberr validateAuthorshipClaim(String nickname) {
        Memberr member = checkExistMemberByNickname(nickname);
        if (!isCurrentAuthenticatedUser(member)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        return member;
    }


    public Memberr validateAuthorshipClaim(Memberr member) {
        if (!isCurrentAuthenticatedUser(member)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        return member;
    }


    public <T> T checkExistEntity(T entity) {
        if (entity == null) {
            throw new RuntimeException("해당하는 엔티티가 없습니다.");
        }
        return entity;
    }

    public <T> T checkExistEntity(T entity, String exceptionMessage) {
        if (entity == null) {
            throw new RuntimeException( exceptionMessage);
        }
        return entity;
    }


    public <T> void checkNotExistEntity(T entity) {
        if (entity != null) {
            throw new RuntimeException("해당하는 엔티티가 이미 존재합니다.");
        }
    }

    public <T> T checkNotExistEntity(Optional<T> entity) {
        throw new RuntimeException("해당하는 엔티티가 없습니다.");
    }

    public Invitation validateInvitationCode(String token) {
        Invitation invitation = invitationRepository.findByCode(token)
                .orElseThrow(() -> new RuntimeException( "유효하지 않은 초대 코드입니다."));
        return invitation;
    }

    public void validateInvitationCodeExpired(Invitation invitation) {
        if (invitation.isExpired()) {
            throw new RuntimeException( "초대 코드가 만료되었습니다.");
        }
    }

    public Memberr validateInfoAccessLimit() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication != null && authentication.isAuthenticated())) {
            throw new RuntimeException( "인증 실패");
        }

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        Memberr member = accountRepository.findById(principal.getId()).orElseThrow(() -> new RuntimeException("인증되지 않은 사용자입니다.")).getMember();

        if (member.getProfileCard() != null && member
                .getJoinDate()
                .isBefore(LocalDateTime.now().minusDays(policyService.getPolicy(PolicyProperties.INFO_ACCESS_LIMIT_DAYS).getValue()))) {

            throw new RuntimeException("명함카드를 생성하지않아 타인의 정보를 열람할 수 있는 기간이 만료되었습니다.");
        }
        return member;
    }


}