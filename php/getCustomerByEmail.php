<?php

	require "init.php";
	$email=$_GET["email"];
	$sql="select * from user where email='$email'";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'email' =>$row['email'],
	  'password'=>$row['password'],'lat'=>$row['lat'],'lon'=>$row['lon'],'status'=>$row['status'],'question'=>$row['question'],'answer'=>$row['answer']));
	}
	echo json_encode($response);
	mysqli_close($con);

?>