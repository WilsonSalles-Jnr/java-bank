package com.boreal.javabank.controller;

import com.boreal.javabank.models.data.Bank;
import com.boreal.javabank.models.data.Customer;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.Setter;

public class NewBankController {
    public TextField accountNumber;
    public TextField accountValue;
    @Setter
    private Customer customer;
    @Setter
    private TableView<Bank> table;

    public void handleAddBank() {
        customer.newBank(new Bank(accountNumber.getText(), accountValue.getText()));
        table.getItems().setAll(customer.getBanks().getItems());
    }


}
