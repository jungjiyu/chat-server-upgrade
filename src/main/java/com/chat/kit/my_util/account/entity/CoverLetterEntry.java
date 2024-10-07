package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoverLetterEntry extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "cover_letter_id")
    private CoverLetter coverLetter;

    @Builder.Default
    @OneToMany(mappedBy = "coverLetterEntry", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private List<CoverLetterComment> coverLetterComments = new ArrayList<>();


    public void addComment(CoverLetterComment comment){
        coverLetterComments.add(comment);
        comment.setCoverLetterEntry(this);
    }

    public void removeComment(CoverLetterComment comment){
        coverLetterComments.remove(comment);
        comment.setCoverLetterEntry(null);
    }
}