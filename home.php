<!DOCTYPE html>
<html>

<head>
    <title>Home</title>
    <style>
        .form-container {
            display: none;
            margin-left: 20px;
            margin-bottom: 20px;
        }

        html,
        body {
            height: 100%;
        }

        html {
            display: table;
            margin: auto;
        }

        body {
            display: table-cell;
            vertical-align: middle;
        }

        .block-container {
            border: 2px solid #ccc;
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 20px;
            background-color: #f9f9f9;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .block-container:nth-child(odd) {
            background-color: #e6f7ff;
        }

        .block-container:nth-child(even) {
            background-color: #fffbe6;
        }

        .block-container button {
            margin-top: 10px;
        }
    </style>
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
            <div>
                <button class="op-button" onclick="toggleForm('form1')">Go</button>
            </div>
            <div id="form1" class="form-container">
                <form method="post" action="add_student.php">
                    Name: <input type="text" name="name"><br>
                    ID: <input type="text" name="student_id"><br>
                    <input type="submit" value="Add Student">
                </form>
            </div>
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