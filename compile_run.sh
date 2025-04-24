#!/bin/bash
set -e -v

echo "Deleting all .class files in this directory and subdirectories..."
find . -name "*.class" -type f -delete
echo "Cleanup complete!"

echo "Compiling..."
javac *.java
