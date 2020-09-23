<?php

	require "init.php";
	
	$email=$_GET["email"];
	$password=$_GET["password"];
	$lat=$_GET["lat"];
	$lon=$_GET["lon"];
	$status=$_GET["status"];

	$sql="update user set email='$email',password='$password',lat='$lat',lon='$lon' ,status='$status' where email='$email' and password='$password'"; 
	if(mysqli_query($con, $sql)){
		$status="update";
	}
	else{
		$status="not update";
	}

	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>