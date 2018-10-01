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
		getPayments();
		break;
	case 'submit':
		submitPayments();
		break;
	case 'status':
		getPaymentsStatus();
		break;
	case 'sync':
		syncPaymentsStatus();
		break;
	default:
		printInvalidAction($action);
		break;
}

function printInvalidAction($action) {
	printResponse(false, "Invalid action: $action");
}

function syncPaymentsStatus() {
	$paymentIds = get('paymentIds');
	$sql = "SELECT idcode, astatus FROM payments WHERE idcode IN($paymentIds)";
	$response = queryList($sql);
	if ($response) {
		$success = true;
		$data = $response;
	}
	else {
		$success = false;
		$data = mysql_error();
	}
	printResponse($success, $data);
}

function submitPayments() {
	$jsonString = post('payments');
	$payments = json_decode(stripslashes(html_entity_decode(rtrim($jsonString, '\0'))), false, 512, JSON_BIGINT_AS_STRING);
	$values = '';
	foreach ($payments as $key => $p) {
		if ($values != '') {
			$values .= ',';
		}
		$values .= "
		(
			$p->idcode,
			NOW(),
			NULLIF($p->bankname, -1), 
			(CASE WHEN $p->checkdate = 0 THEN NULL ELSE FROM_UNIXTIME($p->checkdate/1000) END),
			'$p->checkno', 
			$p->amount, 
			$p->receivedby, 
			'$p->delall', 
			'$p->userlogs', 
			'$p->salesid', 
			$p->terms, 
			'$p->remarks', 
			'$p->checkname', 
			$p->branchno, 
			(CASE WHEN $p->datedeposited = 0 THEN NULL ELSE FROM_UNIXTIME($p->datedeposited/1000) END),
			'$p->astatus', 
			$p->customerid, 
			'$p->drno', 
			'$p->prno', 
			(CASE WHEN $p->salesdate = 0 THEN NULL ELSE FROM_UNIXTIME($p->salesdate/1000) END),
			'$p->orno',  
			(CASE WHEN $p->duedate = 0 THEN NULL ELSE FROM_UNIXTIME($p->duedate/1000) END),
			(CASE WHEN $p->checkdays = 0 THEN NULL ELSE FROM_UNIXTIME($p->checkdays/1000) END),
			'$p->controlno', 
			'$p->deposited',
			$p->debit,
			$p->validatedby, 
			$p->receiptlayout
		)";
	}
	$sql = "INSERT INTO payments(
				idcode,
				datex, 
				bankname, 
				checkdate, 
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
				datedeposited, 
				astatus, 
				customerid, 
				drno, 
				prno, 
				salesdate,
				orno, 
				duedate, 
				checkdays, 
				controlno, 
				deposited, 
				debit, 
				validatedby, 
				receiptlayout
			)
			VALUES
				$values
			ON DUPLICATE KEY UPDATE 
				amount = VALUES(amount)";
	if ($values == '') {
		printResponse(true, (object)[]);
	}
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

function getPayments() {
	$branch = get('branch');
	$userId = get('userId');
	$sql = "SELECT
				idcode,
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
				controlno, 
				deposited, 
				debit, 
				validatedby, 
				receiptlayout,
				IFNULL(UNIX_TIMESTAMP(datex) * 1000, 0) datex,
				IFNULL(UNIX_TIMESTAMP(checkdate) * 1000, 0) checkdate,
				IFNULL(UNIX_TIMESTAMP(checkdays) * 1000, 0) checkdays,
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
		$totalCount = 0;
	}
	else {
		$success = true;
		$data = $response;
		$totalCount = 1; // lol
	}
	printResponse($success, $data, $totalCount);
}

function getPaymentsStatus() {
	$customerId = get('clientId');
	$sql = "SELECT idcode, astatus FROM payments WHERE customerid = $customerId";
	$result = queryList($sql);
	if (!$result) {
		$success = false;
		$data = mysql_error();
	}
	else {
		$success = true;
		$data = $result;
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