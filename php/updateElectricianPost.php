<?php

	require "init.php";
	
	$id=$_GET["id"];
	$available=$_GET["available"];
	$experience=$_GET["experience"];
	$details=$_GET["details"];
	$charge=$_GET["charge"];
	$division=$_GET["division"];
	$subDivision=$_GET["subDivision"];
	$latitude=$_GET["latitude"];
	$longitude=$_GET["longitude"];
	$userId=$_GET["userId"];
	$requestUserId=$_GET["requestUserId"];

	$sql="update electrician set available='$available',experience='$experience',details='$details',charge='$charge',division='$division',subDivision='$subDivision',latitude='$latitude',longitude='$longitude',userId='$userId',requestUserId='$requestUserId' where id='$id'";

	if(mysqli_query($con, $sql)){
		$status="update";
	}
	else{
		$status="not update";
	}

	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>