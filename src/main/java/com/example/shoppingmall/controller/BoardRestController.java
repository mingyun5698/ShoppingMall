package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.BoardDto;
import com.example.shoppingmall.entity.Board;
import com.example.shoppingmall.security.MemberDetailsImpl;
import com.example.shoppingmall.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BoardRestController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity<Board> createBoard(@RequestBody BoardDto boardDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        Board createdBoard = boardService.createBoard(boardDto, memberDetails);
        return ResponseEntity.ok(createdBoard);
    }
}
