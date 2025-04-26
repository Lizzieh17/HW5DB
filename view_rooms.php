<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>View All Rooms</title>
    <link rel="stylesheet" href="styles.css">
</head>

<body>
    <header>
        <h1>View All Rooms</h1>
    </header>

    <main class="container">
        <div class="panel" style="max-width: 800px; width: 100%;">
            <?php
            $actionPage = 'viewAllRooms';
            $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar DormManagement ' . escapeshellarg($actionPage);
            $command = escapeshellcmd($command);
            $output = shell_exec($command);

            if (!empty($output)) {
                $lines = explode("\n", trim($output));

                if (count($lines) > 0 && !empty($lines[0])) {
                    echo '<div style="display: flex; justify-content: center;">';
                    echo '<table class="report-table">';
                    echo '<thead><tr>
                            <th>Building ID</th>
                            <th>Room ID</th>
                            <th>Total Beds</th>
                            <th>Occupied Beds</th>
                            <th>Available Beds</th>
                          </tr></thead>';
                    echo '<tbody>';

                    foreach ($lines as $line) {
                        $columns = array_map('trim', explode(" | ", $line));
                        if (count($columns) >= 5) {
                            echo '<tr>';
                            foreach ($columns as $value) {
                                echo '<td>' . htmlspecialchars($value) . '</td>';
                            }
                            echo '</tr>';
                        }
                    }

                    echo '</tbody></table>';
                    echo '</div>';
                } else {
                    echo '<p class="no-results">No room data available.</p>';
                }
            }

            ?>

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