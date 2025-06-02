package com.algaworks.comments_manager.api.model;

import lombok.Data;

@Data
public class ModerationOutput {
    private Boolean approved;
    private String reason;
}
