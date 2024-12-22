function burgerActive() {
	document.querySelector('.burger__frame').classList.add('active');
}

function hideBurger(event) {
	if (event.target === event.currentTarget) {
		document.querySelector('.burger__frame').classList.remove('active');
	}
}