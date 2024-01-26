/**
 * resources/js/ajaxMemo2.js 
 * alert("test");
 */

let xhr = null;
function sendRequest(url,param,callback,method,type){
	
	xhr = new XMLHttpRequest();
	let httpMethod = (method !== 'POST' && method !== 'post')?'GET':'POST';
	
	let httpParam = (param === null||param ==='')? null:param;
	
	var httpURL = url;
	
	if(httpMethod === 'GET' && httpParam !== null){
		httpURL = httpURL+"?"+httpParam;
	}

	xhr.onreadystatechange=callback;
	xhr.open(httpMethod,httpURL,true);
	
	
	if(type ==="json"){
		xhr.setRequestHeader("Content-Type","application/json");
	}else{
		xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	}
	console.log("httpMethod=>"+httpMethod+":httpParam"+httpParam);
	
	xhr.send(httpMethod === 'POST'? httpParam:null);
	
	
	
	
	
	
}