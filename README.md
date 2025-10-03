#<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Google Scholar Researcher Viewer</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            color: #333;
            max-width: 1200px;
            margin: 0 auto;
        }
        
        h1 {
            color: #1a0dab;
            border-bottom: 2px solid #1a0dab;
            padding-bottom: 10px;
        }
        
        h2 {
            color: #1a0dab;
            margin-top: 30px;
        }
        
        h3 {
            color: #555;
        }
        
        .feature-list, .tech-list {
            background: #f8f9fa;
            padding: 15px;
            border-left: 4px solid #1a0dab;
            margin: 20px 0;
        }
        
        .project-structure {
            background: #f5f5f5;
            padding: 15px;
            border: 1px solid #ddd;
            font-family: monospace;
            white-space: pre;
            overflow-x: auto;
            margin: 20px 0;
        }
        
        .code-block {
            background: #2d2d2d;
            color: #f8f8f2;
            padding: 15px;
            border-radius: 5px;
            overflow-x: auto;
            margin: 20px 0;
            font-family: monospace;
        }
        
        .step {
            background: #fff;
            border: 1px solid #ddd;
            padding: 15px;
            margin: 15px 0;
            border-radius: 5px;
        }
        
        .step-number {
            background: #1a0dab;
            color: white;
            padding: 5px 10px;
            border-radius: 3px;
            font-weight: bold;
            display: inline-block;
            margin-right: 10px;
        }
    </style>
</head>
<body>
    <h1>Google Scholar Researcher Viewer</h1>
    
    <p>A Java application that retrieves and displays information about researchers from Google Scholar using the SerpAPI. The application provides both console and graphical user interfaces for viewing researcher details.</p>
    
    <h2>Features</h2>
    <div class="feature-list">
        <ul>
            <li>Search researchers by their Google Scholar Author ID</li>
            <li>Retrieve comprehensive researcher details including:
                <ul>
                    <li>Full name</li>
                    <li>Total citation count</li>
                    <li>List of published articles with titles, publication years, and citation counts</li>
                </ul>
            </li>
            <li>Dual interface support: console-based and GUI-based</li>
            <li>Comprehensive error handling with user-friendly messages</li>
            <li>JSON parsing for efficient data processing</li>
        </ul>
    </div>
    
    <h2>Technologies Used</h2>
    <div class="tech-list">
        <ul>
            <li>Java 17+</li>
            <li>Swing (for GUI components)</li>
            <li>Apache HttpClient 5 (for HTTP requests)</li>
            <li>Jackson Databind (for JSON parsing)</li>
            <li>SerpAPI (Google Scholar API provider)</li>
        </ul>
    </div>
    
    <h2>Project Structure</h2>
    <div class="project-structure">
src/main/java/com/nao/
│
├── App.java                    # Application entry point
│
├── controller/
│   └── ResearcherController.java    # Links service, console, and GUI
│
├── model/
│   ├── Article.java            # Represents a research article
│   └── Researcher.java         # Represents a researcher
│
├── service/
│   └── ScholarAPIService.java  # Handles API calls and JSON parsing
│
└── view/
    ├── ResearcherGUI.java      # Swing-based GUI implementation
    └── ResearcherView.java     # Console-based view implementation
    </div>
    
    <h2>Setup & Installation</h2>
    
    <h3>Prerequisites</h3>
    <ul>
        <li>Java 17 or higher</li>
        <li>A valid SerpAPI key</li>
    </ul>
    
    <div class="step">
        <span class="step-number">1</span>
        <strong>Clone the repository</strong>
        <div class="code-block">
git clone https://github.com/yourusername/google-scholar-viewer.git
cd google-scholar-viewer
        </div>
    </div>
    
    <div class="step">
        <span class="step-number">2</span>
        <strong>Open the project in your preferred IDE</strong> (IntelliJ IDEA, Eclipse, VS Code, etc.)
    </div>
    
    <div class="step">
        <span class="step-number">3</span>
        <strong>Configure dependencies</strong>
        <p>Add the following libraries to your project:</p>
        <ul>
            <li>Apache HttpClient 5</li>
            <li>Jackson Databind</li>
        </ul>
        <p>If using Maven, add these dependencies to your <code>pom.xml</code>:</p>
        <div class="code-block">
&lt;dependency&gt;
    &lt;groupId&gt;com.fasterxml.jackson.core&lt;/groupId&gt;
    &lt;artifactId&gt;jackson-databind&lt;/artifactId&gt;
    &lt;version&gt;2.16.1&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.apache.httpcomponents.client5&lt;/groupId&gt;
    &lt;artifactId&gt;httpclient5&lt;/artifactId&gt;
    &lt;version&gt;5.3&lt;/version&gt;
&lt;/dependency&gt;
        </div>
    </div>
    
    <div class="step">
        <span class="step-number">4</span>
        <strong>Configure your API key</strong>
        <p>Open <code>ScholarAPIService.java</code> and replace <code>YOUR_API_KEY_HERE</code> with your actual SerpAPI key.</p>
    </div>
    
    <h2>Usage</h2>
    
    <h3>Running the Application</h3>
    <ol>
        <li>Execute the main class: <code>App.java</code></li>
        <li>The GUI window will open automatically</li>
        <li>Enter a Google Scholar Author ID (example: <code>Vz3qkF8AAAAJ</code>) in the search field</li>
        <li>Click the "Search Author" button to retrieve and display researcher information</li>
    </ol>
    
    <h3>Output</h3>
    <ul>
        <li>Researcher's name and total citation count displayed in the left panel</li>
        <li>Comprehensive list of articles displayed in a table format</li>
        <li>Results are simultaneously printed to the console for reference</li>
    </ul>
    
    <h2>Error Handling</h2>
    <p>The application includes robust error handling for:</p>
    <ul>
        <li>Invalid Author IDs</li>
        <li>API connection issues</li>
        <li>JSON parsing errors</li>
        <li>Network connectivity problems</li>
    </ul>
    <p>Meaningful error messages are displayed in both the GUI and console interfaces.</p>
</body>
</html>
