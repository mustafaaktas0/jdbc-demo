import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    Student student1 = new Student(1,"Student-1",4.0);
    Student student2 = new Student(2,"Student-2",4.0);

    try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:default","admin","admin")){

        String ddlSql = "CREATE TABLE IF NOT EXISTS students"
                + "(id int PRIMARY KEY AUTO_INCREMENT, name varchar(30),"
                + " grade double)";

        try (Statement statement = connection.createStatement()){
           int createdTableCount = statement.executeUpdate(ddlSql);

           System.out.println("Table is created !");

           String insertSql = "INSERT INTO students(name, grade)"
                    + " VALUES(?,?)";

           try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)){

               preparedStatement.setString(1,student1.getName());
               preparedStatement.setDouble(2,student1.getGrade());

               System.out.println("Created student number : " +preparedStatement.executeUpdate());

               preparedStatement.setString(1,student2.getName());
               preparedStatement.setDouble(2,student2.getGrade());
               System.out.println("Created student number : " +preparedStatement.executeUpdate());

           }

           String selectSql = "SELECT * from students";
           ResultSet resultSet = statement.executeQuery(selectSql);
            List<Student> students = new ArrayList<>();
           while (resultSet.next()){
               Student studentDB = new Student(resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3));
               students.add(studentDB);
           }

           students.forEach(System.out::println);
        }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
}