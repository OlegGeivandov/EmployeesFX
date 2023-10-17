package Model;

import java.util.Random;

public class Employee {
    public String name;
    public int salary;

    public int capital;

    public Employee(String name, int salary, int capital) {
        this.name = name;
        this.salary = salary;
        this.capital = capital;
    }

    public void takeSalary(int sal){
        capital+=sal;
    }

    public void drinkMoney(){
        capital-=(new Random()).nextInt(300, 3000);
    }

    public void buyFood(int amount){
        capital-=amount;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", capital=" + capital +
                '}';
    }
}
