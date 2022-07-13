
var updateEmail = document.getElementById('updateEmail');
var updateEmailBtn = document.getElementById('updateEmailBtn');
var updatePassword = document.getElementById('updatePassword');
var updatePasswordBtn = document.getElementById('updatePasswordBtn');
var updateTaskNum = document.getElementById('updateTaskNum');
var updateTaskNumBtn = document.getElementById('updateTaskNumBtn');

var messageDiv = document.getElementById('messageDiv');
var returnMessageFromServer = document.getElementById('returnMessageFromServer');

updateEmailBtn.addEventListener('click', function() {
	response = fetch(updateEmailURL, {
		method: 'POST',
		body: updateEmail.value,
		headers: new Headers({
			'X-CSRF-TOKEN': crsfToken
		})
	})
		.then(res => res.text())
		.catch(error => console.error('Error:', error))
		.then(response => {
			returnMessageFromServer.innerHTML = response;
			messageDiv.classList.remove('d-none');
			console.log(response);
		});
});

updatePasswordBtn.addEventListener('click', function() {
	if (updatePassword.value !== null && updatePassword.value !== ''){
		response = fetch(updatePasswordURL, {
			method: 'POST',
			body: updatePassword.value,
			headers: new Headers({
				'X-CSRF-TOKEN': crsfToken})
		})
			.then(res => res.text())
			.catch(error => console.error('Error:', error))
			.then(response => {
				returnMessageFromServer.innerHTML = response;
				messageDiv.classList.remove('d-none');
				console.log(response);
			});
		updatePassword.value = '';
	}
	else {
		returnMessageFromServer.innerHTML = "Please enter new password.";
		messageDiv.classList.remove('d-none');
	}
});

updateTaskNumBtn.addEventListener('click', function() {
	response = fetch(changeTaskNumInHomePageURL + '?taskNum=' + updateTaskNum.value, {
		method: 'GET',
	})
		.then(res => res.text())
		.catch(error => console.error('Error:', error))
		.then(response => {
			returnMessageFromServer.innerHTML = response;
			messageDiv.classList.remove('d-none');
			console.log(response);
		});
});