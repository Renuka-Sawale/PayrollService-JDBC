import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EmployeePayrollServiceTest {
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollDBService employeePayrollDbService = new EmployeePayrollDBService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollDbService.readEmployeePayrollData();
        System.out.println(employeePayrollData);
        Assertions.assertEquals(3,employeePayrollData.size());
    }

    @Test
    public void givenNewSalaryForEmployee_whenUpdated_shouldSyncWithDB_PrepareStatement() {
        EmployeePayrollDBService.getInstance();
        List<EmployeePayrollData> employeePayrollData = EmployeePayrollDBService.getInstance().readEmployeePayrollData();
        EmployeePayrollDBService.getInstance().updateEmployeeSalary("Terisa", 3000000.00);
        boolean result =  EmployeePayrollDBService.getInstance().checkEmployeePayrollSynWithDB("Terisa");
        System.out.println(employeePayrollData);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollDBService.getInstance().readEmployeePayrollData();
        LocalDate startDate = LocalDate.of(2020, 05, 21);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = EmployeePayrollDBService.getInstance().readPayrollForDateRange(startDate, endDate);
        System.out.println(employeePayrollData);
        Assertions.assertEquals(1, employeePayrollData.size());
    }

    @Test
    public void givenEmployeePayrollData_WhenSumSalaryRetrievedByGender_ShouldReturnResult() {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        Map<String, Double> SumSalaryByGender = employeePayrollDBService.readSumOfEmployeeSalaryByGender();
        System.out.println(employeePayrollDBService.readSumOfEmployeeSalaryByGender());
        Assertions.assertTrue(SumSalaryByGender.get("M").equals(4000000.00) && SumSalaryByGender.get("F").equals(3000000.00));
    }

    @Test
    public void givenEmployeePayrollData_WhenAverageSalaryRetrievedByGender_ShouldReturnResult() {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        Map<String, Double> avgSalaryByGender = employeePayrollDBService.readAvgOfEmployeeSalaryByGender();
        System.out.println(employeePayrollDBService.readAvgOfEmployeeSalaryByGender());
        Assertions.assertTrue(avgSalaryByGender.get("M").equals(2000000.00) && avgSalaryByGender.get("F").equals(3000000.00));
    }

    @Test
    public void givenEmployeePayrollData_WhenMinSalaryRetrievedByGender_ShouldReturnResult() {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        Map<String, Double> minSalaryByGender = employeePayrollDBService.readMinEmployeeSalaryByGender();
        System.out.println(employeePayrollDBService.readMinEmployeeSalaryByGender());
        Assertions.assertTrue(minSalaryByGender.get("M").equals(1000000.00) && minSalaryByGender.get("F").equals(3000000.00));
    }

    @Test
    public void givenEmployeePayrollData_WhenMaxSalaryRetrievedByGender_ShouldReturnResult() {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        Map<String, Double> maxSalaryByGender = employeePayrollDBService.readMaxEmployeeSalaryByGender();
        System.out.println(employeePayrollDBService.readMaxEmployeeSalaryByGender());
        Assertions.assertTrue(maxSalaryByGender.get("M").equals(3000000.00) && maxSalaryByGender.get("F").equals(3000000.00));
    }

    @Test
    public void givenEmployeePayrollData_WhenCountTheSalaryRetrievedByGender_ShouldReturnResult() {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        Map<String, Double> countSalaryByGender = employeePayrollDBService.readCountEmployeeSalaryByGender();
        System.out.println(employeePayrollDBService.readCountEmployeeSalaryByGender());
        Assertions.assertTrue(countSalaryByGender.get("M").equals(2.0) && countSalaryByGender.get("F").equals(1.0));
    }
}
