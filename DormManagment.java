import java.sql.*;
import java.util.Scanner;


class Room{
    Integer roomID, buildingID, numBeds;
    Boolean hasPrivateBaths, hasKitchen;
}

class Building{

}

class Student{
    Integer studentID; 
    String name;
    Boolean wantsAC, wantsDining, wantsKitchen, wantsPrivateBath;
}

class Assignment{

}




public class DormManagment {
    
    private Connection connection;
    private Statement statement;
    private static Scanner scan;

    //Public constructors 
    public DormManagment(){
        connection = null;
        statement = null;
        scan = new Scanner(System.in);
    }


    //General Database funcitons
    public void connect(String Username, String mysqlPassword) throws SQLException {
        try {
            String url = "jdbc:mysql://localhost/" + Username + "?useSSL=false";
            System.out.println(url);
            connection = DriverManager.getConnection(url, Username, mysqlPassword);
        } catch (Exception e) {
            throw e;
        }
    }

    public void disConnect() throws SQLException {
        connection.close();
        statement.close();
    }

    public void query(String q) {
        try {
            ResultSet resultSet = statement.executeQuery(q);
            System.out.println("---------------------------------");
            System.out.println("Query: \n" + q + "\n\nResult: ");
            print(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String q) {
        try {
            int result = statement.executeUpdate(q);
            System.out.println("---------------------------------");
            System.out.println("Query: \n" + q + "\n\nResult: ");
            System.out.println(result + " rows were affected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printHeader(ResultSetMetaData metaData, int numColumns) throws SQLException {
        for (int i = 1; i <= numColumns; i++) {
            if (i > 1)
                System.out.print(",  ");
            System.out.print(metaData.getColumnName(i));
        }
        System.out.println();
    }

    public void printRecords(ResultSet resultSet, int numColumns) throws SQLException {
        String columnValue;
        while (resultSet.next()) {
            for (int i = 1; i <= numColumns; i++) {
                if (i > 1)
                    System.out.print(",  ");
                columnValue = resultSet.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
    }

    public void print(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numColumns = metaData.getColumnCount();

        printHeader(metaData, numColumns);
        printRecords(resultSet, numColumns);
    }

    public void insert(String table, String values) {
        String q = "INSERT into " + table + " values (" + values + ")";
        try {
            statement.executeUpdate(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initDatabase(String Username, String Password) throws SQLException {
        connect(Username, Password);
        // create a statement to hold mysql queries
        statement = connection.createStatement();
    }


    //Project functions
    
    // 1) Add a student to the Students table
    // 2) Add an assignment to Assignment table. Check that the assignment meets the student's requirements.
    // 3) View all the assignments in a building, i.e., which students are in which rooms, sorted by student Name.
    // 4) View all the rooms, sorted by buildingId. Display how many bedrooms are available per room
    // 5) View all available rooms that meet a student's request, e.g., matches their desire for a private bathroom (or not), kitchen, etc.
    // 6) View all students that could room with a given student (i.e., have the same requests)
    // 7) View a report that lists, for each building, the number of total bedrooms, the number of total rooms, 
    //    the number of rooms with some availability left, and the number of total bedrooms with some availability. 
    //    at the bottom, create summary of number of total bedrooms available on campus.
    // 8) BONUS: should require joining multiple tables and extending the schema to more attributes/tables

    public void addStudent(Student student){
        if(student.studentID == null || student.name.isEmpty() || student.wantsAC == null || student.wantsKitchen == null || student.wantsPrivateBath == null){
           
        }
        else{
            String q = "INSERT INTO Student (studentID, name, wantsAC, wantsDining, wantsKitchen, wantsPrivateBath)" +
            "VALUES(" + student.studentID + ", '" + student.name + "', '" + student.wantsAC + "', '" + student.wantsDining + "', '" + student.wantsKitchen + "', '" +  student.wantsPrivateBath +  "')";
            
            update(q);
        }
    }

    public void addAssignement(){

    }

    public void viewAssignments(){

    }

    public void viewAvailableRooms(){

    }

    public void viewMatchingStudents(){

    }

    public void viewAllBuildings(){

    }

    public static void main(String[] args) throws SQLException{

    }


}
