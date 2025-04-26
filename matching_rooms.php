<?php
ini_set('display_errors', 1);
error_reporting(E_ALL);

$toastMessage = "";
$toastIsSuccess = false;
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>View All Rooms that Match with Given Student</title>
    <link rel="stylesheet" href="styles.css">

    <script>
        function showToast(message, isSuccess) {
            const toast = document.getElementById("toast");
            toast.innerHTML = message.replace(/\n/g, "<br>");
            toast.className = "toast-top show " + (isSuccess ? "toast-success" : "toast-error");
            setTimeout(() => {
                toast.classList.remove("show");
            }, 5000);
        }
    </script>
</head>

<body>
    <header>
        <h1>Matching Rooms for Student</h1>
    </header>

    <div id="toast" class="toast-top"></div>

    <main class="container">
        <?php
        if (isset($_POST['submit'])) {
            $actionPage = "viewMatchingRooms";
            $studentID = $_POST['studentID'] ?? '';

            $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar DormManagement ' .
                escapeshellarg($actionPage) . ' ' . escapeshellarg($studentID);
            $command = escapeshellcmd($command);

            $output = [];
            $status = 0;
            exec($command, $output, $status);
            $outputMessage = implode("\n", $output);

            if (strpos($outputMessage, "ERROR") !== false) {
                $toastMessage = $outputMessage;
                $toastIsSuccess = false;
            } else {
                if (count($output) > 0 && !empty($output[0])) {
                    echo '<div class="results-container">';
                    echo '<h2>Matching Rooms for Student: ' . htmlspecialchars($studentID) . '</h2>';
                    echo '<table class="results-table">';
                    echo '<tr><th>Building ID</th><th>Building Name</th><th>Has AC</th><th>Has Dining</th>';
                    echo '<th>Room ID</th><th>Has Kitchen</th><th>Has Private Bath</th></tr>';

                    foreach ($output as $line) {
                        $line = trim($line);
                        if (!empty($line)) {
                            $parts = explode(" | ", $line);
                            if (count($parts) >= 7) {
                                echo '<tr>';
                                foreach ($parts as $value) {
                                    echo '<td>' . htmlspecialchars($value) . '</td>';
                                }
                                echo '</tr>';
                            }
                        }
                    }

                    echo '</table>';
                    echo '</div>';
                } else {
                    echo '<div class="results-container">';
                    echo '<p class="no-results">No matching rooms found for this student.</p>';
                    echo '</div>';
                    $toastMessage = "No matching rooms found for this student.";
                    $toastIsSuccess = false;
                }
            }
        }
        ?>

        <div class="panel" style="max-width: 600px; width: 100%;">
            <form method="post" action="matching_rooms.php" class="styled-form">
                <label for="studentID">Student ID:</label>
                <input type="text" name="studentID" id="studentID" required>

                <input type="submit" name="submit" value="Show Rooms">
            </form>

            <div style="margin-top: 1rem;">
                <a href="home.php" class="op-button" style="text-decoration: none;">Back to Home</a>
            </div>
        </div>
    </main>

    <footer>
        <p>Created by Luke Lyons and Lizzie Howell</p>
    </footer>

    <?php if (!empty($toastMessage)): ?>
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                showToast(
                    <?= json_encode($toastMessage) ?>,
                    <?= json_encode($toastIsSuccess) ?>
                );
            });
        </script>
    <?php endif; ?>
</body>

</html>
