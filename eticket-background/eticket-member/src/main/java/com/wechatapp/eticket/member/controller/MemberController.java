package com.wechatapp.eticket.member.controller;

import com.wechatapp.eticket.core.annotation.CheckLogin;
import com.wechatapp.eticket.member.dto.LoginResponseDTO;
import com.wechatapp.eticket.member.dto.UserInfoDTO;
import com.wechatapp.eticket.member.service.IMemberService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberController {

    private final IMemberService memberService;

    /**
     * 
     * @param userInfoDTO
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody UserInfoDTO userInfoDTO) throws WxErrorException {
        return memberService.login(userInfoDTO);
    }

    /**
     * 
     * @param wechatOpenId
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/updateLoginUserInfo")
    @CheckLogin
    public LoginResponseDTO updateLoginUserInfo(@RequestParam("wechatOpenId") String wechatOpenId) throws WxErrorException {
        return memberService.updateLoginUserInfo(wechatOpenId);
    }

}
