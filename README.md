# DigestAuthentication
Springboot API with Reactjs app to test a digest authentication method.

## Backend
The API is stateless and run with Springboot projects. It is a microservice architecture with 
- a service for user data, users are stored in a database with hashed password.  
- a service gateway to filter request and manage the digest authentication.

## Frontend
The Reactjs app allows to test request to the API. It registers a new user and send a request to the API with a digest authentication.

### Source
- https://javadeveloperzone.com/spring-boot/spring-security-digest-authentication-example
- https://en.wikipedia.org/wiki/Digest_access_authentication
