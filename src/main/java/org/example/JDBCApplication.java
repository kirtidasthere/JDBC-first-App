package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCApplication
{
    public static void main(String[] args) {

        String url="jdbc:mysql://localhost:3306/spark";
        String username="root";
        String password="W7301@jqirk";

        try {
            //load and Register Driver
            // Class.forName("com.mysql.cj.jdbc.Driver");
           Connection connection= DriverManager.getConnection(url,username,password);
             Statement statement= connection.createStatement();
             //execute ----> boolean
            //executeQuery -----> Resultset return karega
            //executeUpdate -----> update karega
             //ResultSet resultSet= statement.executeQuery("select * from students");

            /*System.out.println("****************STUDENTS DETAILS****************");
             while (resultSet.next())
             {
                 System.out.println("Student id :"+resultSet.getInt("st_id"));
                 System.out.println("Student name :"+resultSet.getString("stname"));
                 System.out.println("Student email :"+resultSet.getString("email"));
                 System.out.println("Student phoneNo :"+resultSet.getString("phoneNo"));
                 System.out.println("----------------------------------------------------");
             }
*/
             //1.user input ------> fetch data
             //2.user input ------> insert data
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter 1 for fetch data \nEnter 2 for insert data \nEnter 3 for batch update \nEnter 4 for delete data ");

            int choice= sc.nextInt();
            switch (choice)
            {
                //fetching data from database
                case 1:
                    ResultSet fetchData= statement.executeQuery("select * from students");
                    Operations.fetchData(fetchData);//because we don't want to create object
                    break;

                //inserting data into database
                case 2:
                    ResultSet MaxIdResultset= statement.executeQuery("select max(st_id) as max_st_id from students");
                   // boolean next= MaxIdResultset.next();
                    int max_id=0;
                    while (MaxIdResultset.next())
                    {
                        max_id=MaxIdResultset.getInt("max_st_id");
                        System.out.println("Max Student id :"+max_id);

                    }
                    max_id++;
                    System.out.println("Enter the name:");
                    String name=sc.next();
                    System.out.println("Enter the email:");
                    String email=sc.next();
                    System.out.println("Enter the phoneNo:");
                    String phoneNo=sc.next();

                   int rowCount= statement.executeUpdate("insert into students values("+max_id+",'"+name+"','"+email+"','"+phoneNo+"')");
                   if (rowCount>0)
                   {
                       System.out.println("Data Inserted:");
                   }
                   else {
                       System.out.println("Data insertion Failed:");
                   }
                   break;

                case 3:
                    System.out.println("Enter the details for batch:");
                    String [] bulkQueries= new String[10];

                    statement.addBatch("INSERT INTO students (st_id, stname) VALUES (1, 'John Doe')");
                    statement.executeBatch();
                    break;

                case 4:
                    System.out.println("Enter the id for delete record:");
                    int id=sc.nextInt();
                    int row= statement.executeUpdate("DELETE from students where st_id = "+id);
                    if (row>0)
                    {
                        System.out.println("Data deleted:"+id);
                    }
                    else {
                        System.out.println("Data deletion Failed:");
                    }
                    break;

                default:
                    System.out.println("Enter valid input");
                    break;

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
