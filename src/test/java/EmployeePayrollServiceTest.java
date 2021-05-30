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
    public void givenNewSalaryForEmployee_whenUpdated_shouldSyncWithDB_PrepareStatement() {
        EmployeePayrollDBService.getInstance();
        List<EmployeePayrollData> employeePayrollData = EmployeePayrollDBService.getInstance().readEmployeePayrollData();
        EmployeePayrollDBService.getInstance().updateEmployeeSalary("Terisa", 3000000.00);
        boolean result =  EmployeePayrollDBService.getInstance().checkEmployeePayrollSynWithDB("Terisa");
        System.out.println(employeePayrollData);
        Assertions.assertTrue(result);
    }
}
