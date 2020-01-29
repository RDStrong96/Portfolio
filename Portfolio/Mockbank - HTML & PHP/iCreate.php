<HTML>
<HEAD><TITLE>Creating account</TITLE></HEAD>
<BODY>
<?php

//Connect to mysql database
require ('/home/student/strongr/public_html/.credentials.php');

$account = $_POST['num'];
$mysqli = new mysqli();
$mysqli->connect("localhost",$MYSQL_USER,$MYSQL_PW,"strongr");
if ($mysqli->errno){
	printf("Something went wrong connecting to the database, error: %s <br>",$mysqli->errno);
	exit();
}

//Create account
echo "Creating account: ";
echo $account;
echo "<br>";
//Check for existing account
$query = "SELECT * FROM strongr_BANKACCOUNTS WHERE id=$account";
$result = $mysqli ->query($query);
if ($mysqli->errno) {
	printf("Something went wrong with the database, error: %s <br>",$mysqli->error);
	exit();
}
//Try to find dupe
if ($result->fetch_row()){
	printf("Account %s already exisits, no new account created",$account);
	exit();
}
//No dupe found, thus make a new row
$query = "INSERT INTO strongr_BANKACCOUNTS(id,balence) VALUES ($account,0)";
$result = $mysqli->query($query);
printf("Account %s added!",$account);
?>
<p>
<a href="bank.html">Back to the home screen</a>
</BODY>
</HTML>
