<HTML>
<HEAD><TITLE>Clearing database</TITLE></HEAD>
<BODY>
<?php

//Connect to mysql database
require ('/home/student/strongr/public_html/.credentials.php');
$query="DELETE FROM strongr_BANKACCOUNTS";
$mysqli = new mysqli();
$mysqli->connect("localhost",$MYSQL_USER,$MYSQL_PW,"strongr");
$mysqli->query($query);
print("All accounts cleared!");
?>
<p>
<a href="bank.html">Back to the home screen</a>
</BODY>
</HTML>
