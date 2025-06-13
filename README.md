# Blockchain-Based Student Record Management System

## 📚 Description
This Java-based desktop application uses blockchain principles to securely manage student exam records. Built with Java Swing and JDBC, the application includes features for role-based access (admin/student), secure record storage, tamper verification, PDF export, and visual analytics.

## 🚀 Features
- Blockchain-like record chaining with SHA-256 hashes
- Admin and Student login roles
- Add/view exam records with verification
- Detect tampering in database records
- PDF export of entire blockchain
- Interactive charts (bar graph of marks)
- Light/Dark theme toggle
- Modern UI with FlatLaf
- Tampered log tracking

## 🛠️ Technologies Used
- Java (Swing, JDBC)
- MySQL
- FlatLaf for modern UI
- JFreeChart for bar charts

## ⚙️ Installation
1. Import the Maven project in IntelliJ/Eclipse.
2. Run the SQL script: `blockchain_exam_schema.sql` to set up the database.
3. Update your DB credentials in `util/Database.java`.
4. Run `LoginScreen.java`.

## 🧪 Test Users
- Admin: `admin1` / `adminpass`
- Student: `student1` / `studentpass`

## 📂 Project Structure
- `ui/` – User interface (login, register, dashboard)
- `model/` – Blockchain logic
- `util/` – Helpers (DB, hasher, PDF)
- `db/` – Login/register manager

## 📄 License
This is an academic project created for learning and demonstration purposes.