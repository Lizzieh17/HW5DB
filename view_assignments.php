<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <title>View All Assignments From a Building</title>
    </head>

    <body>
        <div>
            <div class="block-container">
                <div id="form1" class="form-container">
                    <form method="post" action="view_assignments.php">
                        Building ID: <input type="text" name="buildingID"><br>
                        <input type="submit" name='submit' value="Show Assignments" class="op-button"><br>
                    </form>
                </div>
            </div>

            <a href="home.php">Back to Home</a> 
        </div>
    </body> 
</html>

<?php
if(isset($_POST['submit'])){

    $actionPage = "viewAssignments";
    $buildingID = $_POST['buildingID'] ?? '';

    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar DormManagement ' . 
                escapeshellarg($actionPage) . ' ' . escapeshellarg($buildingID);
    $command = escapeshellcmd($command);
    $output = shell_exec($command);

    $lines = explode("\n", trim($output));
    if(count($lines) > 0){
        echo '<div class="results-container">';
        echo '<h2> Assignments for Building: ' . htmlspecialchars($lines[0]) . '<h2>';

        if(count($lines) > 1){
            echo '<table class="results-table">';
            echo '<tr><th>Room ID</th><th>Student Name</th></tr>';
            for($i = 1; $i < count($lines); $i++){
                $line = trim($lines[$i]);
                if (!empty($line) && strpos($line, ' | ') !== false) {
                    list($roomID, $studentName) = explode(" | ", $line, 2);
                    echo '<tr>';
                    echo '<td>' . htmlspecialchars($roomID) . '</td>';
                    echo '<td>' . htmlspecialchars($studentName) . '</td>';
                    echo '</tr>';
                }
            }
            echo '</table>';
        } 
        echo '</div>';
    }

} 
?>
