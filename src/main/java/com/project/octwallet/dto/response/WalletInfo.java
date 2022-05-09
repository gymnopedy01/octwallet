package com.project.octwallet.dto.response;

import lombok.Data;

@Data
public class WalletInfo {
    String idx;
    String name;
    String type;
    String keyManagementType;
    String childAddressType;
    String status;
    Address mainAddress;
    Address feeAddress;
    String createdDate;
    String modifiedDate;
}
