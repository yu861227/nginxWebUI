$(function() {
	if (getQueryString("over") == 'true') {
		layer.msg(exportStr.importSuccess);
	}
});

function dExport() {
	window.open(ctx + "/adminPage/export/dataExport")
}


function dImport() {
	$("#file").click();
}

function dImportOver() {
	var files = $('#file').prop('files');// 获取到文件列表
	if (files.length == 0) {
		layer.alert(exportStr.selectFile);
	} else {
		if (confirm(exportStr.confirm)) {
			$("#dataImport").submit();
		}
	}
}

function lExport() {
	window.open(ctx + "/adminPage/export/logExport")
}