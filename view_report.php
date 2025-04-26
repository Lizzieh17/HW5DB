<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Report</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>View Report</h1>
    </header>

    <main class="container">
        <div class="panel" style="max-width: 600px; width: 100%;">
            <?php
                $actionPage = 'viewReport';

                $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar DormManagement ' . escapeshellarg($actionPage);
                $command = escapeshellcmd($command);
                $output = shell_exec($command);

                if (!empty($output)) {
                    echo '<div class="report-output">' . $output . '</div>';
                } else {
                    echo '<p>No report output received.</p>';
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
