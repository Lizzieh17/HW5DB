<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>View All Assignments From a Building</title>
    <link rel="stylesheet" href="styles.css">
    <script>
        function showToast(message, type = 'error') {
            const toast = document.getElementById("toast");
            toast.innerHTML = message.replace(/\n/g, "<br>");
            toast.classList.add("show", type);
            setTimeout(() => {
                toast.classList.remove("show", type);
            }, 5000);
        }
    </script>
</head>

<body>
    <header>
        <h1>View Assignments from a Building</h1>
    </header>

    <div id="toast" class="toast-top"></div>

    <main class="container">
        <?php
        $toastMessage = "";
        
        if (isset($_POST['submit'])) {
            $actionPage = "viewAssignments";
            $buildingID = $_POST['buildingID'] ?? '';

            $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar DormManagement ' .
                escapeshellarg($actionPage) . ' ' . escapeshellarg($buildingID);
            $command = escapeshellcmd($command);
            $output = shell_exec($command);

            $lines = explode("\n", trim($output));
            if (count($lines) > 0) {
                echo '<div class="results-container">';
                echo '<h2>Assignments for Building: ' . htmlspecialchars($lines[0]) . '</h2>';

                if (count($lines) > 1) {
                    echo '<table class="results-table">';
                    echo '<tr><th>Room ID</th><th>Student Name</th></tr>';
                    for ($i = 1; $i < count($lines); $i++) {
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
                } else {
                    $toastMessage = "No assignments found for this building.";
                    echo '<p class="no-results">No assignments found for this building.</p>';
                }

                echo '</div>';
            } else {
                $toastMessage = "Error: Couldn't retrieve assignments.";
            }
        }
        ?>

        <div class="panel" style="max-width: 500px; width: 100%;">
            <form method="post" action="view_assignments.php" class="styled-form">
                <label for="buildingID">Building ID:</label>
                <input type="text" name="buildingID" id="buildingID" required>

                <input type="submit" name="submit" value="Show Assignments">
            </form>

            <div style="margin-top: 1rem;">
                <a href="home.php" class="op-button" style="text-decoration: none;">Back to Home</a>
            </div>
        </div>
    </main>

    <footer>
        <p>Created by Luke Lyons and Lizzie Howell</p>
    </footer>

    <script>
        <?php if (!empty($toastMessage)) : ?>
            document.addEventListener("DOMContentLoaded", () => {
                showToast(<?= json_encode($toastMessage) ?>, 'error');
            });
        <?php endif; ?>
    </script>
</body>

</html>
