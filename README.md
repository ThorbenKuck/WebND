# REST-Points

The REST-Resource is always reachable through /api/.

<details>
<summary>
    
<h3>Authentification</h3>

</summary

The Authentification is utilized statefull. Meaning, the Server keeps the refference for the authentification of the user. 

#### Is Authenticated

Whether or not you are authenticated can be read by the following path:

```
GET
/api/authenticated

Returns 200 (OK) if authenticated
401 (UNAUTHORIZED) else.
```

#### Registration

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

#### Login

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

#### Logout

```
DELETE
/api/authenticated

Returns 202 (ACCEPTED) if okay
208 (ALREADY_REPORTED) if the user already is logged out
```

</details>
