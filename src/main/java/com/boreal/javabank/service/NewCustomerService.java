package com.boreal.javabank.service;

import com.boreal.javabank.models.data.Customer;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewCustomerService {
    public void saveCustomer(TextField nameField, ListView<Customer> customerList) {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            return;
        }

        customerList.getItems().add(new Customer(name));

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();

    }
}
