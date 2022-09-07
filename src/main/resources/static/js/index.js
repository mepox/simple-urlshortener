function onStart() {
	console.log("Page is ready.");
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
	        	
	        	var tbody = document.getElementById("URLTable").getElementsByTagName("tbody")[0];
	        	while(tbody.firstChild) {
	        		tbody.removeChild(tbody.firstChild);
	        	}
	        	
	        	var tinyURL = window.location + "u/";
	        	
	        	var toAdd = "";        	
				for (var i = 0; i < data.length; i++) {
					toAdd += "<tr><td>" + tinyURL + data[i].key + "</td>" +				
					"<td>" + data[i].url + "</td>" +
					"<td><input type='button' class='copyButton rowButton' onclick='toClipboardRowButton(this)' value='Copy'> " +
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
				var copyButton = "<input type='button' class='copyButton rowButton' onclick=copyTextToClipboard('" + tinyURL + "') value='Copy'>";				
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

function deleteURLRowButton(row) {
	// Gets the key
	var key = row.parentNode.parentNode.childNodes[0].firstChild.nodeValue;	

	// Gets the 4 digits from the end
	key = key.substr(key.length - 4);	
	
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

function updateStatus(text) {
	document.getElementById("statusText").innerHTML = text;	
}

function copyTextToClipboard(url) {	
	navigator.clipboard.writeText(url);
	var message = url + " copied to the clipboard.";
	updateStatus(message);		
}

function toClipboardRowButton(row) {
	var tinyURL = row.parentNode.parentNode.childNodes[0].firstChild.nodeValue 
	navigator.clipboard.writeText(tinyURL);
	
	var message = tinyURL + " copied to the clipboard.";	
	updateStatus(message);
}

function isValidHttpUrl(url) {	
	try {
	  var newURL = new URL(url);
	} catch (_) {
	  return false;  
	}

	return true;
}