/**
 * 
 */
 
var logoutForm = document.getElementById('logoutForm');
var logoutDiv = document.getElementById('logoutBtn');

logoutDiv.addEventListener("click", function(event) {
	event.preventDefault();

	response = fetch(updateLoginDateURL, {
		method: 'GET'
	})
		.then(res => res.text())
		.catch(error => console.error('Error:', error))
		.then(response => {
			if (response !== 'Update finished') {
				console.log('Update long in date failed!');
			}
			logoutForm.submit();
		})
});
