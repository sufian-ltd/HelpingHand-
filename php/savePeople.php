<?php

require "init.php";
$name=$_GET["name"];
$email=$_GET["email"];
$password=$_GET["password"];
$contact=$_GET["contact"];
$address=$_GET["address"];
$question=$_GET["question"];
$answer=$_GET["answer"];
$latitude=0;
$longitude=0;

$sql="select * from users where email = '$email' and password = '$password'";
$result=mysqli_query($con,$sql);
if(mysqli_num_rows($result)>0){
	$status="exist";
}
else{
	$sql="insert into users (name,email,password,contact,address,latitude,longitude) 
	values('$name','$email','$password','$contact','$address','$latitude','$longitude','$question','$answer');";
	if(mysqli_query($con, $sql)){
		$status="inserted";
	}
	else{
		$status="not inserted";
	}
}
echo json_encode(array("response"=>$status));
mysqli_close($con);

?>