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

    opens org.connect to javafx.fxml;
    exports org.connect;
    exports org.connect.controller;
    opens org.connect.controller to javafx.fxml;
    exports org.connect.controller.logcontrollers;
    opens org.connect.model.entities to org.hibernate.orm.core;
    opens org.connect.controller.logcontrollers to javafx.fxml;
    exports org.connect.controller.settings;
    opens org.connect.controller.settings to javafx.fxml;
}