package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "poi")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Poi extends BaseTime {
    // index 중복 처리를 위해 작성자에 대한 index로 중복을 건다?
    @Id
    @GeneratedValue
    private Long id;


    @Column(columnDefinition = "TEXT")
    private String title;


    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer index;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Memberr author;

    @OneToMany(mappedBy = "poi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<PoiComment> poiComments = new ArrayList<>();

    public void addComment(PoiComment commentEntity) {
        this.poiComments.add(commentEntity);
        commentEntity.setPoi(this);
    }

    public void checkWrittenBy(Memberr member) {
        if (!this.author.equals(member)) {
            throw new RuntimeException( "작성자가 아닙니다.");
        }
    }
}