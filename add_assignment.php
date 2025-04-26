<?php
$toastMessage = "";
$toastIsSuccess = false;

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit'])) {
    $actionPage = "addAssignment";
    $studentID = $_POST['studentID'] ?? '';
    $buildingID = $_POST['buildingID'] ?? '';
    $roomID = $_POST['roomID'] ?? '';

    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar DormManagement ' .
        escapeshellarg($actionPage) . ' ' .
        escapeshellarg($studentID) . ' ' .
        escapeshellarg($buildingID) . ' ' .
        escapeshellarg($roomID);

    $output = [];
    $returnStatus = 0;

    try {
        exec($command, $output, $returnStatus);
        $outputMessage = implode("\n", $output);

        if (strpos($outputMessage, "ERROR") !== false) {
            $toastMessage = $outputMessage;
        } else {
            $toastMessage = "Assignment created successfully!";
            $toastIsSuccess = true;
        }
    } catch (Exception $e) {
        $toastMessage = "PHP Exception: " . $e->getMessage();
    }
}
?>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Create Assignment</title>
    <link rel="stylesheet" href="styles.css">
</head>

<body>
    <header>
        <h1>Create a New Assignment</h1>
    </header>

    <div id="toast" class="toast-top"></div>

    <script>
        function showToast(message, isSuccess) {
            const toast = document.getElementById("toast");
            toast.innerHTML = message.replace(/\n/g, "<br>");
            toast.className = "toast-top show " + (isSuccess ? "toast-success" : "toast-error");
            setTimeout(() => {
                toast.classList.remove("show");
            }, 5000);
        }

        <?php if (!empty($toastMessage)): ?>
            document.addEventListener("DOMContentLoaded", () => {
                showToast(<?= json_encode($toastMessage) ?>, <?= $toastIsSuccess ? 'true' : 'false' ?>);
            });
        <?php endif; ?>
    </script>


    <main class="container">
        <div class="panel">
        <p>Example:</p>
            <p>Student ID: 1001</p>
            <p>Building ID: 1</p>
            <p>RoomID: 102</p>
            <form method="post" action="add_assignment.php" class="styled-form">
                <label for="studentID">Student ID:</label>
                <input type="text" name="studentID" id="studentID" required>

                <label for="buildingID">Building ID:</label>
                <input type="text" name="buildingID" id="buildingID" required>

                <label for="roomID">Room ID:</label>
                <input type="text" name="roomID" id="roomID" required>

                <input type="submit" name="submit" value="Create Assignment" class="op-button">
            </form>

            <a href="home.php" class="op-button">Back to Home</a>
        </div>
    </main>

    <footer>
        <p>Created by Luke Lyons and Lizzie Howell</p>
    </footer>
</body>

</html>