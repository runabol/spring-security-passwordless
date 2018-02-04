# Introduction

We all have a love/hate relationship with passwords. They protect are most valuable assets but they are so god damn hard to create and remember. 

And to make things even harder for us humans, more and more companies are now enforcing two factor authentication (you know, the little phone pincode thing) to make it even more complicated to login to our accounts.

Even despite advances in biometric authentication (fingerprint, face recognition etc.), passwords still remain the most ubiqutous form of authentication. 

So what can we do to help our fellow users to access our application in an easier manner but without compromising security?

This is where passwordless authentication comes in.

How does that work? 

If you ever went to a website, realized you forgot your password to that website and then used their "Forgot Password" button where they send you a reset link then you know what passwordless authentication is. 

After you entered your email address on the Reset Password page you were sent a link with a special code (a.k.a "token") embedded in it which providess you with the ability to reset your password. 

That website essentially used your email address to create a secure, one-time-password "magic" link to your account. 

Passwordless authentication says, "well, if we can do this when the user loses his password why can't we do it whenever he needs to login?".

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

The weakest link in the chain is the generated token so you should make it as hard as possible for a malicious user to  

1. When sending the link to the user communicate to your email server over SSL. 

2. Tokens should only be usable once. 

3. Tokens should not be easily guessable. 

4. Tokens should expire after a reasonable amount of time (say 15 minutes).



