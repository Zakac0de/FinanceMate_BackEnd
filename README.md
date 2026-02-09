# 💰 FinanceMate Backend

FinanceMate is a robust and secure RESTful API built with **Spring Boot 3** and **Java 17** for personal finance management. It helps users track expenses, categorize transactions automatically, and visualize spending habits through dashboard analytics.

The application features **JWT Authentication**, strict **Data Validation**, and an intelligent **Auto-Categorization Service**.

---

## 🚀 Key Features

* **🔐 Secure Authentication:** Stateless security using **JWT (JSON Web Tokens)** and BCrypt password encoding.
* **🤖 Smart Categorization:** Automatically predicts transaction categories (e.g., "HSL" → *Transport*, "Prisma" → *Food*) based on description keywords.
* **📊 Dashboard Analytics:** Aggregates monthly spending data for frontend visualization.
* **🛡️ Data Integrity:** Uses **DTOs (Data Transfer Objects)** to prevent sensitive data leakage and **Jakarta Validation** to ensure data quality.
* **🧪 Tested Logic:** Core business logic is covered by **JUnit 5** and **Mockito** unit tests.
* **💾 In-Memory Database:** Runs on H2 Database for easy development and testing (auto-seeded with demo data).

---

## 🛠️ Tech Stack

* **Language:** Java 17
* **Framework:** Spring Boot 3.2+
* **Security:** Spring Security 6, JWT (JJWT)
* **Database:** H2 Database (Dev), JPA / Hibernate
* **Testing:** JUnit 5, Mockito
* **Tools:** Maven, Lombok, Postman (for API testing)

---

## ⚙️ Installation & Setup

### Prerequisites
* Java Development Kit (JDK) 17 or higher
* Maven

### 1. Clone the Repository
```bash
git clone [https://github.com/your-username/FinanceMate_BackEnd.git](https://github.com/your-username/FinanceMate_BackEnd.git)
cd FinanceMate_BackEnd
2. Build the ProjectBashmvn clean install
3. Run the ApplicationBashmvn spring-boot:run
The server will start at http://localhost:8080.🏃‍♂️ Getting Started (Demo Data)When the application starts, the DataSeeder automatically creates a test user and default categories.Username: demoPassword: demo123 (Note: In a real env, passwords are hashed)📡 API Endpoints1. AuthenticationMethodEndpointDescriptionPOST/api/v1/auth/registerRegister a new userPOST/api/v1/auth/loginLogin and receive a Bearer TokenLogin Request Body:JSON{
  "username": "demo",
  "password": "demo123"
}
2. TransactionsAll transaction endpoints require a valid JWT Token in the Authorization header (Bearer <token>).MethodEndpointDescriptionPOST/api/v1/transactionsCreate a new transaction (Auto-categorized if category missing)GET/api/v1/transactions?userId=1Get all transactions for a userDELETE/api/v1/transactions/{id}Delete a transactionCreate Transaction Example:JSON{
  "amount": 15.50,
  "description": "Wolt food delivery",
  "transactionDate": "2026-02-09",
  "user": { "id": 1 }
}
Result: The system will automatically assign the category "Food".3. DashboardMethodEndpointDescriptionGET/api/v1/dashboard/summaryGet grouped total spending per category for a specific monthQuery Params: ?userId=1&month=2&year=2026Response Example:JSON[
  { "categoryName": "Food", "totalAmount": 150.00 },
  { "categoryName": "Transport", "totalAmount": 55.90 }
]
🧪 Running TestsTo run the unit tests (Mockito & JUnit):Bashmvn test
This ensures that the DTO mapping, Categorization Logic, and Validation rules are working correctly.📁 Project StructurePlaintextcom.example.FinanceMate
├── config/          # Security & Swagger configurations
├── controller/      # REST Controllers (API Layer)
├── dto/             # Data Transfer Objects (Secure data flow)
├── exception/       # Global Exception Handling
├── model/           # JPA Entities (Database Tables)
├── repository/      # Database Interfaces
├── security/        # JWT Authentication Filters & Utils
└── service/         # Business Logic & Categorization AI
