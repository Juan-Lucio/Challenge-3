# 🧭 Project Setup Guide

This guide explains how to **clone**, **configure**, and **run** the Research Lookup Java Swing application from GitHub.  
The application allows users to search for academic researchers and their publications using the **Google Scholar API (via SerpApi)**, displaying results in an interactive desktop interface.

---

## 📋 Prerequisites

Before starting, make sure you have the following installed:

| Tool | Version | Description |
|------|----------|-------------|
| [Java JDK](https://adoptium.net/) | 17 or later | Required for compiling and running the application |
| [Apache Maven](https://maven.apache.org/) | 3.8+ | For dependency management and project build |
| [PostgreSQL](https://www.postgresql.org/download/) | 13 or later | The relational database used by the project |
| [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) | Community or Ultimate | Recommended IDE for Java development |
| [Git](https://git-scm.com/downloads) | Latest | For cloning the repository |

---

## 📦 Clone the Repository

1. Open a terminal and navigate to your preferred development directory:
   ```bash
   cd ~/Documents/Projects
Clone the repository:

bash
Copy code
git clone https://github.com/YOUR_USERNAME/ResearchLookup.git
Move into the project directory:

bash
Copy code
cd ResearchLookup
Import the project into IntelliJ IDEA:

Open IntelliJ IDEA → File → Open → Select the ResearchLookup folder

IntelliJ will automatically detect the Maven configuration (pom.xml) and download dependencies.

🗃️ Database Configuration
The project uses PostgreSQL with HikariCP for connection pooling.

1. Create the Database
Log in to PostgreSQL and create a database:

sql
Copy code
CREATE DATABASE research_db;
2. Create the Tables
Run the following SQL commands inside your database (you can use pgAdmin or psql):


CREATE TABLE articles (
    id SERIAL PRIMARY KEY,
    author_id VARCHAR(255) REFERENCES researchers(author_id),
    title TEXT,
    authors TEXT,
    publication_date VARCHAR(50),
    summary TEXT,
    link TEXT,
    keywords TEXT,
    cited_by INT
);
⚙️ Environment Variables Setup
The application uses environment variables to access sensitive information like API keys and database credentials.

You must define the following variables in your system:

Variable	Description	Example
SERPAPI_KEY	Your SerpApi API key (required to fetch researcher data)	ab12cd34ef56gh78ij90kl12mn34op56
DB_URL	JDBC connection string to PostgreSQL	jdbc:postgresql://localhost:5432/DB_process_automation
DB_USER	PostgreSQL username	postgres
DB_PASSWORD	PostgreSQL password	mysecurepassword

🪟 Windows PowerShell
powershell
Copy code
setx SERPAPI_KEY "YOUR_SERPAPI_KEY"
setx DB_URL "jdbc:postgresql://localhost:5432/research_db"
setx DB_USER "postgres"
setx DB_PASSWORD "your_password"
🐧 macOS / Linux
Add these lines to your ~/.bashrc or ~/.zshrc file:

bash
Copy code
export SERPAPI_KEY="YOUR_SERPAPI_KEY"
export DB_URL="jdbc:postgresql://localhost:5432/research_db"
export DB_USER="postgres"
export DB_PASSWORD="your_password"
Then apply the changes:

bash
Copy code
source ~/.bashrc
🧩 Running the Application
You can run the project in two different ways:

1. 🧠 From IntelliJ IDEA
Open the project in IntelliJ.

Make sure the SDK is set to JDK 17.

Right-click on the file App.java (usually located in src/main/java/com/nao/App.java) →
Select Run 'App.main()'.

2. 💻 From the Command Line
Build and run using Maven:

bash
Copy code
mvn clean install
java -cp target/com.example.research-1.0-SNAPSHOT.jar com.nao.App
🧪 Testing Database Connection
To verify your database connection is working correctly, use the PostgreSQL shell:

bash
Copy code
psql -U postgres -d research_db -c "\dt"
You should see:

pgsql
Copy code
     List of relations
 Schema |    Name      | Type  |  Owner
--------+--------------+-------+----------
 public | researchers  | table | postgres
 public | articles     | table | postgres
(2 rows)
🧰 Troubleshooting
Problem	Cause	Solution
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder"	Missing logging binding	Add slf4j-simple dependency (already included) or ignore (harmless warning)
Database connection failed	Wrong DB_URL or credentials	Verify environment variables and PostgreSQL service
No researcher found	Invalid or missing SerpApi key	Make sure SERPAPI_KEY is valid
Articles missing abstract/keywords	SerpApi response does not contain those fields	Enable debug mode (setx DEBUG_SERPAPI "true") and check raw API output


🧑‍💻 Author
Developed by Juan-Lucio
For questions or suggestions, please open an issue in the repository.


---

Would you like me to also generate a **Spanish version** of this setup guide (`SETUP_GUIDE_ES.md`) for bilingual documentation?  
That’s often a good practice if your repo is meant for both English and Spanish users.
