<?php

	require "init.php";
	
	$email=$_GET["email"];
	$password=$_GET["password"];

	$sql="update user set password='$password' where email='$email'"; 
	if(mysqli_query($con, $sql)){
		$status="update";
	}
	else{
		$status="not update";
	}

	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>