package com.chat.kit.my_util.account.controller;

import com.chat.kit.my_util.account.dto.ApiResponse;
import com.chat.kit.my_util.account.dto.RegisterRequest;
import com.chat.kit.my_util.account.dto.RegisterResponse;
import com.chat.kit.my_util.account.enums.SuccessCode;
import com.chat.kit.my_util.account.service.AccountAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountAppService accountAppService;

    @PostMapping("/normal")
    public ApiResponse<RegisterResponse.Success> registerNormalType(@Validated @RequestBody RegisterRequest.Create request,
                                                                    @RequestParam(required = true) String token) {
        return ApiResponse.success(SuccessCode.CREATED, accountAppService.joinNormalType(request, token));
    }



}
