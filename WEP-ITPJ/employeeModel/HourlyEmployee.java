package employeeModel;

public class HourlyEmployee extends Employee {
	
	private double hourlyRate;
	private double hoursWorked;
	private double weeklyEarnings;
	
	public HourlyEmployee(String[] name, String[] address, int[] date, double[] rateAndHours) {
		super(name, address, date);
		setHourlyRate(rateAndHours[0]);
		setHoursWorked(rateAndHours[1]);
		setWeeklyEarnings();
	}
	
	// returns salary information, including hourly rate, hours worked per week, and weekly earnings
	public double[] getSalaryInfo() {
		double[] salaryInfo = {getWeeklyEarnings(), hoursWorked, hourlyRate, weeklyEarnings};
		return salaryInfo;
	}
	
	// returns a calculation of the employee's yearly earnings, assuming a 51-week calendar year
	public double calcYearlyEarnings() {
		return weeklyEarnings * 51;
	}
	
	private void setHourlyRate(double rate) {
		this.hourlyRate = rate;
	}
	
	public double getHourlyRate() {
		return hourlyRate;
	}
	
	private void setHoursWorked(double hours) {
		this.hoursWorked = hours;
	}
	
	public double getHoursWorked() {
		return hoursWorked;
	}
	
	// sets earnings for a given week based on hours work - hours over 40 are paid at 1.5 time
	private void setWeeklyEarnings() {
		if (hoursWorked >= 40) {
			weeklyEarnings = hoursWorked * hourlyRate;
		} else {
			double overTime = hoursWorked - 40;
			weeklyEarnings = (hourlyRate * 40) + (1.5 * hourlyRate * overTime);
		}
	}
	
	public double getWeeklyEarnings() {
		return weeklyEarnings;
	}
	
	
}
