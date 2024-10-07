package com.chat.kit.my_util.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public abstract class FileProperty extends BaseTime {
    @Id
    @GeneratedValue
    protected Long id;
    protected String originalName;
    protected String savedName;
    protected String contentType;
    protected Long size;
    protected String path;
    @ManyToOne
    protected Memberr uploader;

}

