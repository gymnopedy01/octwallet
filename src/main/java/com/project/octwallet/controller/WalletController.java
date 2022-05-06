package com.project.octwallet.controller;

import com.project.octwallet.dto.WalletInfo;
import com.project.octwallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("oct")
@Controller
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("sample")
    public String sample() {
        return "sample";
    }


    @GetMapping("mywallet")
    public String mywallet(Model model) {

        WalletInfo mywallet = walletService.myWalletInfoRestTemplate(140);

        model.addAttribute("mywallet", mywallet);

        return "mywallet";
    }

}
