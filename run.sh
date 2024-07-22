#!/bin/bash

# Define variables
AGENT_PATH=/home/kduah/Desktop/libraryManagement/libraryManagement/lib/org.jacoco.agent-0.8.12.jar
CLI_PATH=/home/kduah/Desktop/libraryManagement/libraryManagement/lib/jacoco-cli-0.8.12.jar
OUTPUT_FILE=/home/kduah/Desktop/libraryManagement/libraryManagement/jacoco.exec
SRC_DIR=/home/kduah/Desktop/libraryManagement/libraryManagement/src
TEST_DIR=/home/kduah/Desktop/libraryManagement/libraryManagement/test
CLASSES_DIR=/home/kduah/Desktop/libraryManagement/libraryManagement/bin
LIB_DIR=/home/kduah/Desktop/libraryManagement/libraryManagement/lib

# Clean previous outputs
rm -rf $CLASSES_DIR
mkdir -p $CLASSES_DIR

# Compile the source files
javac -cp "$LIB_DIR/*" -d $CLASSES_DIR $(find $SRC_DIR -name "*.java")

# Compile the test files
javac -cp "$CLASSES_DIR:$LIB_DIR/*" -d $CLASSES_DIR $(find $TEST_DIR -name "*.java")

# Find and format test class names with package structure
TEST_CLASSES=$(find $TEST_DIR -name "*Test.java" | sed "s|$TEST_DIR/||" | sed 's/\.java//g' | sed 's/\//./g' | tr '\n' ' ')

# Check compiled class files
echo "Compiled classes:"
find $CLASSES_DIR -name "*.class"

# Run the tests with JaCoCo agent
echo "Running tests with JaCoCo agent..."
java -javaagent:$AGENT_PATH=destfile=$OUTPUT_FILE -cp "$CLASSES_DIR:$LIB_DIR/*" org.junit.runner.JUnitCore $TEST_CLASSES

# Compile the GenerateReport.java file
javac -cp "$LIB_DIR/jacoco-cli-0.8.12.jar" GenerateReport.java

# Generate the coverage report using the GenerateReport class
echo "Generating coverage report..."
java -cp ".:$LIB_DIR/*" GenerateReport

echo "Coverage report generated at coverage-report/index.html"
