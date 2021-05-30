import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {

    private static EmployeePayrollDBService employeePayrollDBService;
    private static PreparedStatement employeePayrollStatement;

    public EmployeePayrollDBService() {

    }

    public  static EmployeePayrollDBService getInstance() {
        if (employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?user=root";
        String userName = "root";
        Connection connection;
        System.out.println("Connecting to database " +jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, "Renuka@1994");
        System.out.println("Connection is Successful! " +connection);
        return connection;
    }

    private List<EmployeePayrollData> getEmployeePayrollServiceData(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    public int updateEmployeeSalary(String name, double salary) {
        String sql = String.format("Update employee_payroll set salary = %.2f where name = '%s';", salary, name);
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean checkEmployeePayrollSynWithDB(String name) {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollServiceData(name);
        return employeePayrollDataList.equals(getEmployeePayrollServiceData(name));
    }

    public List<EmployeePayrollData> getEmployeePayrollServiceData(String name) {
        List<EmployeePayrollData> employeePayrollList = null;
        if (this.employeePayrollStatement == null)
            this.prepareStatementForEmployeeData();
        try {
            employeePayrollStatement.setString(1,name);
            ResultSet resultSet = employeePayrollStatement.executeQuery();
            employeePayrollList = this.getEmployeePayrollServiceData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    private void prepareStatementForEmployeeData() {
        try {
            Connection connection = this.getConnection();
            String sql = "select * from employee_payroll where name = ?";
            employeePayrollStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EmployeePayrollData> readEmployeePayrollData() {
        String sql = "select * from employee_payroll; ";
        return this.getEmployeePayrollDataUsingDB(sql);
    }

    public List<EmployeePayrollData> readPayrollForDateRange(LocalDate startDate, LocalDate endDate) {
        String query = String.format("select * from employee_payroll where start between '%s' and '%s' ",
                Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getEmployeePayrollDataUsingDB(query);
    }

    private List<EmployeePayrollData> getEmployeePayrollDataUsingDB(String query) {
        List<EmployeePayrollData> employeePayrolllist = new ArrayList<>();
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            employeePayrolllist = this.getEmployeePayrollServiceData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrolllist;
    }
}


