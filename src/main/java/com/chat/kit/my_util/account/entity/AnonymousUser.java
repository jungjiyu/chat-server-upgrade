package com.chat.kit.my_util.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnonymousUser extends User {

    @OneToOne
    private Invitation invitation;

    @OneToOne
    @JoinColumn(name = "comment_id")
    private AbstractComment comment;

    public void addInvitation(Invitation invitation) {
        this.invitation = invitation;
        invitation.setSubscriber(this);
    }

    public void addComment(AbstractComment comment) {
        this.comment = comment;
        comment.setAnonymousAuthor(this);
    }

}