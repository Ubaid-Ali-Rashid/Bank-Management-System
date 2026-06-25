# Bank Management System

A GUI-based banking application built in **Java**, designed with a simple, user-friendly interface and file handling for persistent storage of account and client data — no external database required.

## Features

- **Account management** — create new bank accounts and link them to clients.
- **Client management** — create new clients and store their personal/identification details.
- **Core banking operations:**
  - Deposit funds into an account
  - Withdraw funds from an account
  - Delete an existing account
  - Delete an existing client
- **CNIC-based lookup** — search and retrieve a client's information using their CNIC.
- **Bank-wide total balance check** — view the total amount of money held across all accounts in the bank.
- **File-based persistence** — all account and client data is stored and retrieved via file handling, so data survives between application runs without needing a database server.

## Tech Stack

- **Java** — core application logic
- **Java GUI (Swing/AWT)** — desktop user interface
- **File Handling** — persistent storage of accounts and client records

## How It Works

1. The user launches the application and is presented with a GUI for managing the bank.
2. New clients and accounts can be created and immediately linked to each other.
3. Standard transactions — deposits and withdrawals — update the relevant account's balance and persist the change to file.
4. Accounts or clients can be deleted when no longer needed, removing their records from storage.
5. A client's details can be looked up at any time using their CNIC.
6. The total funds held across all accounts in the bank can be viewed on demand, giving a quick overview of the bank's overall holdings.

## Key Concepts Demonstrated

- Java GUI development for desktop applications
- File handling for persistent data storage (without a database)
- CRUD operations on accounts and clients
- Search/lookup logic (CNIC-based retrieval)
- Aggregation logic (computing total balance across all accounts)

## Possible Future Improvements

- Migrate file-based storage to a relational database (e.g. MySQL or SQLite) for better scalability and data integrity.
- Add transaction history/logging per account.
- Add input validation and authentication (PIN/password) before allowing transactions.

## Author

**Ubaid Ali** — Computer Engineering student, UET Lahore.
