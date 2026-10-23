package com.boreal.javabank.service;

import com.boreal.javabank.models.data.Customer;
import javafx.scene.control.ListView;

public class MainMenuService {
    public void handlAddUser(ListView<Customer> customerList, String name) {
        customerList.getItems().add(new Customer(name));
    }
}
