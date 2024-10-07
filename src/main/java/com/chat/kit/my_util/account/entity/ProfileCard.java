package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCard {

    @Transient
    public static final int MAX_ENTRY_SIZE = 20;

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Memberr author;

    @OneToOne(mappedBy = "profileCard", fetch = FetchType.LAZY)
    private ProfileFileProperty profileImage;

    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private OriginProfileCard originProfileCard;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private PublicProfileCard publicProfileCard;

    @OneToMany(mappedBy = "profileCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProfileCardComment> profileCardComments = new ArrayList<>();

    @OneToMany(mappedBy = "profileCard", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @BatchSize(size = 10)
    private List<AdditionalInfo> additionalInfos = new ArrayList<>();


    public void addComment(ProfileCardComment profileCardComment){
        profileCardComments.add(profileCardComment);
        profileCardComment.setProfileCard(this);
    }

    public void addOriginProfileCard(OriginProfileCard originProfileCard) {
        this.originProfileCard = originProfileCard;
        originProfileCard.setProfileCard(this);
    }

    public void addPublicProfileCard(PublicProfileCard publicProfileCard) {
        this.publicProfileCard = publicProfileCard;
        publicProfileCard.setProfileCard(this);
    }

    public void addAdditionalInfo(AdditionalInfo additionalInfo) {
        additionalInfos.add(additionalInfo);
        additionalInfo.setProfileCard(this);
    }

    public void removeAdditionalInfo(AdditionalInfo additionalInfo) {
        additionalInfos.remove(additionalInfo);
        additionalInfo.setProfileCard(null);
    }

    public void addProfileCardComment(ProfileCardComment profileCardComment) {
        profileCardComments.add(profileCardComment);
        profileCardComment.setProfileCard(this);
    }
}
