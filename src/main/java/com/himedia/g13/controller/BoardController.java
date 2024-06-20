package com.himedia.g13.controller;

import com.himedia.g13.dto.BoardDto;
import com.himedia.g13.dto.MemberDto;
import com.himedia.g13.dto.ReplyDto;
import com.himedia.g13.service.BoardService;
import jakarta.servlet.ServletContext;
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
    public String insertBoard(@ModelAttribute("dto") @Valid BoardDto boarddto, BindingResult result, HttpServletRequest request, Model model) {
        String url="board/insertBoardForm";
        if(result.getFieldError("pass")!=null){
            model.addAttribute("message",result.getFieldError("pass").getDefaultMessage());
        }else if(result.getFieldError("title")!=null){
            model.addAttribute("message",result.getFieldError("title").getDefaultMessage());
        }else if(result.getFieldError("content")!=null){
            model.addAttribute("message",result.getFieldError("content").getDefaultMessage());
        }else{
            bs.insertBoard(boarddto);
            url ="redirect:/boardlist";
        }
        return url;
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

    @GetMapping("/boardView")
    public ModelAndView boardView(@RequestParam("num")int num, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        HashMap<String, Object> result = bs.boardView(num);
        mav.addObject("board",result.get("board"));
        mav.addObject("replyList", result.get("replyList"));
        mav.setViewName("board/boardView");
        return mav;
    }

    @GetMapping("/boardViewWithoutCnt")
    public ModelAndView boardViewWithoutCnt(@RequestParam("num")int num, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        HashMap<String, Object> result = bs.boardViewWithoutCnt(num);
        mav.addObject("board",result.get("board"));
        mav.addObject("replyList", result.get("replyList"));
        mav.setViewName("board/boardView");
        return mav;
    }

    @PostMapping("/insertReply")
    public String insertReply(ReplyDto replyDto, HttpServletRequest request){
        bs.insertReply(replyDto);
        return "redirect:/boardViewWithoutCnt?num="+replyDto.getBoardnum();
    }

    @GetMapping("/deleteReply")
    public String deleteReply(@RequestParam("replynum")int replynum,@RequestParam("boardnum") int boardnum, HttpServletRequest request){
        bs.deleteReply(replynum,boardnum);
        return "redirect:/boardViewWithoutCnt?num="+boardnum;
    }

    @GetMapping("/updateBoardForm")
    public ModelAndView updateBoardForm(@RequestParam("num")int num, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        BoardDto bdto = bs.getBoard(num);
        mav.addObject("board",bdto);
        mav.setViewName("board/updateBoardForm");
        return mav;
    }

    @PostMapping("/updateBoard")
    public String updateBoard(@ModelAttribute("dto") @Valid BoardDto boarddto,
                              BindingResult result,
                              @RequestParam(value="oldimage", required = false) String oldimage,
                              @RequestParam(value="oldsavefilename", required = false) String oldsavefilename,
                              HttpServletRequest request,
                              Model model){
        String url = "board/boardUpdateForm";
        BoardDto bdto = bs.getBoard(boarddto.getNum());
        model.addAttribute("board",bdto);
        if(result.getFieldError("title")!=null){
            model.addAttribute("message","제목은 필수입력 사항입니다. ");
        }else if(result.getFieldError("content")!=null){
            model.addAttribute("message","게시물 내용은 비워둘 수 없습니다. ");
        }else{
            url = "redirect:/boardViewWithoutCnt?num="+boarddto.getNum();
            if(boarddto.getSavefilename()==null||boarddto.getSavefilename().equals("")){
                boarddto.setImage(oldimage);
                boarddto.setSavefilename(oldsavefilename);
            }
            bs.updateBoard(boarddto);
        }
        return url;
    }
    @GetMapping("/deleteBoard")
    public String deleteBoard(@RequestParam("num")int num, HttpServletRequest request){
        bs.deleteBoard(num);
        return "redirect:/boardlist";
    }

}
