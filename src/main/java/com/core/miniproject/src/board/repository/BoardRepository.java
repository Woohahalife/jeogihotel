package com.core.miniproject.src.board.repository;

import com.core.miniproject.src.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Override
    Board save(Board board);

    @Query("""
    select b
    from Board b
    where b.is_deleted=false
""")
    @Override
    List<Board> findAll();
}
