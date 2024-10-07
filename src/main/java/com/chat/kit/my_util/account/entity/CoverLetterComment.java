package com.chat.kit.my_util.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class CoverLetterComment extends AbstractComment {

    @ManyToOne
    @JoinColumn(name = "cover_letter_entry_id")
    private CoverLetterEntry coverLetterEntry;


    @Override
    public Memberr getPostOwner() {
        return coverLetterEntry.getCoverLetter().getAuthor();
    }
}