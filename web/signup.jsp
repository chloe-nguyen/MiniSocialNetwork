
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up</title>
        <style type="text/css">
            #testform label.error {
                color: red;
            }
        </style>
    </head>
    <body>
        <script type="text/javascript" src="js/jquery-3.5.1.js"></script>
        <script type="text/javascript" src="js/jquery.validate.js"></script>
        <script type="text/javascript" src="js/additional-methods.js"></script>
        
        <script>
            $(function() {
                $("#testform").validate({
                    rules: {
                        txtEmail: {
                            required: true,
                            email: true
                        },
                        txtPassword: {
                            required: true,
                            rangelength: [6,30]
                        },
                        txtConfirmPassword: {
                            equalTo: "#txtPass"
                        },
                        txtName: {
                            required: true,
                            rangelength: [2,255]
                        }
                    } //end rules
                }) //end validate
            }); //end function
        </script>
        
        <h1>Register Page</h1>
        <form action="DispatchController" method="POST" id="testform">
            Email <input type="text" name="txtEmail" value="" />
            <font color="red">${sessionScope.ERROR}</font><br/>
            Password <input type="password" name="txtPassword" value="" id="txtPass" /><br/>
            Confirm Password <input type="password" name="txtConfirmPassword" value="" /><br/>
            Name <input type="text" name="txtName" value="" /><br/>
            <input type="submit" value="Sign up" name="btnAction" /><br/>
            <a href="DispatchController?btnAction=loginPage">Back</a>
        </form>
    </body>
</html>
