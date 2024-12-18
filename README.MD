# Bank account kata
This banking application allows users to manage their bank accounts, including making deposits, withdrawals, and viewing transaction statements.

## Overview

1. Launch the Application: Start the application by running the main program.
2. A bank named "My Bank" is automatically created when the application starts.

### Predefined Clients
* Client ID: C001, Name: John Doe
* Client ID: C002, Name: Jane Smith

## endpoints
1 Depositing Money:
  * To deposit money, use the endpoint: /api/accounts/{clientId}/deposit
  * Replace {clientId} with the actual client ID (e.g., C001).
  * Send a request with the amount to deposit
  
  2 Withdrawing Money:
  
  * To withdraw money, use the endpoint: /api/accounts/{clientId}/withdraw
  * Replace {clientId} with the actual client ID (e.g., C001).
  * Send a request with the amount to withdraw.
  
3 Viewing Transaction Statement:
  * To view the transaction history, use the endpoint: /api/accounts/{clientId}/statement
  * Replace {clientId} with the actual client ID (e.g., C001).
  * This will return a list of all transactions for that client.
