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
	case 'submit':
		submitCredits();
	default:
		printInvalidAction($action);
		break;
}

function printInvalidAction($action) {
	printResponse(false, "Invalid action: $action");
}

function submitCredits() {
	$jsonString = post('credits');
	$credits = json_decode(stripslashes(html_entity_decode(rtrim($jsonString, '\0'))), true);
	$values = '';
	foreach ($credits as $key => $value) {
		$credit = (object)$value;
		if ($values != '') {
			$values .= ',';
		}
		$excess = floatval($credit->payamount) - floatval($credit->totalpayable);
		$values .= "(
			FROM_UNIXTIME($credit->datex/1000),
				$credit->salesid,
				$credit->customerid, 
				$credit->prno, 
				$credit->payid,
				$credit->payamount,
				$credit->totalpayable,
				$excess)";
	}
	if ($values == '') {
		printResponse(true, (object)[]);
	}
	$sql = "INSERT INTO 
				customercredit(
					datex, 
					salesid, 
					customerid, 
					prno, 
					payid,
					payamount,
					totalpayable,
					excess
				)
			VALUES $values";
	$response = query($sql);
	if (!$response) {
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