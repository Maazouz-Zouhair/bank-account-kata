# Bank account kata
<<<<<<< HEAD

Think of your personal bank account experience. When in doubt, go for the simplest solution

# Requirements

* Deposit and Withdrawal

* Account statement (date, amount, balance)

* Statement printing

The expected result is a service API, and its underlying implementation, that meets the expressed needs.
Nothing more, especially no UI, no persistence.

# User Stories

## US 1:

In order to save money
As a bank client
I want to make a deposit in my account

## US 2:

In order to retrieve some or all of my savings
As a bank client
I want to make a withdrawal from my account

## US 3:

In order to check my operations
As a bank client
I want to see the history (operation, date, amount, balance) of my operations
=======
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
>>>>>>> origin/bank-account-feature
