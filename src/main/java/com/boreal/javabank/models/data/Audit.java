package com.boreal.javabank.models.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Audit {
    private String customerName;
    private String transactionDescription;
    private Double transactionValue;
    private String situation;
    private Double currentValue;
}
