package com.example.shoppingmall.repository;

import com.example.shoppingmall.entity.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long> {
}
