package com.project.octwallet.dto;

import lombok.Data;

@Data
public class Address {
    String idx;
    String address;
    String name;
    String derivationIndex;
    String type;
    String status;
    String createdDate;
    String modifiedDate;
}