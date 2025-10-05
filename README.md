🧠 Google Scholar Author Lookup

A Java desktop application that allows users to search and visualize public information about researchers from Google Scholar, using the SerpApi
 service.
The program follows the MVC (Model–View–Controller) design pattern to separate the business logic, user interface, and data retrieval layers.

📘 Introduction

Google Scholar Author Lookup is a tool built to simplify access to academic author data.
By providing a valid Google Scholar Author ID, the application retrieves and displays information such as:

Full name

Affiliations

Verified email

Website

Profile picture

Research interests

It serves as a starting point for academic research tools, data visualization dashboards, or bibliometric analysis systems that require easy integration with Google Scholar data through SerpApi.

⚙️ Features

🔍 Search by Author ID – Retrieves real-time data from the Google Scholar API via SerpApi.

🧩 MVC Architecture – Clean separation between data (Model), logic (Controller), and interface (View).

💻 Swing-based GUI – Simple and responsive desktop interface built with Java Swing.

🌐 Dynamic Data Fetching – Uses HTTP requests and JSON parsing via Apache HttpClient and Jackson.

📸 Thumbnail Support – Loads and scales author profile images automatically.

🚫 Error Handling – Displays user-friendly alerts for invalid IDs or API errors.

🧠 Extensible Codebase – Easy to modify and expand with new API endpoints or UI features.

🏗️ Project Structure
com.nao/
│
├── App.java                        # Application entry point
│
├── controller/
│   └── ResearcherController.java   # Manages interaction between view and service
│
├── model/
│   └── Researcher.java             # Data model representing a researcher
│
├── service/
│   └── ScholarAPIService.java      # Handles API requests and JSON parsing
│
└── view/
    └── ResearcherGUI.java          # Swing-based user interface

🚀 How to Run the Application
🧩 Prerequisites

Before running the program, ensure you have:

Java 17 or higher installed

Apache Maven (recommended for dependency management)

A valid SerpApi API key — get one for free at https://serpapi.com/

🧠 Steps

Clone the repository

git clone https://github.com/<your-username>/google-scholar-lookup.git
cd google-scholar-lookup


Open the project in your IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

Update your API key

Open the file:

src/main/java/com/nao/service/ScholarAPIService.java


Replace the placeholder with your valid API key:

private static final String API_KEY = "YOUR_VALID_API_KEY";


Build and run the project

Using Maven:

mvn clean compile exec:java -Dexec.mainClass="com.nao.App"


Or directly from your IDE by running App.java.

🧭 Usage

Launch the application.

Enter a valid Google Scholar Author ID (for example, VjJt1x8AAAAJ).

Click Search.

View the author’s profile information and image.

Click New Search to perform another query, or Exit to close the program.

🧱 Dependencies
Library	Purpose
Apache HttpClient 5	Makes HTTP requests to SerpApi
Jackson Databind	Parses JSON responses
Swing (javax.swing)	GUI framework for desktop apps

Maven dependencies example:

<dependencies>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.17.0</version>
    </dependency>

    <dependency>
        <groupId>org.apache.httpcomponents.client5</groupId>
        <artifactId>httpclient5</artifactId>
        <version>5.2</version>
    </dependency>
</dependencies>

📂 Example Output

When searching for a valid author ID, the application will display:

Author’s name and affiliations

Email and website

Profile picture

List of research interests

If the ID is invalid or unavailable, a message box will appear showing the corresponding error.

🧩 Architecture Overview
┌──────────────────────┐
│   ResearcherGUI      │
│  (View - Swing UI)   │
└─────────┬────────────┘
          │
          ▼
┌──────────────────────┐
│ ResearcherController │
│ (Handles user input, │
│ calls the service)   │
└─────────┬────────────┘
          │
          ▼
┌──────────────────────┐
│  ScholarAPIService   │
│ (Fetches data from   │
│  SerpApi, parses JSON)│
└─────────┬────────────┘
          │
          ▼
┌──────────────────────┐
│     Researcher       │
│ (Data model class)   │
└──────────────────────┘

🧾 License

This project is released under the MIT License.
You are free to use, modify, and distribute it with attribution.

👨‍💻 Author

Developed by: [Your Name]
GitHub: https://github.com/your-username
