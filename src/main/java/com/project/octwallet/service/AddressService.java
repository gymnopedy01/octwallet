package com.project.octwallet.service;

import com.project.octwallet.dto.Address;
import com.project.octwallet.operation.OctetOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AddressService {

    @Autowired
    OctetOperation octetOperation;

    /**
     * 자식주소 목록 조회
     * https://tetco.readme.io/reference/getchildaddresses
     *
     * @return
     */
    public List<Address> getChildAddresses() {
        return octetOperation.getChildAddresses();
    }


}