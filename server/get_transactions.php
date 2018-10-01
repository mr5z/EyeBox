<?php

require_once('include/Config.php');

mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
mysql_select_db(DB_DATABASE);

mysql_set_charset('utf8');

if(get('key') == API_KEY) {
	$branch = get('branch');
	$sql = "SELECT 
				customers.customersid, 
				customers.customername,
				customers.address, 
				(SUM(sales.totalamount) - SUM(sales.payamount)) AS totalcredit 
			FROM 
				customers, sales
			WHERE
				customers.customersid = sales.customerid
			AND
				customers.branchlink = $branch
			AND
				sales.branchno = $branch
			AND
				sales.duedate <> '0000-00-00'
			AND
				(sales.totalamount - sales.payamount) > 0
			GROUP BY
				customers.customersid
			ORDER BY
				sales.salesidx";
	$response = queryList($sql);
	printResponse($response);
}
else {
	printResponse((object)[
		'status' => 'error',
		'message' => 'invalid key'
	]);
}

function queryList($sql) {
	$result = mysql_query($sql);
	if ($result && mysql_num_rows($result) > 0) {
		$rows = [];
		while ($row = mysql_fetch_object($result)) {
			$rows[] = $row;
		}
		return $rows;
	}
	return false;
}

function get($key) {
	return isset($_GET[$key]) ? escape($_GET[$key]) : '';
}

function escape($value) {
    return mysql_real_escape_string($value);
}

function printResponse($response) {
	header('Content-Type: application/json;charset=UTF-8');
	print json_encode($response);
}