package com.shop.controller;

import com.shop.dto.MemberSearchDto;
import com.shop.entity.Member;
import com.shop.service.MngService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MngController {
    private final MngService mngService;
    @GetMapping(value = {"/members","/members/{page}"})
    public String membersManage(MemberSearchDto memberSearchDto, @PathVariable("page") Optional<Integer> page,
                                Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 20);
        Page<Member> members = mngService.getAdminMemberPage(memberSearchDto, pageable);
        model.addAttribute("members", members);
        model.addAttribute("memberSearchDto", memberSearchDto);
        model.addAttribute("maxPage", 5);
        return "member/memberMng";
    }

    @PostMapping(value = "/member/modify")
    public @ResponseBody ResponseEntity modifyUser(@RequestBody Map<String, Object> user){
        try {
            mngService.modifyMember(user);
        }
        catch (Exception e){
            return new ResponseEntity<String>("오류 발생", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("성공", HttpStatus.OK);
    }
}