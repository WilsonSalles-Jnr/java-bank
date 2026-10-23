package com.boreal.javabank.controller;

import com.boreal.javabank.MainApplication;
import com.boreal.javabank.models.data.Audit;
import com.boreal.javabank.models.data.Bank;
import com.boreal.javabank.models.data.Customer;
import com.boreal.javabank.service.MainMenuService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MainMenuController {
    public ListView<Customer> customerList;
    private final MainMenuService mainMenuService;
    public TableColumn<Bank, String> accountNumber;
    public TableColumn<Bank, String> accountValue;
    public TableView<Bank> bankTable;
    public Label customerLabel;
    public VBox bottomBox;
    public BorderPane borderPane;
    public TableView<Audit> auditTable;
    public TableColumn<Audit, String> auditName;
    public TableColumn<Audit, String> auditDescription;
    public TableColumn<Audit, String> auditValue;
    public TableColumn<Audit, String> auditStatus;
    public TableColumn<Audit, String> auditAmount;

    private Customer currentCustomer;

    public void initialize() {
        borderPane.getBottom().setVisible(false);
        borderPane.getBottom().setManaged(false);

        customerList.setCellFactory(e -> new ListCell<>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        handleInitializeTest();
        accountNumber.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getAccountNumber()));
        accountValue.setCellValueFactory(e -> new SimpleStringProperty(NumberFormat.getCurrencyInstance().format(e.getValue().getValue())));
        auditName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getCustomerName()));
        auditDescription.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getTransactionDescription()));
        auditValue.setCellValueFactory(e -> new SimpleStringProperty(NumberFormat.getCurrencyInstance().format(e.getValue().getTransactionValue())));
        auditStatus.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getSituation()));
        auditAmount.setCellValueFactory(e -> new SimpleStringProperty(NumberFormat.getCurrencyInstance().format(e.getValue().getCurrentValue())));

    }

    public void handleNewCustomer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("NewCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        NewCustomerController controller = fxmlLoader.getController();
        controller.setCustomerList(customerList);

        Stage stage = new Stage();
        stage.setTitle("Novo cliente");
        stage.setScene(scene);
        stage.show();
    }

    public void handleNewBank() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("NewBank.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        NewBankController controller = fxmlLoader.getController();
        controller.setCustomer(currentCustomer);
        controller.setTable(bankTable);

        Stage stage = new Stage();
        stage.setTitle("Novo banco");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void handleSelectCustomer(MouseEvent event) {
        if(event.getClickCount() == 2) {
            Customer customer = customerList
                    .getSelectionModel()
                    .getSelectedItem();


            if (customer != null) {
                customerLabel.setText(customer.getName());
                currentCustomer = customer;
                bankTable.getItems().setAll(customer.getBanks().getItems());
            }

            borderPane.getBottom().setManaged(currentCustomer != null);
            borderPane.getBottom().setVisible(currentCustomer != null);
            bottomBox.setVisible(currentCustomer != null);
        }

    }

    public void handleTransfer(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Transfer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        TransferController controller = fxmlLoader.getController();
        controller.populateDestinatary(customerList);
        controller.setCustomer(currentCustomer);
        controller.setMainMenuController(this);

        Stage stage = new Stage();
        stage.setTitle("Transferir");
        stage.setScene(scene);
        stage.show();

    }

    private void handleInitializeTest() {
        TableView<Bank> bank = new TableView<>();
        bank.getItems().add(new Bank("123","321"));
        customerList.getItems().add(new Customer("Wilson", bank));
        TableView<Bank> bank2 = new TableView<>();
        bank2.getItems().add(new Bank("321","123"));
        bank2.getItems().add(new Bank("592","123"));
        customerList.getItems().add(new Customer("Teste", bank2));
    }

    public void transfer(String accountNumber, String text, Bank selectedItem, String toName) throws Exception {
        AtomicReference<Bank> bank = new AtomicReference<Bank>();
        customerList.getItems().stream().filter(c -> c.findBankByAccountNumber(selectedItem.getAccountNumber()) != null).findFirst().ifPresent(p -> bank.set(p.findBankByAccountNumber(selectedItem.getAccountNumber())));
        currentCustomer.withdraw(accountNumber, text, bank.get(), auditTable, toName);
        bankTable.getItems().setAll(currentCustomer.getBanks().getItems());
    }
}
