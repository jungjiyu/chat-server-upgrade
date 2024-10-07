package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OriginProfileCard {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private ProfileCard profileCard;

    @OneToMany(mappedBy = "profileCard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    @Builder.Default
    private List<OriginProfileCardEntry> profileCardEntries = new ArrayList<>();

    public void addEntry(OriginProfileCardEntry entry) {
        if(ProfileCard.MAX_ENTRY_SIZE < profileCardEntries.size() + 1) {
            throw new RuntimeException("명함 항목은 최대 20개까지 등록 가능합니다.");
        }
        profileCardEntries.add(entry);
        entry.setProfileCard(this);
    }

    public void removeEntry(OriginProfileCardEntry entry) {
        this.profileCardEntries.remove(entry);
        entry.setProfileCard(null);
    }

    public FileProperty getProfileImage() {
        return profileCard.getProfileImage();
    }
}
