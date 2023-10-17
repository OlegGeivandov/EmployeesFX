module com.example.employeesfx {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.employeesfx to javafx.fxml;
    exports com.example.employeesfx;
}