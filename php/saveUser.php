<?php

require "init.php";
$email=$_GET["email"];
$password=$_GET["password"];
$lat=$_GET["lat"];
$lon=$_GET["lon"];
$status=$_GET["status"];
$question=$_GET["question"];
$answer=$_GET["answer"];

$sql="select * from user where email = '$email' and password = '$password'";
$result=mysqli_query($con,$sql);
if(mysqli_num_rows($result)>0){
	$status="exist";
}
else{
	$sql="insert into user (email,password,lat,lon,status) 
	values('$email','$password','$lat','$lon','$status','$question','$answer');";
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