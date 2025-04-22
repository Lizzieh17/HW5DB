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
            <button id="addStudentPage" class="op-button">Go to Add Student Page</button>
            <script type="text/javascript">
                document.getElementById("addStudentPage").onclick = function () { location.href = "add_student.php"; };
            </script>
        </div>

        <div class="block-container">
            <li>Add an assignment</li>
            <div>
                <button onclick="toggleForm('form2')">Go</button>
            </div>
            <div id="form2" class="form-container">
                <form method="post" action="add_assignment.php">
                    Title: <input type="text" name="title"><br>
                    Due Date: <input type="date" name="due_date"><br>
                    <input type="submit" value="Add Assignment">
                </form>
            </div>
        </div>

        <div class="block-container">
            <li>View all the assignments in a building</li>
            <div>
                <button class="op-button" onclick="toggleForm('form3')">Go</button>
            </div>
            <div id="form3" class="form-container">
                <form method="get" action="view_assignments.php">
                    Building Name: <input type="text" name="building"><br>
                    <input type="submit" value="View Assignments">
                </form>
            </div>
        </div>

        <div class="block-container">
            <li>View all the rooms</li>
            <div>
                <button class="op-button" onclick="toggleForm('form4')">Go</button>
            </div>
            <div id="form4" class="form-container">
                <form method="get" action="view_rooms.php">
                    <input type="submit" value="View Rooms">
                </form>
            </div>
        </div>

        <div class="block-container">
            <li>View all available rooms</li>
            <div>
                <button class="op-button" onclick="toggleForm('form5')">Go</button>
            </div>
            <div id="form5" class="form-container">
                <form method="get" action="view_available_rooms.php">
                    <input type="submit" value="View Available Rooms">
                </form>
            </div>
        </div>

        <div class="block-container">
            <li>View all students that could room with a given student</li>
            <div>
                <button class="op-button" onclick="toggleForm('form6')">Go</button>
            </div>
            <div id="form6" class="form-container">
                <form method="get" action="possible_roommates.php">
                    Student ID: <input type="text" name="student_id"><br>
                    <input type="submit" value="Find Roommates">
                </form>
            </div>
        </div>

        <div class="block-container">
            <li>View a report</li>
            <div>
                <button class="op-button" onclick="toggleForm('form7')">Go</button>
            </div>
            <div id="form7" class="form-container">
                <form method="get" action="view_report.php">
                    <input type="submit" value="View Report">
                </form>
            </div>
        </div>
    </ol>
</body>

</html>