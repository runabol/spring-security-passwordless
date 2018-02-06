# Introduction

We all have a love/hate relationship with passwords. They protect our most valuable assets but they are so god damn hard to create and remember. 

And just to make things even harder for us humans, more and more companies are now enforcing two factor authentication (you know, the little phone pincode thing) to make it even more complicated to login to our accounts.

Despite advances in biometric authentication (fingerprint, face recognition etc.), passwords still remain the most ubiqutous form of authentication. 

So what can we do to help our fellow users to access our application in an easier manner but without compromising security?

This is where passwordless login comes in.

How does it work? 

If you ever went to a website, realized you forgot your password and then used their "Forgot Password" then you know what passwordless login is. 

After you entered your email address on the Reset Password page you were sent a "magic" link with a special code (a.k.a "token") embedded in it which provided you with the ability to reset your password. 

That website piggy-backed on your already-password-protected email address to create a secure, one-time-password "magic" link to your account. 

Well, if we can do all that in a presumably safe way when the user loses his password why can't we do it whenever a user wants to login? Sure we can.

Oh, and just in case you're wondering some big name (Slack, Medium.com, Twitter) companies are already using this method of authentication.

Alright, let's get down to business then.  

# The nitty gritty

1. Create a [sign-up/sign-in page](https://github.com/creactiviti/spring-security-passwordless/blob/master/src/main/resources/templates/signin.html). It basically needs only one field: email.

```
<input type="email" name="email" class="form-control" placeholder="Email address" required autofocus>
```

2. Create an [endpoint](https://github.com/creactiviti/spring-security-passwordless/blob/master/src/main/java/com/creactiviti/spring/security/passwordless/web/SigninController.java#L35) to handle the form submission:

```
  private final TokenStore tokenStore;
  private final Sender sender;

  @PostMapping("/signin")
  public String signin (@RequestParam("email") String aEmail) {
    
    // verify that the user is in the database.
    // ...
    
    // create a one-time login token
    String token = tokenStore.create(aEmail);
    
    // send the token to the user as a "magic" link
    sender.send(aEmail, token);
    
    return "login_link_sent";
  }
```

3. Create an [endpoint](https://github.com/creactiviti/spring-security-passwordless/blob/master/src/main/java/com/creactiviti/spring/security/passwordless/web/SigninController.java#L48) to authenticate the user based on the "magic" link:

```
  private final Authenticator authenticator;

  @GetMapping("/signin/{token}")
  public String signin (@RequestParam("uid") String aUid, @PathVariable("token") String aToken) {
    try {
      authenticator.authenticate(aUid, aToken);
      return "redirect:/";
    }
    catch (BadCredentialsException aBadCredentialsException) {
      return "invalid_login_link";
    }
  }
```

And that's about it.

# Securing the "magic" link.

There are few precautions you should take to keep the "magic" link as secure as possible:

1. When sending the link to the user communicate to your email server over SSL. 

2. Tokens should only be usable once. 

3. Tokens should not be easily guessable. Use a good, cryptographically strong random number generator. e.g:

```
    SecureRandom random = new SecureRandom();
    byte bytes[] = new byte[TOKEN_BYTE_SIZE];
    random.nextBytes(bytes);
    String token = String.valueOf(Hex.encode(bytes));
```
     
4. Tokens should expire after a reasonable amount of time (say 15 minutes). In this example I use an in-memory `TokenStore` implementation backed by a `SelfExpringHashMap` which as its name suggests expires entries after a given amount of time. In a real-world scenario you will most likely use a database to store your generated tokens so your website can run on more than one machine and so these tokens survive a crash. But the principle is the same. You can have a `created_at` field which stamps the time the token was created so you can determine if it expired or not.


# Running the demo

1. Clone the repo:

```
git clone https://github.com/creactiviti/spring-security-passwordless.git
```

2. Build

```
mvn clean spring-boot:run -Dspring.mail.host=<SMTP HOST> -Dspring.mail.username=<SMTP USERNAME> -Dspring.mail.password=<SMTP PASSWORD> -Dpasswordless.email.from=<SENDER EMAIL ADDRESS>
```

3. Sign-in

Go to [http://localhost:8080/signin](http://localhost:8080/signin)


# License

Apache License version 2.0.

