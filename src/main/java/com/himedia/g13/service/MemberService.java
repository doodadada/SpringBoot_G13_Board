package com.himedia.g13.service;

import com.himedia.g13.dao.IMemberDao;
import com.himedia.g13.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    IMemberDao mdao;

    public MemberDto getMember(String userid) {
        return mdao.getMember(userid);
    }
}
