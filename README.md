# Gift Certificate Application

The application consists of 9 nodes. Each node has a complete list of all nodes at startup. To maintain the cluster's integrity and its state, the application periodically sends a request for the list of available nodes. 

## Main Entities

- **Gift Certificate**: (ID, Name, Description, Price, Duration, Create Date, and Last Update Date)
- **Tag**: (ID and Name)
- **User**: (ID, Username, First Name, Last Name, Telephone, and Address)

## Certificate Endpoints

- **POST**: Add a certificate
  - Endpoint: `http://localhost:8080/api/v1/certificates`

- **PUT**: Update a certificate
  - Endpoint: `http://localhost:8080/api/v1/certificates/{id}`

- **DELETE**: Delete a certificate
  - Endpoint: `http://localhost:8080/api/v1/certificates/{id}`

- **GET**: Get a certificate by ID
  - Endpoint: `http://localhost:8080/api/v1/certificates/{id}`

- **GET**: Get all certificates
  - Endpoint: `http://localhost:8080/api/v1/certificates`

## Tag Endpoints

- **POST**: Add a tag
  - Endpoint: `http://localhost:8080/api/v1/tags`

- **PUT**: Update a tag
  - Endpoint: `http://localhost:8080/api/v1/tags/{id}`

- **DELETE**: Delete a tag
  - Endpoint: `http://localhost:8080/api/v1/tags/{id}`

- **GET**: Get a tag by ID
  - Endpoint: `http://localhost:8080/api/v1/tags/{id}`

- **GET**: Get all tags
  - Endpoint: `http://localhost:8080/api/v1/tags`

## User Endpoints

- **GET**: Get a user by ID
  - Endpoint: `http://localhost:8080/api/v1/users/{id}`

- **GET**: Get all users
  - Endpoint: `http://localhost:8080/api/v1/users`

## Order Endpoints

- **POST**: Place an order
  - Endpoint: `http://localhost:8080/api/v1/orders`

- **GET**: Get an order by ID
  - Endpoint: `http://localhost:8080/api/v1/orders/{id}`

- **GET**: Get all orders
  - Endpoint: `http://localhost:8080/api/v1/orders`
