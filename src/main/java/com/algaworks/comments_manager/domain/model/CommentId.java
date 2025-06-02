package com.algaworks.comments_manager.domain.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class CommentId {

    private UUID value;

    public CommentId(UUID uuid){
        this.value = uuid;
    }

    public CommentId(String uuid){
        this.value = UUID.fromString(uuid);
    }
}
