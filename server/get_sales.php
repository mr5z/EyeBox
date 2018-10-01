<?php

require_once('include/Config.php');

mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
mysql_select_db(DB_DATABASE);

mysql_set_charset('utf8');

if(get('key') == API_KEY) {
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
				totalamount,
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
				transaction,
				blocked,
				terms,
				printx,
				printlayout,
				receipt,
				audit,
				paymentmethod,
				notification,
				IFNULL(UNIX_TIMESTAMP(datemodified) * 1000, 0) datemodified,
				IFNULL(UNIX_TIMESTAMP(deliverydate) * 1000, 0) deliverydate,
				IFNULL(UNIX_TIMESTAMP(duedate) * 1000, 0) duedate,
				IFNULL(UNIX_TIMESTAMP(datedeposit) * 1000, 0) datedeposit,
				IFNULL(UNIX_TIMESTAMP(checkdate) * 1000, 0) checkdate,
				IFNULL(UNIX_TIMESTAMP(salesdate) * 1000, 0) salesdate,
				itemcount
			FROM
				sales
			WHERE
				branchno = $branch
			AND
				(totalamount - payamount) > 0
			AND
				duedate <> '0000-00-00'";
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