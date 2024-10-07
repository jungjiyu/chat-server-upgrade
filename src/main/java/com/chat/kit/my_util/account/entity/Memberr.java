package com.chat.kit.my_util.account.entity;

import com.chat.kit.my_util.account.enums.Gender;
import com.chat.kit.my_util.account.enums.MemberStatus;
import com.chat.kit.my_util.account.util.MemberPolicyProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Memberr")
@SuperBuilder
@SQLRestriction("status != 'UNREGISTERED'")
public class Memberr extends User {
    // ------ MEMBER INFO ------
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String name;

    private LocalDate birth;

    @Column(nullable = false)
    private Long level;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'REGISTERED'")
    private MemberStatus status;

    private LocalDateTime lastModifiedNickname;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime joinDate;

    private Long descendantsCount;

    // ------ Relation Mapping ------
    // ------ ONE TO ONE ------
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Setting setting;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Account account;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_card_id")
    private ProfileCard profileCard;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_letter_id")
    private CoverLetter coverLetter;


    // ----- MANY TO ONE -----

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Memberr parent;

    // ----- ONE TO MANY -----

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @BatchSize(size = 100)
    private List<Poi> poiList = new ArrayList<>();

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @BatchSize(size = 10)
    private List<Invitation> publishedInvitationCodes = new ArrayList<>();

    @OneToMany(mappedBy = "blocker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @BatchSize(size = 10)
    private List<AMTBlock> amtBlackList = new ArrayList<>();

    @OneToMany(mappedBy = "blocker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @BatchSize(size = 100)
    private List<Block> blackList = new ArrayList<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Builder.Default
    @BatchSize(size = 100)
    private List<Memberr> children = new ArrayList<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Builder.Default
    private List<AbstractComment> comments = new ArrayList<>();

    // ----- Method -----

    public static Memberr of(String name, String nickname, Gender gender, LocalDate birth, Account account, Memberr parent) {
        Memberr member = Memberr.builder()
                .name(name)
                .nickname(nickname)
                .gender(gender)
                .birth(birth)
                .status(MemberStatus.REGISTERED)
                .build();
        //명함카드는 생성 x
        if(account != null)
            member.addAccount(account);
        if (parent != null) {
            member.addParent(parent);
            member.setLevel(parent.getLevel() + 1);
        } else {
            member.setLevel(1L);
        }

        member.addCoverLetter(CoverLetter.builder().build());
        member.addSetting(Setting.builder().build());

        return member;
    }


    public void changeNickname(String nickname) {
        if (lastModifiedNickname != null && lastModifiedNickname.isBefore(LocalDateTime.now().minusDays(MemberPolicyProperties.NICKNAME_CHANGE_COOLDOWN_DAYS))) {
            throw new RuntimeException("닉네임은 30일에 한번만 변경 가능합니다.");
        }
        if (!Pattern.matches(MemberPolicyProperties.NICKNAME_REGEX, nickname)) {
            throw new RuntimeException("닉네임은 3~10자리의 한글, 영문, 숫자만 가능합니다.");
        }
        setNickname(nickname);
        this.lastModifiedNickname = LocalDateTime.now();
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
        activity.setOwner(this);
    }

    public void addSetting(Setting setting) {
        this.setting = setting;
        setting.setMember(this);
    }

    public Block addBlock(Memberr member) {
        Block block = new Block();
        block.setBlocker(this);
        block.setBlocked(member);
        this.blackList.add(block);
        return block;
    }

    public void addAmtBlock(AMTBlock amtBlock) {
        this.amtBlackList.add(amtBlock);
        amtBlock.setBlocker(this);
    }

    public void createAmt(Memberr parent) {
        this.addParent(parent);
    }

    public void delete() { // 회원탈퇴 정책 변경시 수정
        this.status = MemberStatus.UNREGISTERED;
    }

    public void addComment(AbstractComment comment) {
        this.comments.add(comment);
        comment.setAuthor(this);
    }

    public void addPublishedInvitationCode(Invitation invitation) {
        this.publishedInvitationCodes.add(invitation);
        invitation.setPublisher(this);
    }

    public void addAccount(Account account) {
        this.account = account;
        account.setMember(this);
    }

    public void addProfileCard(ProfileCard profileCard) {
        this.profileCard = profileCard;
        profileCard.setAuthor(this);
    }

    public void addCoverLetter(CoverLetter coverLetter) {
        this.coverLetter = coverLetter;
        coverLetter.setAuthor(this);
    }

    public void addParent(Memberr parent) {
        this.parent = parent;
        parent.children.add(this);
    }

    public void addPoi(Poi poi) {
        this.poiList.add(poi);
        poi.setAuthor(this);
    }

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = MemberStatus.REGISTERED;
        }
        if(this.descendantsCount == null) {
            this.descendantsCount = 0L;
        }
    }

}
