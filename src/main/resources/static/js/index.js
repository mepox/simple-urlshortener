function onStart() {
	console.log("Page is ready.");
	updateStatus("Ready.");
	showURLs();
}

function showURLs() {
	var url = window.location + "urls/all";
    var xhr = new XMLHttpRequest();

	xhr.open("GET", url);
    xhr.send();

    xhr.onreadystatechange = function() {
        if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS    	
	        	var data = JSON.parse(this.responseText);	        	       	
	        	
	        	var tbody = document.getElementById("urlTable").getElementsByTagName("tbody")[0];
	        	while(tbody.firstChild) {
	        		tbody.removeChild(tbody.firstChild);
	        	}
	        	
	        	var tinyURL = window.location + "u/";
	        	
	        	var toAdd = "";        	
				for (var i = 0; i < data.length; i++) {
					toAdd += "<tr><td class='tinyUrl'>" + tinyURL + data[i].key + "</td>" +				
					"<td class='originalUrl'>" + data[i].url + "</td>" +
					"<td class='actions'>" +
					"<input type='button' class='visitButton rowButton' onclick='visitRowButton(this)' value='Visit'> " +
					"<input type='button' class='copyButton rowButton' onclick='copyRowButton(this)' value='Copy'> " +
					"<input type='button' class='deleteButton rowButton' onclick='deleteURLRowButton(this)' value='Delete'></td></tr>";
				}            
				
				tbody.innerHTML = toAdd;				
			} else {
				// ERROR
				updateStatus("Couldn't get the URLs from the server.");
			}
        }
    };
}


function addURL() {	
	var newURL = document.forms["addURLForm"]["newURL"].value;
	
	newURL = newURL.trim();
	
	if (!isValidHttpUrl(newURL)) {
		updateStatus("Not a valid HTTP URL! Use a full HTTP URL e.g.: http://www.google.com");		
		return;
	}
	
	var url = window.location + "urls/add";
	var xhr = new XMLHttpRequest();
	
	xhr.open("POST", url);	
	xhr.send(newURL);
		
	xhr.onreadystatechange = function() {
	    if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS
				var tinyURL = window.location + "u/" + this.responseText;
				var copyButton = 	" <input type='button' class='visitButton rowButton' onclick=visit('" + tinyURL + "') value='Visit'> " +
									"<input type='button' class='copyButton rowButton' onclick=copyTextToClipboard('" + tinyURL + "') value='Copy'>";				
				var message = "New Short URL has been created: " + tinyURL + " " + copyButton;
				
				updateStatus(message);
				showURLs();
			} else {
				// ERROR
				updateStatus(this.responseText);
			}
		}
	}
	
	// Clears the form	
	document.forms["addURLForm"]["newURL"].value = "";
}

function visitRowButton(row) {
	var tinyUrl = row.parentNode.parentNode.childNodes[0].firstChild.nodeValue;
	visit(tinyUrl);
}

function visit(url) {
	window.open(url, '_blank').focus();
}

function deleteURLRowButton(row) {
	// Get the url
	var tinyUrl = row.parentNode.parentNode.childNodes[0].firstChild.nodeValue;
	
	// Split into array
	var temp = tinyUrl.split("/");
	
	// Get the key
	var key = temp[temp.length-1];
	
	var url = window.location + "urls/delete/" + key;
	var xhr = new XMLHttpRequest();
	
	xhr.open("DELETE", url);	
	xhr.send(key);
	
	xhr.onreadystatechange = function() {
        if (this.readyState == XMLHttpRequest.DONE) {
			if (this.status == 200) {
				// SUCCESS
        		showURLs();
        	} else {
				// ERROR				
			}
			updateStatus(this.responseText);
        }	        	
	}
}

function copyRowButton(row) {
	var tinyURL = row.parentNode.parentNode.childNodes[0].firstChild.nodeValue 
	copyTextToClipboard(tinyURL);
}

function copyTextToClipboard(url) {	
	navigator.clipboard.writeText(url);
	var message = url + " copied to the clipboard.";
	updateStatus(message);		
}

function updateStatus(text) {
	document.getElementById("statusText").innerHTML = "<b>Status: </b>" + text;	
}

function isValidHttpUrl(url) {	
	try {
	  var newURL = new URL(url);
	} catch (_) {
	  return false;  
	}

	return true;
}