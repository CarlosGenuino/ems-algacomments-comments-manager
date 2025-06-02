package com.algaworks.comments_manager.api.client;

import com.algaworks.comments_manager.api.model.ModerationInput;
import com.algaworks.comments_manager.api.model.ModerationOutput;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/moderate")
public interface ModerationClient {

    @PostExchange
    ModerationOutput approve(@RequestBody ModerationInput input);
}
