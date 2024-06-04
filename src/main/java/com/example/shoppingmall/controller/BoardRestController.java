package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.BoardDto;
import com.example.shoppingmall.entity.Board;
import com.example.shoppingmall.entity.Member;
import com.example.shoppingmall.repository.BoardRepository;
import com.example.shoppingmall.security.MemberDetailsImpl;
import com.example.shoppingmall.security.MemberRoleEnum;
import com.example.shoppingmall.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class BoardRestController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;


    // 게시글 쓰기
    @PostMapping("/boards")
    public ResponseEntity<Board> createBoard(@RequestBody BoardDto boardDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        Board createdBoard = boardService.createBoard(boardDto, memberDetails);
        return ResponseEntity.ok(createdBoard);
    }



    // 게시글 삭제
    @GetMapping("/boards")
    public ResponseEntity<List<Board>> getAllBoards() {
            List<Board> boards = (List<Board>) boardRepository.findAll();
            return ResponseEntity.ok(boards);
    }


    // 게시글작성자와 로그인한 유저가 같은경우 또는 ADMIN권한을 가질수 있을 경우 삭제가 가능
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        Optional<Board> boardOptional = boardRepository.findById(id);

        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            if (board.getMember().getId().equals(memberDetails.getMember().getId()) || memberDetails.getMember().getMembertype().equals(MemberRoleEnum.ADMIN)) {
                boardService.deleteBoard(id);
                return ResponseEntity.ok("삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }






}
