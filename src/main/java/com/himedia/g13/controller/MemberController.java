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
        if (result.getFieldError("userid") != null) {
            model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
        } else if (result.getFieldError("pwd") != null) {
            model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
        } else {
            // 정상 로그인 절차 진행
            MemberDto mdto = ms.getMember(memberdto.getUserid());
            if (mdto == null)
                model.addAttribute("message", "아이디/패스워드를 확인하세요");
            else if (!mdto.getPwd().equals(memberdto.getPwd()))
                model.addAttribute("message", "아이디/패스워드를 확인하세요");
            else if (mdto.getPwd().equals(memberdto.getPwd())) {
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", mdto);
                url = "redirect:/boardlist";
            } else
                model.addAttribute("message", "관리자에게 문의하세요");
        }
        return url;
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "member/joinForm";
    }

    @GetMapping("/idcheck")
    public String idcheck(@RequestParam("userid") String userid, Model model) {
        MemberDto mdto = ms.getMember(userid);
        if (mdto == null) model.addAttribute("result", -1);
        else model.addAttribute("result", 1);
        model.addAttribute("userid", userid);
        return "member/idcheck";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("dto") @Valid MemberDto memberdto, BindingResult result, Model model, @RequestParam(value = "reid", required = false) String reid, @RequestParam(value = "pwd_check", required = false) String pwd_check) {
        String url = "member/joinForm";
        if (result.getFieldError("userid") != null) {
            model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
        } else if (result.getFieldError("pwd") != null) {
            model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
        } else if (result.getFieldError("name") != null) {
            model.addAttribute("message", result.getFieldError("name").getDefaultMessage());
        } else if (result.getFieldError("email") != null) {
            model.addAttribute("message", result.getFieldError("email").getDefaultMessage());
        } else if (result.getFieldError("phone") != null) {
            model.addAttribute("message", result.getFieldError("phone").getDefaultMessage());
        }else if(reid==null || !memberdto.getUserid().equals(reid)){
            model.addAttribute("message","아이디 중복체크가 되지 않았습니다.");
        }else if(pwd_check==null || !pwd_check.equals(pwd_check)){
            model.addAttribute("message","비밀번호 확인이 일치하지 않습니다.");
        } else {
            // 정상 join 진행
            ms.insertMember(memberdto);
            model.addAttribute("message","회원 가입이 완료되었습니다. 로그인 하세요");
            url="member/loginForm";
        }
        return url;
    }
}
