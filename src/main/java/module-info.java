module com.boreal.javabank {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires static lombok;

    opens com.boreal.javabank to javafx.fxml;
    opens com.boreal.javabank.controller to javafx.fxml;

    exports com.boreal.javabank;
    exports com.boreal.javabank.controller;
    exports com.boreal.javabank.service;
    exports com.boreal.javabank.models.data;
}