<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="styles.css">
    <title>Home</title>
    <script>
        function toggleForm(id) {
            const form = document.getElementById(id);
            if (form.style.display === "none" || form.style.display === "") {
                form.style.display = "block";
            } else {
                form.style.display = "none";
            }
        }
    </script>
</head>

<body>
    <h1>DB Project</h1>
    <ol>
        <div class="block-container">
            <li>Add a student</li>
            <button id="addStudentPage" class="op-button">Go</button>
            <script type="text/javascript">
                document.getElementById("addStudentPage").onclick = function () { location.href = "add_student.php"; };
            </script>
        </div>

        <div class="block-container">
            <li>Add an Assignment</li>
            <button id="addAssignmentPage" class="op-button">Go</button>
            <script type="text/javascript">
                document.getElementById("addAssignmentPage").onclick = function () { location.href = "add_assignment.php"; };
            </script>
        </div>

        <div class="block-container">
            <li>View all the assignments in a building</li>
            <button id="viewAssignments" class="op-button">Go</button>
            <script type="text/javascript">
                document.getElementById("viewAssignments").onclick = function () { location.href = "view_assignments.php"; };
            </script>
        </div>

        <div class="block-container">
            <li>View all the rooms</li>
            <button id="viewRooms" class="op-button">Go</button>
            <script type="text/javascript">
                document.getElementById("viewRooms").onclick = function () { location.href = "view_rooms.php"; };
            </script>
        </div>

        <div class="block-container">
            <li>View all available rooms</li>
            <button id="viewAvailableRooms" class="op-button">Go</button>
            <script type="text/javascript">
                document.getElementById("viewAvailableRooms").onclick = function () { location.href = "view_available_rooms.php"; };
            </script>
        </div>

        <div class="block-container">
            <li>View all students that could room with a given student</li>
            <button id="viewMatchingStudents" class="op-button">Go</button>
            <script type="text/javascript">
                document.getElementById("viewMatchingStudents").onclick = function () { location.href = "matching_students.php"; };
            </script>
        </div>

        <div class="block-container">
            <li>View a report</li>
            <button id="viewReport" class="op-button">Go</button>
            <script type="text/javascript">
                document.getElementById("viewReport").onclick = function () { location.href = "view_report.php"; };
            </script>
        </div>
    </ol>
</body>

</html>