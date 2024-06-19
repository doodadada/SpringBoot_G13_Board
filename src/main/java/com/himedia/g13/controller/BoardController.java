package com.himedia.g13.controller;

import com.himedia.g13.dto.BoardDto;
import com.himedia.g13.dto.MemberDto;
import com.himedia.g13.service.BoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

@Controller
public class BoardController {

    @Autowired
    BoardService bs;

    @GetMapping("/boardlist")
    public ModelAndView boardlist(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        MemberDto loginUser = (MemberDto) session.getAttribute(("loginUser"));
        if (loginUser == null) {
            mav.setViewName("member/loginForm");
        } else {
            HashMap<String, Object> result = bs.selectBoard(request);
            mav.addObject("boardList", result.get("boardList"));
            mav.addObject("paging", result.get("paging"));
            mav.addObject(result);
        }

        mav.setViewName("board/boardList");
        return mav;
    }

    @GetMapping("/insertBoardForm")
    public String insertBoardForm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") == null) return "member/loginForm";
        else return "board/insertBoardForm";
    }

    @GetMapping("/selectimg")
    public String selectimg() {
        return "board/selectimg";
    }

    @PostMapping("insertBoard")
    public String insertBoard(BoardDto board, HttpServletRequest request) {
        bs.insertBoard(board);
        return "redirect:/boardlist";
    }

    @Autowired
    ServletContext context;

    @PostMapping("/fileupload")
    public String fileUpload(
            @RequestParam("image") MultipartFile file,
            HttpServletRequest request,
            Model model) {
        String path = context.getRealPath("/upload");

        Calendar today = Calendar.getInstance();
        long t = today.getTimeInMillis();

        //파일이름 추출
        String filename = file.getOriginalFilename();
        String fn1 = filename.substring(0, filename.indexOf(".")); //abc.jsp -> abc
        String fn2 = filename.substring(filename.indexOf(".") + 1); //abc.jsp ->jsp
        String uploadPath = path + "/" + fn1 + t + "." + fn2; //저장경로+ abc123456789.jsp

        System.out.println(uploadPath);
        try {
            file.transferTo(new File(uploadPath)); //파일 업로드
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("image",filename); // 사용자가 선택한 파일이름
        model.addAttribute("savefilename",fn1+t+"."+fn2); //서버에 저장되는 이름

        return "board/completupload";
    }
}
