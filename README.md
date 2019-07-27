# REST-Points

The REST-Resource is always reachable through /api/.

## Authentification

The Authentification is utilized statefull. Meaning, the Server keeps the refference for the authentification of the user. Whether or not you are authenticated can be read by the following path:

```
GET
/api/authenticated

Returns 200 (OK) if authenticated
401 (UNAUTHORIZED) else.
```

To register, use the endpoint:

```
POST
/api/users/
Request-Body:
{
    "username":"USERNAME",
    "email":"EMAIL@EMAIL.EMAIL",
    "password":"password"
}

Returns 202 (ACCEPTED) if okay
409 (CONFLICT) if the username or email is already persisted.

Example Response (Okay):

{
    "messages":[
        "msg":"OK"
    ]
}
```

After a successfull registration, the user is automatically authenticated. No separate post to login is required.

To Login, with a registrated account can be done the following way:

```
POST
/api/users/{id}/authenticated
Request-Body:
{
    "password":"PASSWORD"
}

Returns 202 (ACCEPTED) if okay
208 (ALREADY_REPORTED) if the user already is logged in
406 (NOT_ACCEPTABLE) if either the id is not found or the password is incorrect (to prevent bruteforce).
```

Logout can be utilized the following way:

```
DELETE
/api/authenticated

Returns 202 (ACCEPTED) if okay
208 (ALREADY_REPORTED) if the user already is logged out
```
