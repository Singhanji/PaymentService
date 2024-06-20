# Payment

* [Introduction](#introduction)
* [Requirements](#requirements)
* [Prerequisite](#prerequisite)
* [Maven Build](#maven-build)
* [Run the Application in Container](#run-the-application-in-container)
    * 
* [API Endpoints](#api-endpoints)
* [Troubleshooting](#troubleshooting)

## Introduction
The Payment Service is a Java-based application designed to handle payment transactions securely and efficiently.
It integrates with multiple payment gateways such as Razorpay and Stripe.

Before running the application user must obtain necessary secrets by following below instructions:

**Razorpay:**
- Signup: https://dashboard.razorpay.com/?screen=sign_in
- Navigate Developer->API keys
- Generate API key
- You will get Key_id and Key_Secret which will be helping connect to razorpay dashboard.

**Stripe:**
- Signup: https://dashboard.stripe.com/test/dashboard
- Go to Developers -> API Keys
- You can generate your Secret Key there for your Standard Publishable key
- Reveal the test key, use it in the configuration file.

## Requirements
```
Java 8+
Maven 3.6+
Podman
```

## Prerequisite

- Clone the Repository:
```
https://github.com/your-repo/PaymentService.git
```

- Set Environment Variables:
Following environment variables need to be exported to configure MySQL database and Payment Gateway secrets for the application
```bash
export MYSQL_USER=<your-mysql-user>
export MYSQL_PASSWORD=<your-mysql-password>
export MYSQL_ROOT_PASSWORD=<your-mysql-root-password>
export DB_HOST=<your-db-host>
export RAZORPAY_KEY_ID=<your-razorpay-key-id>
export RAZORPAY_KEY_SECRET=<your-razorpay-key-secret>
export STRIPE_KEY_SECRET=<your-stripe-key-secret> 

```   

- Spring boot [application.properties](./paymentservice/src/main/resources/application.properties) defines the DB config
and payment gateway related settings. No changes required in this file, input will be taken from environment variables during
container runtime.
```
spring.datasource.url=jdbc:mysql://${DB_HOST}:3306/{db_name}
spring.datasource.username=${MYSQL_USER}
spring.datasource.password=${MYSQL_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
razorpay.key.id=${RAZORPAY_KEY_ID}
razorpay.key.secret=${RAZORPAY_KEY_SECRET}
stripe.key.secret=${STRIPE_KEY_SECRET}
```

## Maven Build
This command will compile the project, run the tests, and package the application into a JAR file.
```
mvn clean install
```
## Run the Application in Container
- app run in container using podman. Setup.sh is a script to help build and run the application.

```bash
./setup.sh help
Run => ./setup.sh file for help, Command, build the image, and run the container.
Eg =>  ./setup.sh help ==> (for help)
      
Output: Usage
      ./setup.sh {help|cleanup_app|cleanup_db|mysql|build|run}

       Commands:
       help            Show this help message
       cleanup_app     Stop and remove the app container
       cleanup_db      Stop and remove the database container
       my_sql          Run the mysql database container
       build           Build the payment app docker image
       run             Run the payment app container
```

- Start database container
```bash
./setup.sh mysql
```
- Build app image
```bash
./setup.sh build
```
- Start app container
```bash
./setup.sh run
```

# Access application
- The application exposes an API endpoint which can access using host ip and port 8080.
```
http://[HOST IP]:8080/
```
- Here are the primary API endpoints provided by the Payment Service:
```
POST /api/payments: Initiates a payment request
        
     Request:
         {
          "orderId": 11
         }
        
     Response:           
        url: https://xxxx-xxx-xx-x
```                    

# How to test the application
- Razorpay
- Go to this link to test the application
```agsl
https://razorpay.com/docs/payments/payment-gateway/ecommerce-plugins/wix/test-integration/
```
- Go to Cards
- Use any card number, any name, CVV, expiry date

- Stripe
```
https://docs.stripe.com/testing
```
### Additional Information
- Endpoints
  - The application currently supports a single 'POST' endpoint for sending orderId.

- Transaction History Status
  - To view transaction history and payment statuses, please visit your Razorpay or Stripe dashboard.


# Troubleshooting

- Database Connection Issues:

     * Ensure the database is running and accessible.
     * Check environment variables for correct database credentials and host.

- Payment Gateway Errors:

    * Verify the payment gateway keys and secrets.
    * Check the logs for detailed error messages.

- Application Errors:

    * Check the application logs for any runtime exceptions.
    * Ensure all required environment variables are set correctly.


# Todos
- Implement 'GET' Endpoint: 
  - Develop a GET endpoint to retrieve the status and payment history of transactions completed by customers.
- Enhanced Logging: 
  - Implement application logs to capture runtime exceptions and provide detailed error messages for better debugging and monitoring.
- Payment Gateway Integration: 
  - Extend support for additional payment gateways to provide more options for customers.
- Transaction Alerts: 
  - Implement email or SMS a alerts for transaction statuses to notify customers about payment confirmations, failures, and refunds.