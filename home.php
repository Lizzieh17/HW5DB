<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="styles.css">
    <script>
        function toggleForm(id) {
            const form = document.getElementById(id);
            form.style.display = (form.style.display === "none" || form.style.display === "") ? "block" : "none";
        }
    </script>
</head>

<body>
    <header>
        <h1>Welcome to the Dorm Database</h1>
    </header>

    <main class="container">
        <div class="grid">

            <div class="panel">
                <li>Add a student</li>
                <button id="addStudentPage" class="op-button">Go</button>
                <script>
                    document.getElementById("addStudentPage").onclick = function () {
                        location.href = "add_student.php";
                    };
                </script>
            </div>

            <div class="panel">
                <li>Add an Assignment</li>
                <button id="addAssignmentPage" class="op-button">Go</button>
                <script>
                    document.getElementById("addAssignmentPage").onclick = function () {
                        location.href = "add_assignment.php";
                    };
                </script>
            </div>

            <div class="panel">
                <li>View all the assignments in a building</li>
                <button id="viewAssignments" class="op-button">Go</button>
                <script>
                    document.getElementById("viewAssignments").onclick = function () {
                        location.href = "view_assignments.php";
                    };
                </script>
            </div>

            <div class="panel">
                <li>View all the rooms</li>
                <button id="viewRooms" class="op-button">Go</button>
                <script>
                    document.getElementById("viewRooms").onclick = function () {
                        location.href = "view_rooms.php";
                    };
                </script>
            </div>

            <div class="panel">
                <li>View all available rooms that you want</li>
                <button id="viewMatchingRooms" class="op-button">Go</button>
                <script>
                    document.getElementById("viewMatchingRooms").onclick = function () {
                        location.href = "matching_rooms.php";
                    };
                </script>
            </div>

            <div class="panel">
                <li>View all students that could room with a given student</li>
                <button id="viewMatchingStudents" class="op-button">Go</button>
                <script>
                    document.getElementById("viewMatchingStudents").onclick = function () {
                        location.href = "matching_students.php";
                    };
                </script>
            </div>

            <div class="panel">
                <li>View a report</li>
                <button id="viewReport" class="op-button">Go</button>
                <script>
                    document.getElementById("viewReport").onclick = function () {
                        location.href = "view_report.php";
                    };
                </script>
            </div>

        </div>
    </main>

    <footer>
        <p>Created by Luke Lyons and Lizzie Howell</p>
    </footer>
</body>

</html>
