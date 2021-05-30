import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeePayrollServiceTest {
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollDBService employeePayrollDbService = new EmployeePayrollDBService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollDbService.readEmployeePayrollData();
        System.out.println(employeePayrollData);
        Assertions.assertEquals(3,employeePayrollData.size());
    }

    @Test
    public void givenNewSalaryForEmployee_whenUpdated_shouldSyncWithDB() {
            EmployeePayrollDBService employeePayrollService = new EmployeePayrollDBService();
            List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData();
            int result = employeePayrollService.updateEmployeeSalary("Terisa", 3000000.00);
            System.out.println(employeePayrollData);
            Assertions.assertEquals(1, result);
    }
}
