<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <title>View All Rooms that Match with Given Student</title>
    </head>

    <body>
        <div>
            <div class="block-container">
                <div id="form1" class="form-container">
                    <form method="post" action="matching_rooms.php">
                        Student ID: <input type="text" name="studentID"><br>
                        <input type="submit" name='submit' value="Show Rooms" class="op-button"><br>
                    </form>
                </div>
            </div>

            <a href="home.php">Back to Home</a> 
        </div>
    </body> 
</html>

<?php
if(isset($_POST['submit'])){

    $actionPage = "viewMatchingRooms";
    $studentID = $_POST['studentID'] ?? '';

    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar DormManagement ' . 
                escapeshellarg($actionPage) . ' ' . escapeshellarg($studentID);
    $command = escapeshellcmd($command);
    $output = shell_exec($command);

    $lines = explode("\n", trim($output));
    if(count($lines) > 0){
        echo '<div class="results-container">';
        echo '<h2> Matching rooms for student: ' . htmlspecialchars($studentID) . '<h2>';

        echo '<table class="results-table">';
        echo '<tr><th>Building ID</th><th>Building Name</th><th>Has AC</th><th>Has Dining</th>';
        echo '<th>Room ID</th><th>Has Kitchen</th><th>Has Private Bath</th></tr>';
        for ($i = 0; $i < count($lines); $i++) {
            $line = trim($lines[$i]);
            if (!empty($line)) {
                $parts = explode(" | ", $line);
                if (count($parts) >= 7) {
                    echo '<tr>';
                    echo '<td>' . htmlspecialchars($parts[0]) . '</td>'; 
                    echo '<td>' . htmlspecialchars($parts[1]) . '</td>'; 
                    echo '<td>' . htmlspecialchars($parts[2]) . '</td>'; 
                    echo '<td>' . htmlspecialchars($parts[3]) . '</td>'; 
                    echo '<td>' . htmlspecialchars($parts[4]) . '</td>'; 
                    echo '<td>' . htmlspecialchars($parts[5]) . '</td>'; 
                    echo '<td>' . htmlspecialchars($parts[6]) . '</td>'; 
                    echo '</tr>';
                }
            }
        }
        echo '</table>';
        echo '</div>';
    }

} 
?>
