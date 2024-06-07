package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.BoardDto;
import com.example.shoppingmall.entity.Board;
import com.example.shoppingmall.entity.Member;
import com.example.shoppingmall.repository.BoardRepository;
import com.example.shoppingmall.security.MemberDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BoardService {

    @Autowired
    private final BoardRepository boardRepository;

    // 게시글 만들기 메소드
    public Board createBoard(BoardDto boardDto, MemberDetailsImpl memberDetails) {

        Long id = null;
        String title = boardDto.getTitle();
        String content = boardDto.getContent();
        Member member_id = memberDetails.getMember();
        Board board = new Board(id, title, content, member_id);
        boardRepository.save(board);

        return board;
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public Board editBoard(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).orElse(null);
        if (board != null) {
            board.setContent(boardDto.getContent());
            board.setTitle(boardDto.getTitle());
            boardRepository.save(board);
        } else {
            // Handle the case where the board is not found
            // Maybe throw an exception or return null
        }

        return board;
    }



}
