let setIconFlag1 = true;
let setIconFlag2 = true;
let burgerFlag = true;
const loginInput = document.querySelectorAll('.login__input');
const loginForm = document.querySelector('.login__form');
const headerNav = document.querySelector('.header__nav');

function setIcon(event) {
	const passIcon = event.target;
	if (passIcon.classList.contains('second-icon')) {
		const inputPass = document.querySelector('.input-pass-confirm');
		if (setIconFlag2) {
			passIcon.setAttribute('src', 'style/icons/eye.svg');
			inputPass.setAttribute('type', 'text');
		} else {
			passIcon.setAttribute('src', 'style/icons/eye-hide.svg');
			inputPass.setAttribute('type', 'password');
		}
		setIconFlag2 = !setIconFlag2;
	} else {
		const inputPass = document.querySelector('.input-pass');
		if (setIconFlag1) {
			passIcon.setAttribute('src', 'style/icons/eye.svg');
			inputPass.setAttribute('type', 'text');
		} else {
			passIcon.setAttribute('src', 'style/icons/eye-hide.svg');
			inputPass.setAttribute('type', 'password');
		}
		setIconFlag1 = !setIconFlag1;
	}
}
function burgerActive() {
	if(burgerFlag){
		headerNav.classList.add("header__nav-active");
	} else {
		headerNav.classList.remove("header__nav-active");
	}
	burgerFlag = !burgerFlag;
}

function sendLogin(event) {
	event.preventDefault();
	const formData = new FormData(loginForm);

	fetch('/auth/login', {
        method: 'POST',
        body: formData
    })
    .then(response => {
    	if (response.status === 401) {
    		loginInput[0].classList.add("input-red");
			loginInput[1].classList.add("input-red");
			passIcon.classList.add("pass-icon-red");
    	}
    	if (response.ok) {
    		window.location.href = '/test';
    	}
    })
    .then(data => {
        console.log(data);
    })
    .catch(error => {
        console.log(error);
    });
}

function sendSignup(event) {
	event.preventDefault();
	if (loginInput[1].value !== loginInput[2].value) {
		loginInput[1].classList.add("input-red");
		loginInput[2].classList.add("input-red");
		return;
	}
	const formData = new FormData(loginForm);


	fetch('/auth/signup', {
        method: 'POST',
        body: formData
    })
    .then(response => {
    	if (response.status === 401) {
    		loginInput[0].classList.add("input-red");
			loginInput[1].classList.add("input-red");
			passIcon.classList.add("pass-icon-red");
    	}
    	if (response.ok) {
    		window.location.href = '/test';
    	}
    })
    .then(data => {
        console.log(data);
    })
    .catch(error => {
        console.log(error);
    });
}