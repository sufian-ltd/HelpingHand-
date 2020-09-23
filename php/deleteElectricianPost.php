<?php

	require "init.php";
	$id=$_GET["id"];

	$sql="delete from electrician where id='$id'";
	if(mysqli_query($con, $sql)){
		$status="delete";
	}
	else{
		$status="not delete";
	}
	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>