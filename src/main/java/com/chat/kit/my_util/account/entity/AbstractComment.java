package com.chat.kit.my_util.account.entity;

import com.chat.kit.my_util.account.enums.CommentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class AbstractComment extends BaseTime {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue
    private Long id;


    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Memberr author;

    @OneToOne
    private AnonymousUser anonymousAuthor;

    @OneToOne(mappedBy = "abstractComment", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_info_id")
    private TagInfo tagInfo;

    public void addTagInfo(TagInfo tagInfo) {
        this.tagInfo = tagInfo;
        tagInfo.setAbstractComment(this);
    }

    public void removeTagInfo() {
        if (tagInfo != null) {
            tagInfo.setAbstractComment(null);
        }
        this.tagInfo = null;
    }

    public Long getPostOwnerId() {
        return getPostOwner().getId();
    }

    public abstract Memberr getPostOwner();

    public CommentType getCommentType() {
        return content.length() > 100 ? CommentType.LONG :
                content.length() > 50 ? CommentType.MIDDLE :
                        CommentType.SHORT;
    }
}

