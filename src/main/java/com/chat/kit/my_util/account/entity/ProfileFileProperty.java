package com.chat.kit.my_util.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileFileProperty extends FileProperty {
    @OneToOne
    @Setter
    private ProfileCard profileCard;
}
