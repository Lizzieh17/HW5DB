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

    //$jsonStudent = escapeshellarg(json_encode($student));
    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar DormManagement ' . 
                escapeshellarg($actionPage) . ' ' . escapeshellarg($buildingID);
    $command = escapeshellcmd($command);
    system($command);
} 
?>
