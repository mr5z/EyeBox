<?php

require_once('include/Config.php');

const STATUS_SUCCESS = "success";
const STATUS_FAILED = "failed";
const UPLOAD_DIRECTORY = "files";
const ONE_MB = 1024 * 1024;

mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
mysql_select_db(DB_DATABASE);

mysql_set_charset('utf8');

$action = get('action');
if ($action == '') {
	$action = post('action');
}

switch($action) {
	case 'submit':
		submitVisits();
		break;
	case 'upload':
		uploadVisits();
		break;
	default:
		printInvalidAction($action);
		break;
}

function printInvalidAction($action) {
	printResponse(false, "Invalid action: $action");
}

function submitVisits() {
	$jsonString = post('visits');
	$visits = json_decode(stripslashes(html_entity_decode(rtrim($jsonString, '\0'))), true);
	$values = '';
	foreach ($visits as $key => $v) {
		$v = (object)$v;
		if ($values != '') {
			$values .= ',';
		}
		$values .= "
		(
			FROM_UNIXTIME($v->date/1000),
			FROM_UNIXTIME($v->timex/1000),
			$v->agent,
			$v->client,
			'$v->signature',
			$v->filesizex,
			'$v->filetypex',
			$v->filewidth,
			$v->fileheight,
			'$v->filenamex'
		)";
	}
	$sql = "INSERT IGNORE INTO 
				tbl_visits(
					date,
					timex,
					agent, 
					client, 
					signature,
					filesizex,
					filetypex,
					filewidth,
					fileheight,
					filenamex
				)
			VALUES $values";

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

function uploadVisits() {
	$visitId = post('visitId');
    $file = files('signature');
    $filename = '';
    $hasFile = false;
    $link = '';
    if ($file != null) {
        $filename = unique($file['name']);
        $link = linkify($filename);
        fileUpload($file, $filename);
        $hasFile = true;
    }
    if ($hasFile) {
    	$sql = "UPDATE
    				tbl_visits
    			SET
    				filenamex = '$filename',
    				timex = NOW()
				WHERE
					id = $visitId";
    	$response = query($sql);
    	$success = mysql_affected_rows() >= 0;
    	printResponse($success, $success ? ((object)[]) : mysql_error(), $success ? 1 : 0);
    }
    else {
    	printResponse(false, null, 0);
    }
}

function fileUpload($file, $newFilename) {
    $error = $file['error'];
    $size = $file['size'];
    if ($error == UPLOAD_ERR_OK && $size < ONE_MB * 5) {
        $tmp_name = $file['tmp_name'];
        $serverLocation = UPLOAD_DIRECTORY . '/' . $newFilename;
        move_uploaded_file($tmp_name, $serverLocation);
    }
}

function activeFile() {
    $currentPath = $_SERVER['PHP_SELF'];
    $path = pathinfo($currentPath);
    return $path['basename'];
}

function unique($name) {
    return 'eyebox' . time() . '_' . $name;
}

function linkify($filename) {
    $currentPath = $_SERVER['PHP_SELF'];
    $path = pathinfo($currentPath);
    $currentDir = $path['dirname'];
    return $_SERVER['SERVER_NAME'] . "$currentDir/" . UPLOAD_DIRECTORY . "/$filename";
}

function files($key) {
    if (!empty($_FILES) && isset($_FILES[$key])) {
        $file = $_FILES[$key];
        $error = $file['error'];
        if ($error == UPLOAD_ERR_OK) {
            return $file;
        }
    }
    return null;
}