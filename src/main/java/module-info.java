module com.mycompany.java_tp2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    opens com.mycompany.java_tp2 to javafx.fxml;
    exports com.mycompany.java_tp2;
    requires rxtx;
}
