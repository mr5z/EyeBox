<?php

require_once('include/Config.php');

mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
mysql_select_db(DB_DATABASE);

mysql_set_charset('utf8');

$action = get('action');
if ($action == '') {
	$action = post('action');
}

switch($action) {
	case 'get':
		getSalesReport();
		break;
	default:
		printInvalidAction($action);
		break;
}

function getSalesReport() {
	$sql = "SELECT 
				idcode, titlex, header, footer, TO_BASE64(logo) AS logo
			FROM
				salesreport
			WHERE 
				header IS NOT NULL
			AND 
				footer IS NOT NULL";
	$result = queryList($sql);
	if ($result) {
		printResponse(true, $result, count($result));
	}
	else {
		printResponse(false, mysql_error(), 0);
	}
}

function printInvalidAction($action) {
	printResponse(false, "Invalid action: $action");
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