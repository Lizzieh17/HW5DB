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
            System.out.println("nop at student");
        } else {
            // booleans might be messing up
            String input = student.studentID + ", '" + student.name + "', " + student.wantsAC + ", "
                    + student.wantsDining + ", " + student.wantsKitchen + ", " + student.wantsPrivateBath;
            System.out.println("input at add student: " + input);
            database.insert("Student (studentID, name, wantsAC, wantsDining, wantsKitchen, wantsPrivateBath)", input);
        }
    }

    public void addAssignement(Assignment assignment, jdbc_db database) {
        if (assignment.studentID == null || assignment.buildingID == null || assignment.roomID == null) {
            System.out.println("nop");
        } else {
            String input = assignment.studentID + ", '" + assignment.buildingID + "', '" + assignment.roomID + "')";
            database.insert("Assignment", input);
        }
    }

    public void viewAssignments() {

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
        System.out.println("hey man");

        Room room = new Room();
        Building building = new Building();
        Student student = new Student();
        Assignment assignment = new Assignment();

        String actionPage = args[0];

        if (actionPage.equals("addStudent")) {
            if (args.length != 7) {
                System.err.println(
                        "Usage: java DormManagement <id> <name> <wantsAC> <wantsDining> <wantsKitchen> <wantsPrivateBath>");
                return;
            } else {
                try {
                    student.studentID = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid string format. Cannot convert to integer.");
                }
                student.name = args[2];
                student.wantsAC = args[3].toUpperCase();
                student.wantsDining = args[4].toUpperCase();
                student.wantsKitchen = args[5].toUpperCase();
                student.wantsPrivateBath = args[6].toUpperCase();
            }
            System.out.println(student.name + ", " + student.studentID + ", " + student.wantsAC + ", "
                    + student.wantsDining + ", " + student.wantsKitchen + ", " + student.wantsPrivateBath);
            dormManager.addStudent(student, database);
        } else if (actionPage.equals("addAssignmet")) {

        } else if (actionPage.equals("viewAssignments")) {

        } else if (actionPage.equals("viewMatchingStudents")) {

        } else if (actionPage.equals("viewAllBuildings")) {

        }

        database.disConnect();

    }

}
