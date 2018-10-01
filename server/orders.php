<?php

require_once('include/Config.php');

mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
mysql_select_db(DB_DATABASE);

mysql_set_charset('utf8');

$action = post('action');

switch ($action) {
	case 'submit':
		submitOrders();
		break;
	default:
		printInvalidAction($action);
		break;
}

function submitOrders() {
	$jsonString = post('orders');

	if (empty($jsonString))
		printResponse(false, null);

	$orders = json_decode(stripslashes(html_entity_decode(rtrim($jsonString, '\0'))), true);
	$values = '';
	foreach ($orders as $key => $value) {
		$value = (object)$value;
		if ($values != '')
			$values .= ',';

		$values .= "
		(
			$value->quantity,
			$value->employeesid,
			$value->productid,
			$value->orderfrom,
			$value->anybrand,
			(CASE WHEN $value->date = 0 THEN NULL ELSE FROM_UNIXTIME($value->date/1000) END)
		)";
	}

	if ($values == '') {
		printResponse(true, (object)[]);
	}

	$sql = "INSERT INTO
				salesorder(
					quantity, 
					employeesid, 
					productid,
					orderfrom,
					anybrand,
					`date`
				) 
			VALUES $values";

	$result = query($sql);
	if (!$result) {
		$success = false;
		$data = mysql_error();
	}
	else {
		$success = true;
		$data = (object)[];
	}
	printResponse($success, $data);
}

function query($sql) {
	return mysql_query($sql);
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

function post($key) {
	return isset($_POST[$key]) ? escape($_POST[$key]) : '';
}

function escape($value) {
    return mysql_real_escape_string($value);
}

function printResponse($success, $data, $totalCount = 0) {
	header('Content-Type: application/json;charset=UTF-8');
	print json_encode((object)[
		'success' => $success,
		'data' => $data,
		'totalCount' => $totalCount
	]);
	exit;
}

function printInvalidAction($action) {
	printResponse(false, "Invalid action: $action");
}