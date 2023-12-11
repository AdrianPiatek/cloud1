# Cognito

Create and configure user pool acording to your needs 

## Cognito integration

### Spring Boot as recource server

- dependency: ```org.springframework.boot:spring-boot-starter-oauth2-resource-server```
- properties:
```
    spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://cognito-idp.<aws-region>.amazonaws.com/<pool-id>/.well-known/jwks.json
    spring.security.oauth2.resourceserver.jwt.issuer-uri=https://cognito-idp.<aws-region>.amazonaws.com/<pool-id>
```
replace \<aws-region> and \<pool-id> 

### Angular

Use javascript library [amazon-cognito-identity-js](https://www.npmjs.com/package/amazon-cognito-identity-js)

# RDS (Relational database service) - database

Create and configure database acording to your needs 

## Connecting to DB

Use endpoint provided in **Connectivity & security** panel  
If you want to access DB outside aws services change security group settings  

# ECS (Elastic conteiner service) - docker 

- Create and push images to public docker repository  
- Create cluster
- Create task definition
  - Configure infrastructure
  - Add images
  - Expose ports if nessesery
  - Add nessesery evironment variables
- Create task in cluster
- Add inbound rules in security group to allow access if needed
