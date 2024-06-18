package com.himedia.g13.controller;

import com.himedia.g13.dto.MemberDto;
import com.himedia.g13.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

    @Autowired
    MemberService ms;

    @GetMapping("/")
    public String root() {
        return "member/loginForm";
    }

    @PostMapping("login")
    public String login(@ModelAttribute("dto") @Valid MemberDto memberdto, BindingResult result, Model model, HttpServletRequest request) {
        String url = "member/loginForm";
        if(result.getFieldError("userid")!=null) {
            model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
        }else if(result.getFieldError("pwd")!=null) {
            model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
        }else{
            // 정상 로그인 절차 진행
            MemberDto mdto = ms.getMember(memberdto.getUserid());
            if(mdto==null)
                model.addAttribute("message","아이디/패스워드를 확인하세요");
            else if(!mdto.getPwd().equals(memberdto.getPwd()))
                model.addAttribute("message","아이디/패스워드를 확인하세요");
            else if(mdto.getPwd().equals(memberdto.getPwd())){
                HttpSession session = request.getSession();
                session.setAttribute("loginUser",mdto);
                url="redirect:/boardlist";
            }else
                model.addAttribute("message","관리자에게 문의하세요");
        }
        return url;
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "member/joinForm";
    }

    @GetMapping("/idcheck")
    public String idcheck(@RequestParam("userid") String userid, Model model) {
        if(userid==null)
        return "member/idcheck";
    }

}
