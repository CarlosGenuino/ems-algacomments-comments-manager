package com.algaworks.comments_manager.api.controller;

import com.algaworks.comments_manager.api.client.ModerationClient;
import com.algaworks.comments_manager.api.model.CommentInput;
import com.algaworks.comments_manager.api.model.CommentOutput;
import com.algaworks.comments_manager.api.model.ModerationInput;
import com.algaworks.comments_manager.api.model.ModerationOutput;
import com.algaworks.comments_manager.domain.model.Comment;
import com.algaworks.comments_manager.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final ModerationClient moderationClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutput createComment(@RequestBody CommentInput input){
        Comment entity = Comment.builder()
                            .text(input.getText())
                            .author(input.getAuthor())
                            .build();
        ModerationInput moderationInput = ModerationInput.builder()
                .commentId(entity.getId().toString())
                .text(entity.getText())
                .build();
        ModerationOutput moderation = moderationClient.approve(moderationInput);

        if (Boolean.FALSE.equals(moderation.getApproved())){
            commentRepository.delete(entity);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        commentRepository.saveAndFlush(entity);
        return CommentOutput.builder()
                .id(entity.getId())
                .text(entity.getText())
                .author(entity.getAuthor())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @GetMapping("/{commentId}")
    public CommentOutput findOne(@PathVariable UUID commentId){
        Comment entity = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return CommentOutput.builder()
                .id(entity.getId())
                .text(entity.getText())
                .author(entity.getAuthor())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @GetMapping
    public Page<CommentOutput> listPagedComments(@PageableDefault Pageable pageable){
        Page<Comment> entityPaged = commentRepository.findAll(pageable);
        return entityPaged.map(e -> CommentOutput
                                                .builder()
                                                .id(e.getId())
                                                .text(e.getText())
                                                .author(e.getAuthor())
                                                .createdAt(e.getCreatedAt())
                                                .build()
        );
    }

}
