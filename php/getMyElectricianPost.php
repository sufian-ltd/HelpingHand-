<?php

	require "init.php";
	$type=$_GET["type"];
	$userId=$_GET["userId"];
	$sql="select * from electrician where type='$type' and userId='$userId'";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'type'=>$row['type'],'available' =>$row['available'],'experience' =>$row['experience'],
	  'details'=>$row['details'],'charge'=>$row['charge'],'division'=>$row['division'],'subDivision'=>$row['subDivision'],'latitude'=>$row['latitude'],'longitude'=>$row['longitude'],'userId'=>$row['userId'],'requestUserId'=>$row['requestUserId']));
	}
	echo json_encode($response);
	mysqli_close($con);

?>