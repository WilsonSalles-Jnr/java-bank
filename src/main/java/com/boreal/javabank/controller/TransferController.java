package com.boreal.javabank.controller;

import com.boreal.javabank.models.data.Bank;
import com.boreal.javabank.models.data.Customer;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import lombok.Setter;

import java.text.NumberFormat;

public class TransferController {
    public ChoiceBox<Customer> destinatary;
    public ChoiceBox<Bank> account;
    public TextField transferValue;
    public Label fromName;
    public ChoiceBox<Bank> fromAccounts;
    public Label failedMessage;
    public Double currentValue;
    public Label currentAmount;

    @Setter
    private MainMenuController mainMenuController;

    public void initialize() {
        destinatary.setConverter(destinataryConverter());
        destinatary.getSelectionModel().selectedItemProperty().addListener(destinataryListener());
        account.setConverter(bankConverter());
        fromAccounts.setConverter(bankConverter());
        fromAccounts.getSelectionModel().selectedItemProperty().addListener(fromAccountListener());

    }

    private ChangeListener<Bank> fromAccountListener() {
        return (observable, oldBank, bank) -> {
            currentValue = bank.getValue();
            currentAmount.setText(String.format("SALDO: R$ %s", NumberFormat.getCurrencyInstance().format(bank.getValue())));
        };
    }

    private static StringConverter<Customer> destinataryConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(Customer customer) {
                return customer == null ? "" : customer.getName();
            }

            @Override
            public Customer fromString(String string) {
                return null;
            }
        };
    }

    private ChangeListener<Customer> destinataryListener() {
        return (observable, oldCustomer, newCustomer) -> {
            account.getSelectionModel().clearSelection();
            account.getItems().clear();

            if (newCustomer != null) {
                account.getItems().setAll(newCustomer.getBanks().getItems());

                if (!account.getItems().isEmpty()) {
                    account.getSelectionModel().selectFirst();
                }
            }
        };
    }

    public void populateDestinatary(ListView<Customer> customers) {
        destinatary.getItems().addAll(customers.getItems());
    }

    public void setCustomer(Customer customer) {
        fromName.setText(customer.getName());
        fromAccounts.getItems().addAll(customer.getBanks().getItems());
        if(!fromAccounts.getItems().isEmpty()) {
            fromAccounts.getSelectionModel().selectFirst();
        }
    }

    public void handleTransfer(ActionEvent actionEvent) throws Exception {
        try {
        failedMessage.setVisible(false);
        mainMenuController.transfer(fromAccounts.getSelectionModel().getSelectedItem().getAccountNumber(), transferValue.getText(), account.getSelectionModel().getSelectedItem(), destinatary.getSelectionModel().getSelectedItem().getName());
        currentValue -= Double.parseDouble(transferValue.getText());
        currentAmount.setText(String.format("SALDO: R$ %s", NumberFormat.getCurrencyInstance().format(currentValue)));

        } catch (Exception e) {
            failedMessage.setText(e.getMessage());
            failedMessage.setVisible(true);
        }
    }

    private StringConverter<Bank> bankConverter() {
        return new StringConverter<Bank>() {
            @Override
            public String toString(Bank bank) {
                return bank != null ? bank.getAccountNumber() : "";
            }

            @Override
            public Bank fromString(String string) {
                return null;
            }
        };
    }

}
