module org.chatgui.chatgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires static lombok;
    requires jakarta.persistence;
    requires java.validation;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens org.chatgui to javafx.fxml;
    exports org.chatgui;
    exports org.chatgui.controller;
    opens org.chatgui.controller to javafx.fxml;
    exports org.chatgui.controller.logcontrollers;
    opens org.chatgui.controller.logcontrollers to javafx.fxml;
}