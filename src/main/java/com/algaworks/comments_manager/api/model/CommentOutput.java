package com.algaworks.comments_manager.api.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CommentOutput {
    private UUID id;
    private String text;
    private String author;
    private LocalDateTime createdAt;
}
