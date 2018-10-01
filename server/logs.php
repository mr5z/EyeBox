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
	case 'upload':
		uploadLog();
		break;
	case 'download':
		downloadLog();
		break;
	default:
		printInvalidAction($action);
		break;
}

function uploadLog() {
	$userId = get('userId');
	$message = get('message');
	$sql = "INSERT INTO logs(userId, message, logTime) VALUES($userId, '$message', NOW())";
	$result = query($sql);
	$success = $result != null;
	printResponse($success, ($success ? (object)[] : false), $result != null ? 1 : 0);
}

function downloadLog() {
	$userId = get('userId');
	$page = get('page');
	$pageSize = get('pageSize');
	$sql = "SELECT 
				id,
				message,
				CONVERT_TZ(logTime,'-07:00','+08:00') AS logTime
			FROM logs
			WHERE userId = $userId
			ORDER BY id
			LIMIT $page,$pageSize";
	$result = queryList($sql);
	printResponse($result != null, $result, count($result));
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

function printInvalidAction($action) {
	printResponse(false, "Invalid action: $action");
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