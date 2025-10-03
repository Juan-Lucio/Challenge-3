# Google Scholar Researcher Viewer

A Java application that retrieves and displays information about researchers from Google Scholar using the SerpAPI. The application provides both console and graphical user interfaces for viewing researcher details.

## Features

- Search researchers by their Google Scholar Author ID
- Retrieve comprehensive researcher details including:
  - Full name
  - Total citation count
  - List of published articles with titles, publication years, and citation counts
- Dual interface support: console-based and GUI-based
- Comprehensive error handling with user-friendly messages
- JSON parsing for efficient data processing

## Technologies Used

- **Java 17+** - Core programming language
- **Swing** - For GUI components
- **Apache HttpClient 5** - For HTTP requests
- **Jackson Databind** - For JSON parsing
- **SerpAPI** - Google Scholar API provider
- 
# Installation Guide

## Prerequisites

Before installation, ensure you have:

- Java 17 or higher installed
- A valid SerpAPI key from [SerpAPI](https://serpapi.com/google-scholar-author-api)

## Step-by-Step Installation

# IDE Setup

## Opening the Project in Your IDE

### Recommended IDEs
- IntelliJ IDEA
- Eclipse
- VS Code (with Java extensions)

### Instructions for IntelliJ IDEA

1. **Open IntelliJ IDEA**
2. Select **Open** from the welcome screen or **File > Open** if you have a project already open.
3. Navigate to the directory where you cloned the repository and select the project root folder.
4. Click **OK** to open the project.


### Instructions for VS Code

1. **Open VS Code**
2. Install the [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) if you haven't already.
3. Go to **File > Open Folder** and select the project root folder.

### Verifying Project Structure

After opening the project, ensure the following structure is visible in your IDE's project explorer:

## STEP5_RUN.md


# How to Run

## Running the Application

### From the IDE

1. **Locate the main class**: `App.java` in `src/main/java/com/nao/`.
2. **Right-click** on the file and select **Run** (or **Debug**) to start the application.

### From the Command Line

If you prefer to run from the command line, you can use Maven:


mvn compile exec:java -Dexec.mainClass="com.nao.App"

