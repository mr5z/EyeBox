<?php

include "include/Config.php";

if((isset($_POST['key']) && API_KEY==$_POST['key']) || (isset($_GET['key']) && API_KEY==$_GET['key'])){

	mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
	mysql_select_db(DB_DATABASE);

	header('Access-Control-Allow-Origin: *');
	header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");
	header('Access-Control-Allow-Methods: GET, POST, PUT');
	header("Content-Type: application/json; charset=UTF-8");

	mysql_query("SET NAMES 'utf8'");
	mysql_query("SET CHARACTER SET 'utf8'");

	echo '{"tables": [';

		$table_name = "products";
		echo '{"'.$table_name.'": [';
			$result = mysql_query("SELECT 
									products.idcode, 
									products.generic, 
									products.namex, 
									products.units, 
									products.price, 
									products.byx,
									(SUM(stocks.quantitystocks)) AS soh 
									FROM products, stocks 
									WHERE products.idcode = stocks.productidstocks 
									AND products.hiddenx = '0' 
									AND products.productclass = 'Wholesale' 
									AND stocks.branchno = '".$_GET['branch']."' 
									GROUP BY products.idcode 
									ORDER BY products.generic ASC");
			if (!$result) {
				echo 'Could not run query: ' . mysql_error();
				exit;
			}
			if (mysql_num_rows($result) > 0) {
				$count = 0;
				while ($row = mysql_fetch_assoc($result)) {
					$obj = json_encode($row);
					if($count==0){
						echo $obj;
					}else{
						echo ",".$obj;
					}
					$count = $count + 1;
				}
			}
		echo ']}';
	

	echo ']}';
	
}else{
	echo "INVALID KEY!";
}
?>