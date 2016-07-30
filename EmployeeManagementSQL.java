import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EmployeeManagementSQL {

	static void display(ResultSet rs)
	{
		System.out.println("\n--------------Employee List---------------\n");
		System.out.println(String.format("%-10s%-15s%-10s%-20s%-10s", "ID","Name","salary","contact-no","Email-Id"));
		try {
			while(rs.next())
			{
				System.out.println(String.format("%-5s%-20s%-10s%-15s%-10s",rs.getObject(1).toString(),
						rs.getObject(2).toString(),rs.getObject(3).toString(),rs.getObject(4).toString(),rs.getObject(5).toString()));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void show(ResultSet rs)
	{
		try {
				System.out.println("\nEmployee Details :" + 
			"\nID: " + rs.getObject(1).toString() + "\nName: " + rs.getObject(2).toString() + "\nSalary: " + 
		rs.getObject(3).toString() + "\nContact No: " + rs.getObject(4).toString() + "\nEmail-id: " + rs.getObject(5).toString() +" ");
			
			} catch (SQLException e) {
			
			e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		
		Connection connection = null;
		Statement stmt = null;
		PreparedStatement prestatement = null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		int id = 0;
		String name = null;
		float salary = 0;
		long contact_no = 0;
		String email_id = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee_Management","root","09tf1a0219");
			//connection.prepareStatement("create table employee(id int,name varchar(25),salary float(7,2),contact_no bigint(11),email_id varchar(25)) if not exists");
			stmt = connection.createStatement();
			stmt.executeUpdate("create table if not exists employee(id int not null,name varchar(25) not null,salary float(7,2),contact_no bigint(11) not null,email_id varchar(25) not null)");
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		do
		{
			System.out.println("\n*********Welcome to the Employee Management System**********\n");
			System.out.println("1). Add Employee to the DataBase\n" +
								"2). Search for Employee\n" +
								"3). Edit Employee details\n" +
								"4). Delete Employee Details\n" +
								"5). Display all Employees working in this company\n" +
								"6). EXIT\n");
			System.out.println("Enter your choice : ");
			int ch = sc.nextInt();
			
			switch(ch)
			{
			case 1:	try {
					prestatement = connection.prepareStatement("insert into employee values(?,?,?,?,?)");
					} catch (SQLException e1) {
					
						e1.printStackTrace();
					}
					System.out.println("\nEnter the following details to ADD list:\n");
					System.out.println("Enter ID :");
					id = sc.nextInt();
					System.out.println("Enter Name :");
					name = sc.next();
					System.out.println("Enter Salary :");
					salary = sc.nextFloat();
					System.out.println("Enter Contact No :");
					contact_no = sc.nextLong();
					System.out.println("Enter Email-ID :");
					email_id = sc.next();
					
				try {
					prestatement.setInt(1, id);
					prestatement.setString(2, name);
					prestatement.setFloat(3, salary);
					prestatement.setLong(4, contact_no);
					prestatement.setString(5, email_id);
					prestatement.executeUpdate();
					rs = stmt.executeQuery("select * from employee");
					display(rs);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
					break;
					
			case 2: System.out.println("Enter the Employee ID to search :");
					id = sc.nextInt();
					int i=0;
				try {
					rs = stmt.executeQuery("select * from employee");
					while(rs.next())
					{
						if(id == rs.getInt(1))
						{
							show(rs);
							i++;
						}
					}
					} catch (SQLException e) {
					
					e.printStackTrace();
					}
					
					if(i == 0)
					{
						System.out.println("\nEmployee Details are not available, Please enter a valid ID!!");
					}
					
					break;
					
			case 3: System.out.println("\nEnter the Employee ID to EDIT the details");
					id = sc.nextInt();
					int j=0;
					try {
						rs = stmt.executeQuery("select * from employee");
						while(rs.next())
						{
							if(id == rs.getInt(1))
							{
								j++;
							do{
								int ch1 =0;
								System.out.println("\nEDIT Employee Details :\n" +
													"1). Employee ID\n" +
													"2). Name\n" +
													"3). Salary\n" +
													"4). Contact No.\n" +
													"5). Email-ID\n" +
													"6). GO BACK\n");
								System.out.println("Enter your choice : ");
								ch1 = sc.nextInt();
								switch(ch1)
								{
								case 1: System.out.println("\nEnter new Employee ID:");
								 		int new_id = sc.nextInt();
										stmt.executeUpdate("update employee set id = "+new_id+" where id = "+id);
								 		rs = stmt.executeQuery("select * from employee where id = "+new_id);
										show(rs);
										break;
								
								case 2: System.out.println("Enter new Employee Name:");
										String new_name = sc.next();
										stmt.executeUpdate("update employee set name = '"+new_name+"' where id = "+id);
										//show(rs);
										break;
										
								case 3: System.out.println("Enter new Employee Salary:");
										float new_salary = sc.nextFloat();
										stmt.executeUpdate("update employee set salary = "+new_salary+" where id = "+id);
										//show(rs);
										break;
										
								case 4: System.out.println("Enter new Employee Contact No. :");
										long new_contact = sc.nextLong();
										stmt.executeUpdate("update employee set contact_no = "+new_contact+" where id = "+id);
										//show(rs);
										break;
										
								case 5: System.out.println("Enter new Employee Email-ID :");
										String new_email = sc.next();
										stmt.executeUpdate("update employee set email_id = '"+new_email+"' where id = "+id);
										//show(rs);
										break;
										
								case 6: j++;
										break;
										
								default : System.out.println("\nEnter a correct choice from the List :");
											break;
								
								}
								}
							while(j==0);
							}
						}
					} catch (SQLException e1) {
					
						e1.printStackTrace();
					}
					if(j == 0)
					{
						System.out.println("\nEmployee Details are not available, Please enter a valid ID!!");
					}
					
					break;
					
			case 4: System.out.println("\nEnter Employee ID to DELETE from the Databse :");
					id = sc.nextInt();
					int k=0;
				try {
					rs = stmt.executeQuery("select * from employee");
					while(rs.next())
					{
						if(id == rs.getInt(1))
						{
							stmt.executeUpdate("delete from employee where id = "+id);
							System.out.println("Deleted from the DATABASE successfully!!\n\n");
							//display(rs);
							k++;
						}
					}
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				if(k == 0)
				{
					System.out.println("\nEmployee Details are not available, Please enter a valid ID!!");
				}
					break;
					
			case 5: try {
						rs = stmt.executeQuery("select * from employee");
						display(rs);
					} catch (SQLException e) {
					
						e.printStackTrace();
					}
					
					break;
					
			case 6: sc.close();
					System.exit(0);
					
			default: System.out.println("\nEnter a correct choice from the List");
			 			break;
			}
		}
		while(true);
	}

}
