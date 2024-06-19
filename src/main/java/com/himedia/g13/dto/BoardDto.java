package com.himedia.g13.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardDto {
    private int num;
    private String userid;
    @NotEmpty(message="비밀번호를 입력하세요(게시글 수정 삭제시 필요합니다)")
    @NotNull(message="비밀번호를 입력하세요(게시글 수정 삭제시 필요합니다)")
    private String pass;
    private String email;
    @NotEmpty(message="제목을 입력하세요")
    @NotNull(message="제목을 입력하세요")
    private String title;
    @NotEmpty(message="내용을 입력하세요")
    @NotNull(message="내용을 입력하세요")
    private String content;
    private int readcount;
    private Timestamp writedate;
    private String imgfilename;
    private int replycnt;
}
