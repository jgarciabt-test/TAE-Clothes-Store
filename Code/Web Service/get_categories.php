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

if(isset($_GET["main_category"])){
	
	$category = $_GET["main_category"];
	$result = mysql_query("SELECT cat_id, cat_name, cat_pic, MIN(prod_price) FROM TAE_STORE.categories JOIN TAE_STORE.products ON(cat_id = prod_cat) WHERE cat_main_cat LIKE '$category' GROUP BY cat_id");
	
	if(mysql_num_rows($result) > 0){
		$response["categories"] = array();
		
		while($row = mysql_fetch_array($result)){
			$element = array();
			$element["cat_id"] = $row["cat_id"];
			$element["cat_name"] = $row["cat_name"];
			$element["cat_lowest_price"] = $row["MIN(prod_price)"];
			$element["cat_pic"] = $row["cat_pic"];
			
			array_push($response["categories"],$element);
		}
		
		$response["success"] = 1;

	} else{
		$response["success"] = 0;
		$response["message"] = "No elements found";
	}
	
	echo json_encode($response);
}

?>