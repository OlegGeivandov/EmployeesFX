module com.example.employeesfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.employeesfx to javafx.fxml;
    exports com.example.employeesfx;
}