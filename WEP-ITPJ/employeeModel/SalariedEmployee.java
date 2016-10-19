package employeeModel;

public class SalariedEmployee extends Employee {

	private double annualSalary;
	
	public SalariedEmployee(String[] name, String[] address, int[] date, double salary) {
		super(name, address, date);
		setAnnualSalary(salary);
	}
	
	private void setAnnualSalary(double salary) {
		this.annualSalary = salary;
	}
	
	// getter for annual salary, but implements the inherited abstract method
	public double[] getSalaryInfo() {
		double[] salInfo = {annualSalary};
		return salInfo;
	}
	
}
