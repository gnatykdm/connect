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
    requires org.hibernate.orm.core;
    requires java.naming;

    // WebSocket API
    requires javax.websocket.api;

    // Spring Messaging
    requires spring.messaging;
    requires spring.websocket;
    requires spring.context;

    // Opening packages to javafx.fxml and hibernate.orm.core
    opens org.connect to javafx.fxml;
    exports org.connect;
    exports org.connect.controller;
    opens org.connect.controller to javafx.fxml;
    exports org.connect.controller.logcontrollers;
    opens org.connect.model.entities to org.hibernate.orm.core;
    opens org.connect.controller.logcontrollers to javafx.fxml;
}