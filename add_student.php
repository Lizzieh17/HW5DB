<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <title>Add a student</title>
    </head>

    <body>
        <div>
            <div class="block-container">
                <div id="form1" class="form-container">
                    <form method="post" action="add_student.php">
                        ID: <input type="text" name="studentID"><br>
                        Name: <input type="text" name="name"><br>
                        WantsAC: <input type="checkbox" name="wantsAC" value="true"><br>
                        WantsDining: <input type="checkbox" name="wantsDining" value="true"><br>
                        WantsKitchen: <input type="checkbox" name="wantsKitchen" value="true"><br>
                        WantsPrivateBath: <input type="checkbox" name="wantsPrivateBath" value="true"><br>
                        <input type="submit" name='submit' value="Add Student">
                    </form>
                </div>
            </div>

            <a href="home.php">Back to Home</a> 
        </div>
    </body> 
</html>

<?php
if(isset($_POST['submit'])){

    $studentID = $_POST['studentID'] ?? '';
    $name = $_POST['name'] ?? '';
    $wantsAC = isset($_POST['wantsAC']) ? 'true' : 'false';
    $wantsDining = isset($_POST['wantsDining']) ? 'true' : 'false';
    $wantsKitchen = isset($_POST['wantsKitchen']) ? 'true' : 'false';
    $wantsPrivateBath = isset($_POST['wantsPrivateBath']) ? 'true' : 'false';

    //$jsonStudent = escapeshellarg(json_encode($student));
    $command = "java DormManagement " . escapeshellarg($studentID) . ' ' . escapeshellarg($name) . ' ' .  escapeshellarg($wantsAC) . ' ' . 
                escapeshellarg($wantsKitchen) . ' ' . escapeshellarg($wantsDining) . ' ' . escapeshellarg($wantsPrivateBath);

    $command = escapeshellcmd($command);

    echo "<h2>Student Added</h2>";
    echo "Student ID: " . htmlspecialchars($studentID) . "<br>";
    echo "Name: " . htmlspecialchars($name) . "<br>";
    echo "Wants AC: " . htmlspecialchars($wantsAC) . "<br>";
    echo "Wants Dining: " . htmlspecialchars($wantsDining) . "<br>";
    echo "Wants Kitchen: " . htmlspecialchars($wantsKitchen) . "<br>";
    echo "Wants Private Bath: " . htmlspecialchars($wantsPrivateBath) . "<br>";
    echo "command: $command <br>";
    system($command); 
} 
?>