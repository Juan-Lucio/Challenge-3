# Google Scholar Researcher

This project is a **Java application** that retrieves and displays information about researchers from **Google Scholar** using the [SerpAPI](https://serpapi.com/google-scholar-author-api).  
It provides two types of user interfaces:  

- **Console View** – displays researcher information in the terminal.  
- **Graphical User Interface (GUI)** – built with **Swing**, allowing users to search authors by ID and view details interactively.  

---

## 🚀 Features

- Search a researcher by their **Google Scholar Author ID**.  
- Retrieve researcher details including:
  - Full name  
  - Total citation count  
  - List of published articles (title, year, citations)  
- Display results both in **console** and in a **GUI window**.  
- Error handling with meaningful messages in both console and GUI.  

---

## 🛠️ Technologies Used

- **Java 17+**  
- **Swing** (for GUI)  
- **Apache HttpClient 5** (for HTTP requests)  
- **Jackson Databind** (for JSON parsing)  
- **SerpAPI** (Google Scholar API provider)  

---

## 📂 Project Structure
src/main/java/com/nao/
│
├── App.java # Entry point
│
├── controller/
│ └── ResearcherController.java # Links service, console, and GUI
│
├── model/
│ ├── Article.java # Represents a research article
│ └── Researcher.java # Represents a researcher
│
├── service/
│ └── ScholarAPIService.java # Handles API calls and JSON parsing
│
└── view/
├── ResearcherGUI.java # Swing-based GUI
└── ResearcherView.java # Console-based view


---

## ⚙️ Setup & Installation

1. **Clone this repository**  
   ```bash
   git clone https://github.com/yourusername/google-scholar-viewer.git
   cd google-scholar-viewer
2. **Open the project in your preferred IDE (e.g., IntelliJ, Eclipse, VS Code).**

3. **Configure dependencies**

Add the following libraries to your project:

Apache HttpClient 5

Jackson Databind

If using Maven, add to your pom.xml:

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.16.1</version>
</dependency>
<dependency>
    <groupId>org.apache.httpcomponents.client5</groupId>
    <artifactId>httpclient5</artifactId>
    <version>5.3</version>
</dependency>

4. **Set your SerpAPI Key**

Open ScholarAPIService.java.

Replace YOUR_API_KEY_HERE with your real SerpAPI key.

**▶️ How to Run**

Run the main class:
App.java
The GUI window will open.

Enter a Google Scholar Author ID (for example: 
Vz3qkF8AAAAJ) and click Search Author.

**Results will appear:**

Researcher’s name and total citations in the left panel.

List of articles in the table.

Results also print to the console.
