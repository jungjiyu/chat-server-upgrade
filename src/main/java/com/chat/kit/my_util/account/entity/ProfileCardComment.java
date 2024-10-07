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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProfileCardComment extends AbstractComment {

    @ManyToOne
    @JoinColumn(name = "profile_card_id")
    private ProfileCard profileCard;


    @Override
    public Memberr getPostOwner() {
        return profileCard.getAuthor();
    }

}