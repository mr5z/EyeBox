<?php

require_once('include/Config.php');

mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
mysql_select_db(DB_DATABASE);

mysql_set_charset('utf8');

if(get('key') == API_KEY) {
	$branch = get('branch');
	$userId = get('userId');
	$sql = "SELECT
				bankname, 
				checkno, 
				amount, 
				receivedby, 
				delall, 
				userlogs,
				salesid, 
				terms, 
				remarks,
				checkname,
				branchno, 
				astatus, 
				customerid, 
				drno, 
				prno, 
				orno, 
				checkdays, 
				controlno, 
				deposited, 
				debit, 
				validatedby, 
				receiptlayout,
				IFNULL(UNIX_TIMESTAMP(datex) * 1000, 0) datex,
				IFNULL(UNIX_TIMESTAMP(checkdate) * 1000, 0) checkdate,
				IFNULL(UNIX_TIMESTAMP(datedeposited) * 1000, 0) datedeposited,
				IFNULL(UNIX_TIMESTAMP(salesdate) * 1000, 0) salesdate,
				IFNULL(UNIX_TIMESTAMP(duedate) * 1000, 0) duedate
			FROM
				payments
			WHERE
				branchno = $branch
			AND
				receivedby = $userId";
	$response = queryList($sql);
	if (!$response) {
		$success = false;
		$data = mysql_error();
	}
	else {
		$success = true;
		$data = $response;
	}
	printResponse($success, $data);
}
else {
	printResponse((object)[
		'success' => false,
		'data' => 'invalid key'
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

function printResponse($success, $data) {
	header('Content-Type: application/json;charset=UTF-8');
	print json_encode((object)[
		'success' => $success,
		'data' => $data
	]);
	exit;
}