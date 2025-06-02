package com.algaworks.comments_manager.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModerationInput {
    private String commentId;
    private String text;
}
