# Spring Security Cheat Sheet

## Step 1: Set up a user model and repository

- Step 1A: Make a user model, do NOT name the model "User"
- Step 1B: Create a repository interface for the user model
  - Make sure it `extends JpARepositoty`

## Step 2: Create a controller for that model

- Autowire the new repository as a field in the controller

## Step 3: Create a dummy login page

- No functionality yet, just return "/login.html"
- Make sure there is a login.html file in resources/templates

## Step 4: Wire up the user details service implementation

- Step 4A: Create `UserDetailsServiceImpl` class which `implements UserDetailsService` and add its required methods
- Step 4B: Create a `findByUsername` method in your user repository to help with accessing user details
- Step 4C: Implement `UserDetails` in the model class and add `UserDetails`'s required methods to help get the user's details from Spring Security
  - **Tip:** Use IntelliJ to implement the methods
  - Make the boolean ones all return true
- Step 4D: Complete the `loadUserByUsername` method in `UserDetailServiceImpl` method by calling the user repo's `findByUsername` method and returning its response

## Step 5: WebSecurityConfig extends WebSecurityConfigurerAdapter

- Has a UserDetailsService
- Has a PasswordEncoder Bean
- Has a SecurityFilterChain Bean
  - Takes a HttpSecurity object as param
  - CORS? CSRF?
  - Matchers for URLs that are allowed
    - Ensure that login and signup URLs allowed; also consider homepage etc.
  - Form login with login page set up
  - Logout
  - includes AuthenticationManagerBuilder shared object
    - Sets the userDeatulService and passwordEncoder

## Step 6: Compete Login Page

- Create it w/ form
- Ensure it posts to the route you specified in WebSecurityConfig
- Try it out!
- Add to a template w/ things about the Principal

## Step 7: Create Registration page

- Step 7A: Create signup.html w/ a form
- Step 7B: Create a get mapping for the sign up page in the user controller
- Step 7C: Create a post mapping for the sign up page to register new users
  - Ensure the registration form POSTs to a route the controller is ready for
  - Check it's saving in the DB

## Step 8: Create a Homepage

- Step 8A: Create a dummy index.html page
- Step 8B: Create a GET mapping to navigate to the homepage
  - Make sure the link has been specified in WebSecuirtyConfig
  - **Optional:** Place this in a new controller
- Step 8C: Pass the user's information to the html template to handle dispalying their username and login/sign up buttons

## Step 9: Logout button

- Should only visible if a user is logged in
