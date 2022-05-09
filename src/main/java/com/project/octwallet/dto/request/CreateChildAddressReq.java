package com.project.octwallet.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateChildAddressReq {
    String name;
    int offset;
    String pinHash;
    String updatePinHash;
}