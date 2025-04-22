<?php
$name = $_POST['name'] ?? '';
$student_id = $_POST['student_id'] ?? '';

echo "<h2>Student Added</h2>";
echo "Name: " . htmlspecialchars($name) . "<br>";
echo "Student ID: " . htmlspecialchars($student_id);
?>

<html>
    <div>
        <a href="home.php">Back to Home</a>
    </div>
</html>
