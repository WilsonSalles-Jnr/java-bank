package com.boreal.javabank.models.data;

import javafx.scene.control.TableView;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.ProviderNotFoundException;
import java.text.NumberFormat;

@AllArgsConstructor
@Getter
public class Customer {
    private String name;
    private TableView<Bank> banks;

    public Customer(String name) {
        this.name = name;
        banks = new TableView<>();
    }

    public void newBank(Bank bank) {
        banks.getItems().add(bank);
    }

    public void withdraw(String accountNumber, String value, Bank toAccount, TableView<Audit> audit, String toName) throws Exception {
        Double price = Double.parseDouble(value);

        Bank targetBank = banks.getItems().stream().filter(b -> b.getAccountNumber().equals(accountNumber)).findFirst().orElseThrow(() -> {
            audit.getItems().add(new Audit(name, String.format("Transferência bancária de %s para %s falhou", accountNumber, toAccount.getAccountNumber()), price * -1, "FALHA", Double.parseDouble("0")));

            return new Exception("Não foi encontrado a conta informada");
        });

        if (targetBank.getValue() < Double.parseDouble(value)) {
            audit.getItems().add(new Audit(name, String.format("Transferência bancária de %s para %s falhou", accountNumber, toAccount.getAccountNumber()), price * -1, "FALHA", targetBank.getValue()));
            throw new Exception("Saldo insuficiente");
        }
        targetBank.withdraw(price);
        toAccount.deposit(price);
        audit.getItems().add(new Audit(name, String.format("Transferência bancária de %s para %s", accountNumber, toAccount.getAccountNumber()), price * -1, "AUTORIZADO", targetBank.getValue()));
        audit.getItems().add(new Audit(toName, String.format("Recebido %s de %s", NumberFormat.getCurrencyInstance().format(price), toAccount.getAccountNumber()), price, "AUTORIZADO", toAccount.getValue()));
    }

    public Bank findBankByAccountNumber(String accountNumber) {
        return banks.getItems().stream().filter(b -> b.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
    }
}
