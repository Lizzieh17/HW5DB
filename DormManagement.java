import java.sql.*;

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

    public void addStudent(Student student, jdbc_db database) {
        if (student.studentID == null || student.name.isEmpty() || student.wantsAC == null
                || student.wantsKitchen == null || student.wantsPrivateBath == null) {
            System.out.println("ERROR: nop at student");
        } else {
            String input = student.studentID + ", '" + student.name + "', " + student.wantsAC + ", "
                    + student.wantsDining + ", " + student.wantsKitchen + ", " + student.wantsPrivateBath;
            database.insert("Student (studentID, name, wantsAC, wantsDining, wantsKitchen, wantsPrivateBath)", input);
        }
    }

    public void addAssignement(Assignment assignment, jdbc_db database) {
        if (assignment.studentID == null || assignment.buildingID == null || assignment.roomID == null) {
            System.out.println("ERROR: nop at assignment");
        } else {
            String input = assignment.studentID + ", " + assignment.buildingID + ", " + assignment.roomID;
            database.insert("Assignment (studentID, buildingID, roomID)", input);
        }
    }

    public void viewAssignments(jdbc_db database, int buildingId) throws SQLException {
        System.out.println(database.query("SELECT * FROM Assignment WHERE buildingID = " + buildingId + ";"));
    }

    public void viewAvailableRooms() {

    }

    public void viewMatchingStudents() {

    }

    public void viewAllBuildings() {

    }



    public static void main(String[] args) throws SQLException {

        // String username = "lal013";
        // String password = "ooveiz0M";
        String username = "seh051";
        String password = "Eiza0eiv";
        DormManagement dormManager = new DormManagement();

        jdbc_db database = new jdbc_db();
        database.connect(username, password);
        database.initDatabase();
        // System.out.println("hey man");

        Room room = new Room();
        Building building = new Building();
        Student student = new Student();
        Assignment assignment = new Assignment();

        String actionPage = args[0];

        if (actionPage.equals("addStudent")) {
            if (args.length != 7 || database.studentExists(student.studentID)) {
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
                    dormManager.addStudent(student, database);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Invalid string format. Cannot convert to integer.");
                }
            }
        } else if (actionPage.equals("addAssignment")) {
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
                } else {
                    database.insert("Assignment (studentID, buildingID, roomID)", 
                        assignment.studentID + ", " + assignment.buildingID + ", " + assignment.roomID);
                }
            }

        } else if (actionPage.equals("viewAssignments")) {
            if (args.length != 2) {
                System.out.println("ERROR: Invalid Usage: java DormManagement <buildingID>");
                return;
            } else {
                int buildingId;
                try {
                    buildingId = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Invalid string format. Cannot convert to integer.");
                    return;
                }
                if (!database.buildingExists(buildingId)) {
                    System.out.println("ERROR: Invalid buildingID. Please check the value exists.");
                    return;
                } else {
                    dormManager.viewAssignments(database, buildingId);
                }
            }
        } else if (actionPage.equals("viewMatchingStudents")) {

        } else if (actionPage.equals("viewAllBuildings")) {

        }

        database.disConnect();

    }

}
