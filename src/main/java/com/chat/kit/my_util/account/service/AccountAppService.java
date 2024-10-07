package com.chat.kit.my_util.account.service;

import com.chat.kit.my_util.account.dto.MemberSynRequest;
import com.chat.kit.my_util.account.dto.RegisterRequest;
import com.chat.kit.my_util.account.dto.RegisterResponse;
import com.chat.kit.my_util.account.entity.Account;
import com.chat.kit.my_util.account.entity.AnonymousUser;
import com.chat.kit.my_util.account.entity.Invitation;
import com.chat.kit.my_util.account.entity.Memberr;
import com.chat.kit.my_util.account.enums.Region;
import com.chat.kit.my_util.account.repository.AccountRepository;
import com.chat.kit.my_util.account.repository.MemberRepositoryy;
import com.chat.kit.my_util.account.util.EmailVerificationContext;
import com.chat.kit.my_util.account.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class AccountAppService {

    private final AccountRepository accountRepository;
    private final MemberRepositoryy memberRepository;
    private final EmailVerificationContext emailVerificationContext;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final JwtProvider jwtProvider;


    //나중에 분리시키자
    public RegisterResponse.Success joinNormalType(RegisterRequest.Create joinDto, String invitationCode) {
//        validateRegisterRequest(joinDto);

        Account newAccount = joinDto.toAccount(passwordEncoder);

//        Invitation invitation = validationService.validateInvitationCode(invitationCode);
//        Memberr publisher = invitation.getPublisher();
        Memberr publisher = null; // invitation.getPublisher() 제거

        Memberr newMember = Memberr.of(
                joinDto.getName(),
                joinDto.getNickname(),
                joinDto.getGender(),
                joinDto.getBirth(),
                newAccount,
                publisher);

//        Optional.ofNullable(invitation.getSubscriber())
//                .map(AnonymousUser::getComment)
//                .ifPresent(newMember::addComment);

        Memberr member = memberRepository.saveAndFlush(newMember);
//        memberRepository.updateAncestor(member.getId());
//        invitation.setJoined();


        String systemToken = jwtProvider.createSystemToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + systemToken);

        HttpEntity<MemberSynRequest> request = new HttpEntity<>(MemberSynRequest.builder().id(member.getId()).build(), headers);
        RestTemplate restTemplate = new RestTemplate();

        // 디버깅을 위한 로그 출력
        log.info("Request Headers: {}", headers);
        log.info("Request Body: {}", request.getBody());

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity("http://chat.storyb.kr/members", request, Void.class);
            log.info("Response Status: {}", response.getStatusCode());
        } catch (HttpServerErrorException e) {
            log.error("HTTP 500 Error Response: {}", e.getResponseBodyAsString());
            throw e; // 예외를 다시 던져서 처리
        }

        return RegisterResponse.Success.builder()
                .phone(newAccount.getPhoneNumber())
                .email(newAccount.getEmail())
                .nickname(newMember.getNickname())
                .build();    }

    private void validateRegisterRequest(RegisterRequest.Create joinDto) { //TODO 도메인 서비스로 이동
//        if (!joinDto.isValidPassword()) {
//            throw new RuntimeException( "비밀번호는 8~15자리의 영문, 숫자, 특수문자 조합이어야 합니다.");
//        }
//
//        String email = joinDto.getEmail();
//        String phone = joinDto.getPhoneNumber();
//        String identityVerificationToken = joinDto.getIdentityVerificationToken();
//
//        Region region = joinDto.getRegion();
//
//        EmailVerificationContext.VerificationData verificationData = emailVerificationContext.get(identityVerificationToken);
//
//        if (verificationData == null) {
//            throw new RuntimeException( "인증번호가 잘못 되었습니다.");
//        }
//
//        String emailOrPhone = verificationData.getValue();
//
//        if (emailOrPhone == null || !emailOrPhone.equals(email) && !emailOrPhone.equals(phone)) {
//            throw new RuntimeException( "유효하지않은 요청입니다.");
//        }
//
//        if (region == Region.KR && phone == null) {
//            throw new RuntimeException("한국인 사용자는 전화번호를 필수로 입력해야합니다.");
//        } else if (region == Region.OTHER && email == null) {
//            throw new RuntimeException( "외국인 사용자는 이메일을 필수로 입력해야합니다.");
//        }
//
//        if (memberRepository.existsByNickname(joinDto.getNickname())) {
//            throw new RuntimeException("이미 사용중인 닉네임입니다.");
//        }
//
//        if (accountRepository.existsByEmailOrPhoneNumber(email, phone)) {
//            throw new RuntimeException("이미 가입된 이메일 또는 전화번호입니다.");
//        }

    }




}
