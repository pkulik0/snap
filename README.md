# Snap - URL shortener API

Snap allows users to shorten links just like bit.ly or tinyurl.com. \
Lengthy urls can be shortened to just the base url + 5 random characters.

Stack:
- Java
- Spring Boot
- Apache Commons Validator
- Auth0 Java JWT

## Authentication

### /api/auth/register - **POST**

Example request body:
```json
{
  "username": "example",
  "email": "example@user.com",
  "password": "somethingStrong"
}
```

Example response:
```json
{
  "id": 4,
  "username": "example",
  "email": "example@user.com",
  "password": "$2a$10$q1ZHFbMgvPVWo4rXbJf/CeYmfltrhc.ESLWeLed4.7Ub1TZCSlYA2"
}
```

### /api/auth/login - **POST** 

Requires *username* and *password* as encoded form data. \
The received JWT token is valid for 7 days.

Example response:
```json
{
  "expiresAt": "1664054106725",
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlzcyI6IlNuYXAiLCJleHAiOjE2NjQwNTQxMDZ9.ls85Zyis55T4CAHg-tBgvfQMCgjDXrCBgSnxPkHMAZtGdni30cUur9DRuIhnh60kz_TF4zS5-UDwdFCvo1L4_w"
}
```

## Links

### /api/links - **GET** | **POST**

- GET

Example response
```json
[
  {
    "shortened": "qvkgx",
    "url": "http://a.com",
    "public": true
  },
  {
    "shortened": "MxIwY",
    "url": "http://b.com",
    "public": false
  }
]
```

- POST 

 Example request body 
```json
{
  "url": "http://a.com",
  "public": true
}
```
Response
```json
{
  "shortened": "qvkgx",
  "url": "http://a.com",
  "public": true
}
```

### /api/links/{shortened} - **GET** | **PATCH** | **DELETE**

- GET 

Example response

```json
{
  "shortened": "qvkgx",
  "url": "http://a.com",
  "public": true
}
```

- PATCH

Example request body
```json
{
  "shortened": "STBYx",
  "url": "https://newUrl.com",
  "public": false
}
```
Response: The modified Link object.

- DELETE

Example request ```/api/links/STBYx```

Response: ```Status 200 OK```

### /{shortened} - **GET** 



Response ```Status 302 Found``` with redirect to this link's full url.