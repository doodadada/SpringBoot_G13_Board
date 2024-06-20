package com.himedia.g13.dao;

import com.himedia.g13.dto.BoardDto;
import com.himedia.g13.dto.Paging;
import com.himedia.g13.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardDao {
    int getAllCount();

    List<BoardDto> selectBoard(Paging paging);

    int getReplyCount(BoardDto board);

    void insertBoard(BoardDto boarddto);

    void plusOneReadCount(int num);

    BoardDto getBoard(int num);

    List<ReplyDto> selectReply(int num);

    void insertReply(ReplyDto replyDto);

    void deleteReply(int replynum, int boardnum);

    void updateBoard(BoardDto boarddto);

    void deleteBoard(int num);
}
