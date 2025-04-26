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

    // Project functions

    // 1) Add a student to the Students table
    public void addStudent(Student student, jdbc_db database) {
        if (student.studentID == null || student.name.isEmpty() || student.wantsAC == null
                || student.wantsKitchen == null || student.wantsPrivateBath == null) {
            System.out.println("ERROR: nop at student");
        }
        else {
            String input = student.studentID + ", '" + student.name + "', " + student.wantsAC + ", "
                    + student.wantsDining + ", " + student.wantsKitchen + ", " + student.wantsPrivateBath;
            database.insert("Student (studentID, name, wantsAC, wantsDining, wantsKitchen, wantsPrivateBath)", input);
        }
    }
    
    // 2) Add an assignment to Assignment table. Check that the assignment meets the student's requirements.
    public void addAssignement(Assignment assignment, jdbc_db database) {
        if (assignment.studentID == null || assignment.buildingID == null || assignment.roomID == null) {
            System.out.println("ERROR: nop at assignment");
        } 
        else if(!database.roomIsAvailable(assignment.roomID, assignment.buildingID)){
            System.out.println("ERROR: Room not available");
        }
        else {
            String input = assignment.studentID + ", " + assignment.buildingID + ", " + assignment.roomID;
            database.insert("Assignment (studentID, buildingID, roomID)", input);
        }
    }

    //building name grab not working will fix later
    // 3) View all the assignments in a building, i.e., which students are in which rooms, sorted by student Name.
    public void viewAssignments(jdbc_db database, int buildingId) throws SQLException {
        StringBuilder assignmentResult = new StringBuilder();
    
        String buildingName = "Couldn't find building";
        String q1 = "SELECT B.name AS buildingName " +
                    "FROM Assignment A " +
                    "JOIN Building B ON A.buildingID = B.buildingID " +
                    "WHERE A.buildingID = " + buildingId + " LIMIT 1";
    
        String q2 = "SELECT A.roomID, S.name " +
                    "FROM Assignment A " +
                    "JOIN Student S ON A.studentID = S.studentID " +
                    "WHERE A.buildingID = " + buildingId + 
                    " ORDER BY S.name, A.roomID";
    
        try {
            ResultSet resultSet1 = database.statement.executeQuery(q1);
            if (resultSet1.next()) {
                buildingName = resultSet1.getString("buildingName");
            }
    
            if (buildingName.equals("Couldn't find building")) {
                System.out.println("ERROR: Couldn't find building");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    
        System.out.println(buildingName);
    
        try {
            ResultSet resultSet2 = database.statement.executeQuery(q2);
            while (resultSet2.next()) {
                int roomID = resultSet2.getInt("roomID");
                String studentName = resultSet2.getString("name");
                assignmentResult.append(roomID).append(" | ").append(studentName).append("\n");
            }
    
            if (assignmentResult.length() == 0) {
                assignmentResult.append("ERROR: Couldn't find any assignments");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        System.out.println(assignmentResult.toString());
    }
    
    // 4) View all the rooms, sorted by buildingId. Display how many bedrooms are available per room
    public void viewAllRooms(jdbc_db database) {
        StringBuilder roomList = new StringBuilder();
        String q = "SELECT Room.buildingID, Room.roomID, Room.numBeds, (Room.numBeds - COUNT(Assignment.studentID)) AS availableBeds, COUNT(Assignment.studentID) AS occupiedBeds " +
               "FROM Room LEFT JOIN Assignment ON Room.roomID = Assignment.roomID " +
               "GROUP BY Room.roomID, Room.buildingID, Room.numBeds " +
               "ORDER BY Room.buildingID, Room.roomID";

        try{
            ResultSet resultSet = database.statement.executeQuery(q);
            if (!resultSet.isBeforeFirst()) {
                roomList.append("No rooms found in the database");
            } 
            else {
                while (resultSet.next()) {
                    int buildingID = resultSet.getInt("buildingID");
                    int roomID = resultSet.getInt("roomID");
                    int numBeds = resultSet.getInt("numBeds");
                    int availableBeds = resultSet.getInt("availableBeds");
                    int occupiedBeds = resultSet.getInt("occupiedBeds");
                    
                    roomList.append(buildingID).append(" | ")
                           .append(roomID).append(" | ")
                           .append(numBeds).append(" | ")
                           .append(availableBeds).append(" | ")
                           .append(occupiedBeds).append("\n");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(roomList.toString());
        
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
    public void viewMatchingStudents(int givenStudentID, jdbc_db database) {
        StringBuilder matchingStudents = new StringBuilder();
        Boolean givenWantsAC = false, givenWantsDining = false, givenWantsKitchen = false, givenWantsPrivateBath = false;

        String q1 = "SELECT Student.wantsAC, Student.wantsDining, Student.wantsKitchen, Student.wantsPrivateBath " +
                   "FROM Student WHERE Student.studentID = " + givenStudentID;
        try{
            ResultSet resultSet1 = database.statement.executeQuery(q1);
            if(resultSet1.next()){
                givenWantsAC = resultSet1.getBoolean("wantsAC");
                givenWantsDining = resultSet1.getBoolean("wantsDining");
                givenWantsKitchen = resultSet1.getBoolean("wantsKitchen"); 
                givenWantsPrivateBath = resultSet1.getBoolean("wantsPrivateBath");
            }
            else{
                System.out.println("ERROR: Coulnd't find student");
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }       
        
        String q2 = "SELECT Student.studentID, Student.name, Student.wantsAC, Student.wantsDining, Student.wantsKitchen, Student.wantsPrivateBath " +
                   "FROM Student WHERE Student.wantsAC = " + givenWantsAC.toString() + " AND Student.wantsDining = " + givenWantsDining.toString() + 
                   " AND  Student.wantsKitchen = " + givenWantsKitchen.toString() + " AND  Student.wantsPrivateBath = " + givenWantsPrivateBath.toString();
        try{
            ResultSet resultSet2 = database.statement.executeQuery(q2);
            while(resultSet2.next()){
                int studentID = resultSet2.getInt("studentID");
                String studentName = resultSet2.getString("name");
                boolean wantsAC  = resultSet2.getBoolean("wantsAC");
                boolean wantsDining = resultSet2.getBoolean("wantsDining");
                boolean wantsKitchen = resultSet2.getBoolean("wantsKitchen");
                boolean wantsPrivateBath = resultSet2.getBoolean("wantsPrivateBath");
                matchingStudents.append(studentID).append(" | ")
                                .append(studentName).append(" | ")
                                .append(wantsAC).append(" | ")
                                .append(wantsDining).append(" | ")  
                                .append(wantsKitchen).append(" | ")
                                .append(wantsPrivateBath).append("\n");
            }

            if(matchingStudents.length() == 0 ){
                matchingStudents.append("ERROR: Coulnd't find any students");
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(matchingStudents.toString());
    }

    public void viewAllBuildings(jdbc_db database) {
        StringBuilder buildingList = new StringBuilder();
        String query = "SELECT * FROM Building ORDER BY buildingID";
        try {
            ResultSet resultSet = database.statement.executeQuery(query);
            while (resultSet.next()) {
                int buildingID = resultSet.getInt("buildingID");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                boolean hasAC = resultSet.getBoolean("hasAC");
                boolean hasDining = resultSet.getBoolean("hasDining");

                buildingList.append(buildingID).append(" | ")
                            .append(name).append(" | ")
                            .append(address).append(" | ")
                            .append(hasAC).append(" | ")
                            .append(hasDining).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(buildingList.toString());
    }

    public void viewReport(jdbc_db database) throws SQLException {
        System.out.println("Report: ");
        System.out.println(database.printReport());
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
                System.out.println("ERROR: Please enter all values");
                return;
            } 
            else {
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
                } else if (student.studentID < 1000){
                    System.out.println("ERROR: Invalid studentID. Please follow the example.");
                    return;
                } else{
                    dormManager.addStudent(student, database);
                }
            }
        } 
        else if (actionPage.equals("addAssignment")) {
            if (args.length != 4) {
                System.out.println("ERROR: Please enter all values");
                return;
            } 
            else {
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
                    dormManager.addAssignement(assignment, database);
                }
            }

        } 
        else if (actionPage.equals("viewAssignments")) {
            if (args.length != 2) {
                System.out.println("ERROR: Please enter all values");
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
                    dormManager.viewMatchingStudents(studentID, database);
                }
            }
        } 
        else if (actionPage.equals("viewAllRooms")) {
            dormManager.viewAllRooms(database);
        } 
        else if (actionPage.equals("viewReport")) {
            dormManager.viewReport(database);
        }
        database.disConnect();

    }

}
