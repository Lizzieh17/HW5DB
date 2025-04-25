<?php
ini_set('display_errors', 1);
error_reporting(E_ALL);

$toastMessage = "";
$toastIsSuccess = false;

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit'])) {
    $actionPage = "addStudent";
    $studentID = $_POST['studentID'] ?? '';
    $name = $_POST['name'] ?? '';
    $wantsAC = isset($_POST['wantsAC']) ? 'true' : 'false';
    $wantsDining = isset($_POST['wantsDining']) ? 'true' : 'false';
    $wantsKitchen = isset($_POST['wantsKitchen']) ? 'true' : 'false';
    $wantsPrivateBath = isset($_POST['wantsPrivateBath']) ? 'true' : 'false';

    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar DormManagement ' .
                escapeshellarg($actionPage) . ' ' . escapeshellarg($studentID) . ' ' . escapeshellarg($name) . ' ' .
                escapeshellarg($wantsAC) . ' ' . escapeshellarg($wantsKitchen) . ' ' .
                escapeshellarg($wantsDining) . ' ' . escapeshellarg($wantsPrivateBath);

    $command = escapeshellcmd($command);
    $output = [];
    $status = 0;
    exec($command, $output, $status);
    $outputMessage = implode("\n", $output);

    if (strpos($outputMessage, "ERROR") !== false) {
        $toastMessage = $outputMessage;
    } else {
        $toastMessage = "Student added successfully!";
        $toastIsSuccess = true;
    }
}
?>


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

        <?php if (!empty($toastMessage)):
            $isSuccess = strpos($toastMessage, "successfully") !== false;
            ?>
            document.addEventListener("DOMContentLoaded", () => {
                showToast(<?= json_encode($toastMessage) ?>, <?= $isSuccess ? 'true' : 'false' ?>);
            });
        <?php endif; ?>
    </script>


    <main class="container">
        <div class="panel" style="max-width: 500px; width: 100%;">
            <p>Example:</p>
            <p>Student ID: 1001</p>
            <p>Name: John Doe</p>
            <p>Check the boxes for the amenities the student wants.</p>

            <form method="post" action="add_student.php" class="styled-form">
                <label for="studentID">ID:</label>
                <input type="text" name="studentID" id="studentID" required>

                <label for="name">Name:</label>
                <input type="text" name="name" id="name" required>

                <label><input type="checkbox" name="wantsAC" value="true"> Wants AC</label>
                <label><input type="checkbox" name="wantsDining" value="true"> Wants Dining</label>
                <label><input type="checkbox" name="wantsKitchen" value="true"> Wants Kitchen</label>
                <label><input type="checkbox" name="wantsPrivateBath" value="true"> Wants Private Bath</label>

                <input type="submit" name="submit" value="Add Student">
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