package com.project.octwallet.controller;

import com.project.octwallet.dto.Address;
import com.project.octwallet.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequestMapping("oct")
@Controller
public class AddressController {


    @Autowired
    AddressService addressService;

    @GetMapping("childaddresses")
    public String childaddresses(Model model) {

        List<Address> list = addressService.getChildAddresses();
        model.addAttribute("childaddresses", list);

        return "address/childaddresses";
    }

}
