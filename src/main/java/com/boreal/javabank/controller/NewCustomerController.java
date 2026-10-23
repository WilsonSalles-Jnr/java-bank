package com.boreal.javabank.controller;

import com.boreal.javabank.models.data.Customer;
import com.boreal.javabank.service.NewCustomerService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.Setter;

public class NewCustomerController {
    @FXML
    private TextField nameField;
    @Setter
    public ListView<Customer> customerList;
    private NewCustomerService newCustomerService;

    public void initialize() {
        newCustomerService = new NewCustomerService();
    }

    @FXML
    public void handleSaveCustomer() {
        newCustomerService.saveCustomer(nameField, customerList);
    }
}