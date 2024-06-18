package com.himedia.g13.dao;

import com.himedia.g13.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDao {
    MemberDto getMember(String userid);

    void insertMember(MemberDto mdto);
}
