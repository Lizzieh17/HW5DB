import java.sql.*;

class Room{
    Integer roomID, buildingID, numBeds;
    Boolean hasPrivateBaths, hasKitchen;
}

class Building{
    Integer buildingID;
    String name, address;
    Boolean hasAC, hasDining;
}

class Student{
    Integer studentID; 
    String name;
    Boolean wantsAC, wantsDining, wantsKitchen, wantsPrivateBath;
}

class Assignment{
    Integer studentID, buildingID, roomID;    
}

public class DormManagement {

    //Public constructors 

    public DormManagement(){
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

    public void addStudent(Student student, jdbc_db database){
        if(student.studentID == null || student.name.isEmpty() || student.wantsAC == null || student.wantsKitchen == null || student.wantsPrivateBath == null){
           System.out.println("nop");
        }
        else{
            //booleans might be messing up
            String input = student.studentID + ", '" + student.name + "', " + student.wantsAC + ", " + student.wantsDining + ", " + student.wantsKitchen + ", " +  student.wantsPrivateBath +  ")";
            database.insert("Student", input);
        }
    }
    
    public void addAssignement(Assignment assignment, jdbc_db database){
        if(assignment.studentID == null || assignment.buildingID == null || assignment.roomID == null){
            System.out.println("nop");
         }
         else{
             String input = assignment.studentID + ", '" + assignment.buildingID + "', '" + assignment.roomID  +  "')";
             database.insert("Assignment", input);
         }
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

        String username = "lal013";
        String password = "ooveiz0M";
        DormManagement dormManager = new DormManagement();

        jdbc_db database = new jdbc_db();
        database.connect(username, password);
        database.initDatabase();
   

        Room room = new Room();
        Building building = new Building();
        Student student = new Student();
        Assignment assignment = new Assignment();
        

        String actionPage = args[0]; 

        if(actionPage.equals("addStudent")){
            if (args.length != 7) {
                System.err.println("Usage: java DormManagement <id> <name> <wantsAC> <wantsDining> <wantsKitchen> <wantsPrivateBath>");
                return;
            }
            else{
                try {
                    student.studentID = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid string format. Cannot convert to integer.");
                }
                student.name = args[2];
                student.wantsAC =  Boolean.parseBoolean(args[3]);
                student.wantsDining = Boolean.parseBoolean(args[4]);
                student.wantsKitchen = Boolean.parseBoolean(args[5]);
                student.wantsPrivateBath = Boolean.parseBoolean(args[6]);
            }
            System.out.println(student.name + student.studentID + student.wantsAC + student.wantsDining + student.wantsKitchen + student.wantsPrivateBath);
            dormManager.addStudent(student, database);
        }
        else if(actionPage.equals("addAssignmet")){

        }
        else if(actionPage.equals("viewAssignments")){

        }
        else if(actionPage.equals("viewMatchingStudents")){

        }
        else if(actionPage.equals("viewAllBuildings")){

        }

        database.disConnect();

    }


}
