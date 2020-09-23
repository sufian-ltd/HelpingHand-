<?php

	require "init.php";

	$sql="select * from user";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'email' =>$row['email'],
	  'password'=>$row['password'],'lat'=>$row['lat'],'lon'=>$row['lon'],'status'=>$row['status']));
	}
	echo json_encode($response);
	mysqli_close($con);

?>