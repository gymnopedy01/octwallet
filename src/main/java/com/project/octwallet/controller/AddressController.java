package com.project.octwallet.controller;

import com.project.octwallet.dto.response.Address;
import com.project.octwallet.operation.OctetOperation;
import com.project.octwallet.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("oct")
@Controller
public class AddressController {


    @Autowired
    AddressService addressService;

    @GetMapping("child-addresses")
    public String childAddresses(Model model) {

        List<Address> list = addressService.getChildAddresses();
        model.addAttribute("childaddresses", list);

        return "address/child-addresses";
    }

    @PostMapping("child-addresses/create")
    public String createChildAddresses(Model model) {

        List<OctetOperation.CreateChildAddressRes> createChildAddressRes = addressService.createChildAddress();
        log.info("createChildAddress : {}", createChildAddressRes);

        return "redirect:/oct/child-addresses";

    }

    @ResponseBody
    @GetMapping("child-addresses/{address}")
    public ResponseEntity childAddressBalance(@PathVariable(required = false) Optional<String> address) {
        return new ResponseEntity(
                addressService.getChildAddressBalance(address.get())
                , HttpStatus.OK
        );
    }

}
