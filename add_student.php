<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add a Student</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>Add a Student</h1>
    </header>

    <main class="container">
        <div class="panel" style="max-width: 500px; width: 100%;">
            <form method="post" action="add_student.php" class="styled-form">
                <label for="studentID">ID:</label>
                <input type="text" name="studentID" id="studentID" required>

                <label for="name">Name:</label>
                <input type="text" name="name" id="name" required>

                <label><input type="checkbox" name="wantsAC" value="true"> Wants AC</label>
                <label><input type="checkbox" name="wantsDining" value="true"> Wants Dining</label>
                <label><input type="checkbox" name="wantsKitchen" value="true"> Wants Kitchen</label>
                <label><input type="checkbox" name="wantsPrivateBath" value="true"> Wants Private Bath</label>

                <input type="submit" name="submit" value="Add Student" class="op-button">
            </form>

            <div style="margin-top: 1rem;">
                <a href="home.php" class="op-button" style="text-decoration: none;">Back to Home</a>
            </div>
        </div>
    </main>

    <footer>
        <p>Created by Luke Lyons and Lizzie Howell</p>
    </footer>
</body>
</html>

<?php
if (isset($_POST['submit'])) {
    $actionPage = "addStudent";
    $studentID = $_POST['studentID'] ?? '';
    $name = $_POST['name'] ?? '';
    $wantsAC = isset($_POST['wantsAC']) ? 'true' : 'false';
    $wantsDining = isset($_POST['wantsDining']) ? 'true' : 'false';
    $wantsKitchen = isset($_POST['wantsKitchen']) ? 'true' : 'false';
    $wantsPrivateBath = isset($_POST['wantsPrivateBath']) ? 'true' : 'false';

    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar DormManagement ' .
                escapeshellarg($actionPage) . ' ' . escapeshellarg($studentID) . ' ' . escapeshellarg($name) . ' ' .
                escapeshellarg($wantsAC) . ' ' . escapeshellarg($wantsKitchen) . ' ' . escapeshellarg($wantsDining) . ' ' . escapeshellarg($wantsPrivateBath);

    $command = escapeshellcmd($command);
    system($command); 
    
    
} 
?>