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

if(isset($_GET["category"])){
	
	$category = $_GET["category"];
	$result = mysql_query("SELECT * FROM TAE_STORE.products JOIN TAE_STORE.picture ON (prod_main_pic = pic_id) WHERE `prod_cat` = '$category'");
	
	if(mysql_num_rows($result) > 0){
		$response["products"] = array();
		
		while($row = mysql_fetch_array($result)){
			$element = array();
			$element["prod_id"] = $row["prod_id"];
			$element["prod_name"] = $row["prod_name"];
			$element["prod_desc"] = $row["prod_desc"];
			$element["prod_detail"] = $row["prod_detail"];
			$element["prod_price"] = $row["prod_price"];
			$element["prod_offer"] = $row["prod_offer"];
			$element["pic_url"] = $row["pic_url"];
			$element["prod_cat"] = $row["prod_cat"];
			
			array_push($response["products"],$element);
		}
		
		$response["success"] = 1;

	} else{
		$response["success"] = 0;
		$response["message"] = "No elements found";
	}
	
	echo "[",json_encode($response),"]";
}

?>