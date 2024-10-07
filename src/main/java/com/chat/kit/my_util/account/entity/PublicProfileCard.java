package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PublicProfileCard {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "profile_card_id")
    private ProfileCard profileCard;

    @OneToMany(mappedBy = "publicProfileCard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<PublicProfileCardEntry> publicProfileCardEntries = new ArrayList<>();

    public void addEntry(PublicProfileCardEntry entry) {
        publicProfileCardEntries.add(entry);
        entry.setPublicProfileCard(this);
    }

    public void removeEntry(PublicProfileCardEntry entry) {
        this.publicProfileCardEntries.remove(entry);
        entry.setPublicProfileCard(null);
    }


    public FileProperty getProfileImage() {
        return profileCard.getProfileImage();
    }
}
