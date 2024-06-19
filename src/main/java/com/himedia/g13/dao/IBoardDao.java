package com.himedia.g13.dao;

import com.himedia.g13.dto.BoardDto;
import com.himedia.g13.dto.Paging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardDao {
    int getAllCount();

    List<BoardDto> selectBoard(Paging paging);

    int getReplyCount(BoardDto board);

    void insertBoard(BoardDto board);
}
