<HTML>
<HEAD><TITLE>Depositing to account</TITLE></HEAD>
<BODY>
<?php

//Connect to mysql database
require ('/home/student/strongr/public_html/.credentials.php');

$account = $_POST['num'];
$ammo = $_POST['ammount'];
$mysqli = new mysqli();
$mysqli->connect("localhost",$MYSQL_USER,$MYSQL_PW,"strongr");
if ($mysqli->errno){
        printf("Something went wrong connecting to the database, error: %s <br>",$mysqli->errno);
        exit();
}

//Deposit to account

//Check for existing account
$query = "SELECT balence FROM strongr_BANKACCOUNTS WHERE id=$account";
$result = $mysqli ->query($query);
if ($mysqli->errno) {
        printf("Something went wrong with the database, error: %s <br>",$mysqli->error);
        exit();
}

//Find and update row
$row = $result->fetch_row();
$ammount = $row[0];
$newBalence = $ammount + $ammo;
if ( !($ammount=="") ){
	$mysqli->query("UPDATE strongr_BANKACCOUNTS SET balence=$newBalence");
        printf("Account %s now contains $%s, deposit complete!",$account,$newBalence);
}else{
        printf("Account %s does not exist!",$account);
        exit();
}
?>
<p>
<a href="bank.html">Back to the home screen</a>
</BODY>
</HTML>

