package com.project.octwallet.dto;

import lombok.Data;

@Data
public class WalletInfo {

    String idx;
    String name;
    String type;
    String keyManagementType;
    String childAddressType;
    String status;
    Address mainAddress; //추가적업
    Address feeAddress;  //추가작업
    String createdDate;
    String modifiedDate;

    @Data
    public static class Address {
        String idx;
        String address;
        String name;
        String derivationIndex;
        String type;
        String status;
        String createdDate;
        String modifiedDate;
    }

}
