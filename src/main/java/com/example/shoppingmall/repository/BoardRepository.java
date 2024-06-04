package com.example.shoppingmall.repository;

import com.example.shoppingmall.entity.Board;
import com.example.shoppingmall.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BoardRepository extends CrudRepository<Board, Long> {
}
