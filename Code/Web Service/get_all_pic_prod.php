<?php

$response = array();

//require_once __DIR__ . '/db_connect.php';
//$db = new DB_CONNECT();

$conn_str = mysql_connect('localhost', 'root', '');
if (!$conn_str) {
  die('Not connected  to the database: ' . mysql_error());
}

$db_selected = mysql_select_db('TAE_STORE', $conn_str);
if (!$db_selected) {
  die ("Can\'t use your_database_name : " . mysql_error());
}

if(isset($_GET["prod_id"])){
	
	$product = $_GET["prod_id"];
	$result = mysql_query("SELECT picture.pic_url FROM picture JOIN prod_pic ON(pic_id = pic) where prod_pic.prod = $product");
	
	if(mysql_num_rows($result) > 0){
		$response["pictures"] = array();
		
		while($row = mysql_fetch_array($result)){
			$element = array();
			$element["pic_url"] = $row["pic_url"];
			array_push($response["pictures"],$element);
		}
		
		$response["success"] = 1;

	} else{
		$response["success"] = 0;
		$response["message"] = "No elements found";
	}
	
	echo "[",json_encode($response)."]";
}

?>