module husseinm.opdss {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    requires com.jfoenix;
    requires java.desktop;
    requires java.prefs;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens husseinm.opdss to javafx.fxml, javafx.base, javafx.controls, javafx.graphics;
    exports husseinm.opdss;
}