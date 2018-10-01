<?php

require_once('include/Config.php');

mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
mysql_select_db(DB_DATABASE);

mysql_set_charset('utf8');

$action = get('action');

switch ($action) {
	case 'get':
		getSalesByBranch();
		break;
	default:
		printInvalidAction($action);
		break;
}

function getSalesByBranch() {
	$branch = get('branch');
	$sql = "SELECT
				salesidx,
				customerid,
				companyname,
				agent,
				checkedby,
				deliveredby,
				adjustments,
				adjustmentamount,
				userlogs,
				delall,
				drno,
				sino,
				prno,
				orno,
				amount,
				ROUND(totalamount - payamount, 2) AS totalamount,
				salestype,
				salesaccount,
				remarks,
				origtotal,
				branchno,
				debit,
				payamount,
				balance,
				syncx,
				modified,
				transaction
			FROM
				sales
			WHERE
				branchno = $branch
			AND
				(totalamount - payamount) > 0
			AND
				duedate <> '0000-00-00'";
	$result = queryList($sql);
	if ($result) {
		$success = true;
		$data = $result;
	}
	else {
		$success = false;
		$data = mysql_error();
	}
	printResponse($success, $data, 1); // TODO too lazy to find out how to get the size
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