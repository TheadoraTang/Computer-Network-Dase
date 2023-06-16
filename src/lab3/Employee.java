package lab3;
public class Employee{
    private String name;
    private int month;
    public Employee(String name, int month){
        this.name = name;
        this.month = month;
    }
    public float get_Salary(int month){
        if(month == this.month){
            System.out.println("Happy Birthday to "+this.name+ "!");return 100;
        }
        else{
            return 0;
        }
    }
    public static void main(String[] args) {
        Employee a[] = new Employee[3];
        a[0] = new SalariedEmployee("张三", 5, 1000);
        a[1] = new HourlyEmployee("李四", 3, 2000, 200);
        a[2] = new SalesEmployee("王五", 2, 50000, (float) 0.1);System.out.println(a[0].name+ "的工资为: " + a[0].get_Salary(2));System.out.println(a[1].name+ "的工资为: " + a[1].get_Salary(2));System.out.println(a[2].name+ "的工资为: " + a[2].get_Salary(2));}
}
class SalariedEmployee extends Employee{
    private float salary;
    public SalariedEmployee(String name, int month, float salary){
        super(name, month);
        this.salary = salary;
    }
    @Override
    public float get_Salary(int month)
    {
        return salary + super.get_Salary(month);
    }
}
class HourlyEmployee extends Employee
{
    private float salary; //每小时工资
    private int hour; //每月工作的小时数
    public HourlyEmployee(String name, int month, float salary,int hour){
        super(name, month);
        this.salary = salary;
        this.hour = hour;
    }
    @Override
    public float get_Salary(int month)
    {
        return salary;
    }
}
class SalesEmployee extends Employee
{
    private float sale; //月销售额
    private float bonus; //提成率
    public SalesEmployee(String name, int month, float sale, float bonus){
        super(name, month);
        this.sale = sale;
        this.bonus = bonus;
    }
    @Override
    public float get_Salary(int month)
    {
        return sale * bonus + super.get_Salary(month);
    }
}