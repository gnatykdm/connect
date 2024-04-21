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

    opens org.chatgui.chatgui to javafx.fxml;
    exports org.chatgui.chatgui;
    exports org.chatgui.chatgui.controller;
    opens org.chatgui.chatgui.controller to javafx.fxml;
    exports org.chatgui.chatgui.controller.logcontrollers;
    opens org.chatgui.chatgui.controller.logcontrollers to javafx.fxml;
}