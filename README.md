# Mini Social Network
This is one of the assignments of Java Web LAB, which is a subject at FPT University.

## Features
This app uses Filter as Main Controller and follow MVC2 model.

### Function 1: Register a new account
* A new account is registration following information such as email, name, password, role, status (email as 
ID)
* The default role of new account is member
* The default status of new account is new
* Password must be encrypted using SHA-256 before store in database

### Function 2: Login
* The actor enters ID and password, the function checks if the ID with the password is in the available 
account list, then grant the access permission. If not, a message would appear no notify that account is 
not found
* Login function includes logout and welcome functions

### Function 3:  Search articles
* List the available article in the system and sort by date
* Each page has a maximum of 20 records displayed
* Paging is required to use
* Login is required

### Function 4:  Display Article Details
* Users click on each post to see details such as comments, number of likes, and dislikes
* Login is required

### Function 5: Post the article
* Login is required
* If the user has not authenticated, the system redirects to the login page
* The default status of the article is Active
* The status will be changed to Delete if the user or Admin deletes 
that post
* Only members can use this function

### Function 6: Delete the Article
* User can delete his post or his comments
* This action requires to show a confirmation message before deletion

### Function 7: Verify registered user using email
* When registering a new account, the system will send a confirmation code to the email address that is 
registered with third party as Google, Yahoo, etc
* After entering the verification code, the status of the account will change to Active
* Only members can use this function

<!-- ### Function 8: Post the comment
* (in progress)

### Function 9: Make emotions actions
* (in progress)

### Function 10:  Show Notification
* (in progress) -->
## Contact me via
1. [GitHub](www.github.com/chloe-nguyen)
2. [Linkedin](www.linkedin.com/in/chloe-nguyen-1206)
3. Email: *chloenguyen1206@gmail.com*

### © 2020 by chloe-nguyen

# MiniSocialNetwork
