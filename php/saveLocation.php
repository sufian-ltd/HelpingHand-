<?php

	require "init.php";
	
	$id=$_GET["id"];
	$latitude=$_GET["latitude"];
	$longitude=$_GET["longitude"];

	$sql="update users set latitude='$latitude',longitude='$longitude' where id='$id'";

	if(mysqli_query($con, $sql)){
		$status="update";
	}
	else{
		$status="not update";
	}

	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>