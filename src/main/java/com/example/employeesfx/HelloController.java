package com.example.employeesfx;

import Model.Employee;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.Properties;

public class HelloController {
    @FXML
    VBox employeesVBox = new VBox();

    @FXML
    TextField nameTxtField;

    @FXML
    TextField salaryTxtField;

    @FXML
    TextField capitalTxtField;

    Connection conn;
    Statement st;




    public void initialize() throws SQLException {
        conn = connectToDB();
        st = conn.createStatement();
        loadEmployees();
    }

    private static Connection connectToDB() {
        String url = "jdbc:postgresql://localhost:5432/trainingDatabase?user=postgres&password=123";//&ssl=true
        try {
            Connection conn = DriverManager.getConnection(url);
            System.out.println("подключено");
            return conn;
        } catch (Exception e) {
            System.out.println("не удалось подключиться к базе. "+e.getMessage());
            return null;
        }
    }



    private void loadEmployees() throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM employee ORDER BY employee_name");
        employeesVBox.setSpacing(10);
        HBox hbFirst = new HBox(30, new Label("Name"),  new Label("Capital"), new Label("Salary"), new Label("Food"));
        employeesVBox.getChildren().add(hbFirst);
        while (rs.next()) {
            Employee emp = new Employee(rs.getString("employee_name"), rs.getInt("employee_salary"), rs.getInt("employee_capital"));
            int pk = rs.getInt("employee_id");
            System.out.println(emp.name);
            HBox hb = new HBox();
            hb.setSpacing(15);
            Label lbName = new Label(emp.name);
            Label lbCapital = new Label(String.valueOf(emp.capital));
            TextField tfSalary = new TextField(String.valueOf(emp.salary));
            tfSalary.setPrefWidth(60);
            TextField tfFood = new TextField();
            tfFood.setPrefWidth(60);

            Button btTakeSalary = new Button("TakeSal");
            btTakeSalary.setOnAction(ActionEvent->{
                emp.salary = Integer.parseInt(tfSalary.getText());
                emp.takeSalary(emp.salary );
                lbCapital.setText(String.valueOf(emp.capital));



                try {
                    // такой запрос проходит
//                    st.execute("UPDATE employee SET employee_capital =555555, employee_salary = 77777 WHERE employee_id =2");
                    // запросы с переменными не проходят
                    st.execute("UPDATE employee SET employee_capital =" + emp.capital + ", employee_salary =" + emp.salary + "WHERE employee_id =" + pk);
//                    st.execute("UPDATE employee SET employee_capital =" + String.valueOf(emp.capital) + ", employee_salary =" + String.valueOf(emp.salary) + "WHERE employee_id =" + String.valueOf(pk));
//                    st.execute("UPDATE employee SET employee_capital =" + Integer.toString(emp.capital) + ", employee_salary =" + Integer.toString(emp.salary) + "WHERE employee_id =" + Integer.toString(pk));



                    // так работает, но много строчек вместо одного запроса
//                    String sql = "UPDATE employee SET employee_capital = ?, employee_salary = ? WHERE employee_id = ?";
//                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
//                    preparedStatement.setInt(1, emp.capital);
//                    preparedStatement.setInt(2, emp.salary);
//                    preparedStatement.setInt(3, pk);
//                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });


            Button btBar = new Button("GoToBar");
            Button btBuyFood = new Button("BuyFood");
            Button btResign = new Button("Resign");


            hb.getChildren().add(lbName);
            hb.getChildren().add(lbCapital);
            hb.getChildren().add(tfSalary);
            hb.getChildren().add(tfFood);
            hb.getChildren().add(btTakeSalary);
            hb.getChildren().add(btBar);
            hb.getChildren().add(btBuyFood);
            hb.getChildren().add(btResign);

            employeesVBox.getChildren().add(hb);
        }
    }



}