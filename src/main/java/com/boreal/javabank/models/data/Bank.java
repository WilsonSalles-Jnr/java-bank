package com.boreal.javabank.models.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Bank {
    private String accountNumber;
    private Double value;

    public Bank(String accountNumber, String value) {
        this.accountNumber = accountNumber;
        this.value = Double.parseDouble(value);
    }

    public void withdraw(Double newValue) {
        value -= newValue;
    }

    public void deposit(Double newValue) {
        value += newValue;
    }
}
