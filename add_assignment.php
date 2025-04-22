<?php
$title = $_POST['name'] ?? '';
$due_date = $_POST['due_date'] ?? '';

echo "<h2>Assignment Added</h2>";
echo "Title: " . htmlspecialchars($title) . "<br>";
echo "Due Date: " . htmlspecialchars($due_date);
?>

<html>
    <div>
        <a href="home.php">Back to Home</a>
    </div>
</html>
