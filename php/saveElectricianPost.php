<?php

	require "init.php";
	$type=$_GET["type"];
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

	$sql="insert into electrician (type,available,experience,details,charge,division,subDivision,latitude,longitude,userId,requestUserId) 
	values('$type','$available','$experience','$details','$charge','$division','$subDivision','$latitude','$longitude','$userId','$requestUserId');";
	if(mysqli_query($con, $sql)){
		$status="inserted";
	}
	else{
		$status="not inserted";
	}
	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>