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
public class CoverLetter {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "coverLetter")
    private Memberr author;

    @OneToMany(mappedBy = "coverLetter", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<CoverLetterEntry> coverLetterEntries = new ArrayList<>();

    public void addEntries(List<CoverLetterEntry> entries) {
        coverLetterEntries.addAll(entries);
        entries.forEach(entry -> entry.setCoverLetter(this));
    }

    public void addEntry(CoverLetterEntry entry) {
        coverLetterEntries.add(entry);
        entry.setCoverLetter(this);
    }

    public void removeEntry(CoverLetterEntry entry) {
        coverLetterEntries.remove(entry);
        entry.setCoverLetter(null);
    }
}
