import java.sql.*;

import javax.swing.text.View;

class Room {
    Integer roomID, buildingID, numBeds;
    Boolean hasPrivateBaths, hasKitchen;
}

class Building {
    Integer buildingID;
    String name, address;
    Boolean hasAC, hasDining;
}

class Student {
    Integer studentID;
    String name, wantsAC, wantsDining, wantsKitchen, wantsPrivateBath;
}

class Assignment {
    Integer studentID, buildingID, roomID;
}

public class DormManagement {

    // Public constructors

    public DormManagement() {
    }
    // Project functions


    // 1) Add a student to the Students table
    public void addStudent(Student student, jdbc_db database) {
        System.out.println("Hello from add");
        if (student.studentID == null || student.name.isEmpty() || student.wantsAC == null
                || student.wantsKitchen == null || student.wantsPrivateBath == null) {
            System.out.println("ERROR: nop at student");
        } else {
            String input = student.studentID + ", '" + student.name + "', " + student.wantsAC + ", "
                    + student.wantsDining + ", " + student.wantsKitchen + ", " + student.wantsPrivateBath;
            database.insert("Student (studentID, name, wantsAC, wantsDining, wantsKitchen, wantsPrivateBath)", input);
        }
    }
    
    // 2) Add an assignment to Assignment table. Check that the assignment meets the student's requirements.
    public void addAssignement(Assignment assignment, jdbc_db database) {
        if (assignment.studentID == null || assignment.buildingID == null || assignment.roomID == null) {
            System.out.println("ERROR: nop at assignment");
        } else {
            String input = assignment.studentID + ", " + assignment.buildingID + ", " + assignment.roomID;
            database.insert("Assignment (studentID, buildingID, roomID)", input);
        }
    }

    //building name grab not working will fix later
    // 3) View all the assignments in a building, i.e., which students are in which rooms, sorted by student Name.
    public void viewAssignments(jdbc_db database, int buildingId) throws SQLException {
        StringBuilder assignmentResult = new StringBuilder();

        String buildingName = "Couldn't find building";
        String q1 = "SELECT Building.name FROM Building AS buildingName " +
                    "WHERE Assignment.buildingID = " + buildingId;
        String q2 = "SELECT Assignment.roomID, Student.name FROM Assignment " + 
                    "JOIN Student ON Assignment.studentID = Student.studentID " +
                    "WHERE Assignment.buildingID = " + buildingId +
                    " ORDER BY Student.name, Assignment.roomID";
        try{
            ResultSet resultSet1 = database.statement.executeQuery(q1);
            if(resultSet1.next()){
                buildingName = resultSet1.getString("buildingName");
            }

            if(buildingName.equals("Couldn't find building")){
                System.out.println("ERROR: Coulnd't find building");
                return;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(buildingName);

        try{
            ResultSet resultSet2 = database.statement.executeQuery(q2);
            while(resultSet2.next()){
                int roomID = resultSet2.getInt("roomID");
                String studentName = resultSet2.getString("name");
                assignmentResult.append(roomID).append(" | ").append(studentName).append("\n");
            }

            if(assignmentResult.length() == 0 ){
                assignmentResult.append("ERROR: Coulnd't find any assignments");
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(assignmentResult.toString());
    }

    // 4) View all the rooms, sorted by buildingId. Display how many bedrooms are available per room
    public void viewAvailableRooms() {

    }

    // 5) View all available rooms that meet a student's request, e.g., matches their desire for a private bathroom (or not), kitchen, etc.
    public void viewMatchingRooms(int studentID, jdbc_db database) {
        StringBuilder matchingRooms = new StringBuilder();
        Boolean wantsAC = false, wantsDining = false, wantsKitchen = false, wantsPrivateBath = false;

        String q1 = "SELECT Student.wantsAC, Student.wantsDining, Student.wantsKitchen, Student.wantsPrivateBath " +
                   "FROM Student WHERE Student.studentID = " + studentID;
        try{
            ResultSet resultSet1 = database.statement.executeQuery(q1);
            if(resultSet1.next()){
                wantsAC = resultSet1.getBoolean("wantsAC");
                wantsDining = resultSet1.getBoolean("wantsDining");
                wantsKitchen = resultSet1.getBoolean("wantsKitchen"); 
                wantsPrivateBath = resultSet1.getBoolean("wantsPrivateBath");
            }
            else{
                System.out.println("ERROR: Coulnd't find student");
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }        

        String q2 = "SELECT Building.buildingID, Building.name AS buildingName, Building.hasAC, Building.hasDining, " + 
                    "Room.roomID, Room.hasKitchen, Room.hasPrivateBaths FROM Room " +
                    "JOIN Building ON Room.buildingID = Building.buildingID " + 
                    "WHERE Building.hasAC = " + wantsAC.toString() + " AND Building.hasDining = " + wantsDining.toString() + 
                    " AND Room.hasKitchen = " + wantsKitchen.toString() + " AND Room.hasPrivateBaths = " + wantsPrivateBath.toString();
        //System.out.println(q2);
        try{
            ResultSet resultSet2 = database.statement.executeQuery(q2);
            while(resultSet2.next()){
                
                int buildingID = resultSet2.getInt("buildingID");
                String buildingName = resultSet2.getString("buildingName");
                boolean buildingHasAC  = resultSet2.getBoolean("hasAC");
                boolean buildingHasDining = resultSet2.getBoolean("hasDining");
                int roomID = resultSet2.getInt("roomID");
                boolean roomHasKitchen = resultSet2.getBoolean("hasKitchen");
                boolean roomHasPrivateBaths = resultSet2.getBoolean("hasPrivateBaths");
                matchingRooms.append(buildingID).append(" | ")
                             .append(buildingName).append(" | ")
                             .append(buildingHasAC).append(" | ")
                             .append(buildingHasDining).append(" | ")  
                             .append(roomID).append(" | ")
                             .append(roomHasKitchen).append(" | ")
                             .append(roomHasPrivateBaths).append("\n");

            }

            if(matchingRooms.length() == 0 ){
                matchingRooms.append("ERROR: Coulnd't find any rooms");
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(matchingRooms.toString());
    }

    // 6) View all students that could room with a given student (i.e., have the same requests)
    public void viewMatchingStudents() {
 
    }

    // 7) View a report that lists, for each building, the number of total bedrooms, 
    //    the number of total rooms, the number of rooms with some availability left, 
    //    and the number of total bedrooms with some availability. At the bottom, 
    //    create summary of number of total bedrooms available on campus.
    public void viewAllBuildings() {

    }



    public static void main(String[] args) throws SQLException {

        String username = "lal013";
        String password = "ooveiz0M";
        // String username = "seh051";
        // String password = "Eiza0eiv";
        DormManagement dormManager = new DormManagement();

        jdbc_db database = new jdbc_db();
        database.connect(username, password);
        database.initDatabase();
        //System.out.println("hey man");

        Room room = new Room();
        Building building = new Building();
        Student student = new Student();
        Assignment assignment = new Assignment();

        String actionPage = args[0];

        if (actionPage.equals("addStudent")) {
            if (args.length != 7) {
                System.out.println("ERROR: Invalid Usage: java DormManagement <id> <name> <wantsAC> <wantsDining> <wantsKitchen> <wantsPrivateBath>");
                return;
            } else {
                try {
                    student.studentID = Integer.parseInt(args[1]);
                    student.name = args[2];
                    student.wantsAC = args[3].toUpperCase();
                    student.wantsDining = args[4].toUpperCase();
                    student.wantsKitchen = args[5].toUpperCase();
                    student.wantsPrivateBath = args[6].toUpperCase();
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Invalid string format. Cannot convert to integer.");
                }

                //Checking to see if student exists
                if(database.studentExists(student.studentID)){
                    System.out.println("ERROR: Student already found with <studentID>");
                    return;
                }      
                else
                    dormManager.addStudent(student, database);
            }
        } 
        else if (actionPage.equals("addAssignment")) {
            if (args.length != 4) {
                System.out.println("ERROR: Invalid Usage: java DormManagement <studentID> <buildingID> <roomID>");
                return;
            } else {
                try {
                    assignment.studentID = Integer.parseInt(args[1]);
                    assignment.buildingID = Integer.parseInt(args[2]);
                    assignment.roomID = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Invalid string format. Cannot convert to integer.");
                }
                if (!database.studentExists(assignment.studentID) || !database.buildingExists(assignment.buildingID) || !database.roomExists(assignment.roomID)){
                    System.out.println("ERROR: Invalid studentID, buildingID, or roomID. Please check the values exists.");
                } 
                //if(!database.roomsFull(roomID)) and need to check to see if room and building match
                else {
                    database.insert("Assignment (studentID, buildingID, roomID)", 
                        assignment.studentID + ", " + assignment.buildingID + ", " + assignment.roomID);
                }
            }

        } 
        else if (actionPage.equals("viewAssignments")) {
            if (args.length != 2) {
                System.out.println("ERROR: Invalid Usage: java DormManagement <buildingID>");
                return;
            } 
            else {
                int buildingId;
                try {
                    buildingId = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Invalid string format. Cannot convert to integer.");
                    return;
                }

                if(!database.buildingExists(buildingId)) {
                    System.out.println("ERROR: Invalid buildingID. Please check the value exists.");
                    return;
                } 
                else {
                    dormManager.viewAssignments(database, buildingId);
                }
            }
        } 
        else if (actionPage.equals("viewMatchingRooms")) {
            if (args.length != 2) {
                System.out.println("ERROR: Invalid Usage: java DormManagement <buildingID>");
                return;
            } 
            else {
                int studentID;
                try {
                    studentID = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Invalid string format. Cannot convert to integer.");
                    return;
                }

                if(!database.studentExists(studentID)) {
                    System.out.println("ERROR: Invalid studentID. Please check the value exists.");
                    return;
                } 
                else {
                    dormManager.viewMatchingRooms(studentID, database);
                }
            }
        }
        else if (actionPage.equals("viewMatchingStudents")) {

        } 
        else if (actionPage.equals("viewAllBuildings")) {

        }

        database.disConnect();

    }

}
