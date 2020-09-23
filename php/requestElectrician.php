<?php

	require "init.php";
	
	$id=$_GET["id"];
	$requestUserId=$_GET["requestUserId"];

	$sql="update electrician set requestUserId='$requestUserId' where id='$id'";

	if(mysqli_query($con, $sql)){
		$status="update";
	}
	else{
		$status="not update";
	}

	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>