# Bank account kata
This banking application allows users to manage their bank accounts, including making deposits, withdrawals, and viewing transaction statements.

## Overview

### Technologies Used

- **Spring Boot**: 3.4.0
- **Java**: 17
- **Maven**: 3.9.7
- **Docker**: 27.0.3
- **Amazon Corretto**: 17
- **Google Cloud SDK**: 504.0.1


### Predefined Clients
* Client ID: C001
* Client ID: C002
### Request JSON form

## endpoints
1 Depositing Money:
  * Endpoint: http://34.173.6.46/api/accounts/C001/deposit
  * POSTRequest Body: Send a request with the amount to deposit in JSON format.
  * Example Request:
  ```json
{
  "amount": 100,00
}
```
  2 Withdrawing Money:
  
 * Endpoint: http://34.173.6.46/api/accounts/C001/withdraw
  * POSTRequest Body: Send a request with the amount to deposit in JSON format.
  * Example Request:
  ```json
{
  "amount": 100,00
}
```
3 Viewing Transaction Statement:
  * To view the transaction history, use the endpoint: http://34.173.6.46/api/accounts/C001/statement
  * This will return a list of all transactions for that client.
