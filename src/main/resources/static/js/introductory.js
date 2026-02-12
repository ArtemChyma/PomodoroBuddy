const startButton = document.querySelector('.starting-button');
const modalOverlay = document.getElementById('modalOverlay');
const closeModal = document.getElementById('closeModal');
const wrapper = document.querySelector('.wrapper');
const togglePassword = document.getElementById('togglePassword');
const passwordInput = document.getElementById('password');
const showRegister = document.getElementById('showRegister');

const registerModalOverlay = document.getElementById('registerModalOverlay');
const registerCloseModal = document.getElementById('registerCloseModal');
const showLogin = document.getElementById('showLogin');
const fieldErrors = document.querySelector('.fieldErrors');
const fieldErrorsLogin = document.querySelector('.fieldErrorsLogin');
const registerButton = document.getElementById('register-submit-btn');
const fieldLoginError = document.querySelector('.fieldErrorsLogin');


console.log(fieldLoginError);
if (fieldLoginError) {
    openLoginModalWindow();
}

if (startButton) {
    startButton.addEventListener('click', function (e) {
        e.preventDefault();
        openLoginModalWindow();
    });
}

if (closeModal && registerCloseModal) {
    closeModal.addEventListener('click', closeLogInModalWindow);
    registerCloseModal.addEventListener('click', closeRegisterModalWindow);
}

if (modalOverlay && registerModalOverlay) {
    modalOverlay.addEventListener('click', function (e) {
        if (e.target === modalOverlay) {
            closeLogInModalWindow();
        }
    });
    registerModalOverlay.addEventListener('click', function (e) {
        if (e.target === registerModalOverlay) {
            closeRegisterModalWindow();
        }
    });
}

document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape' && modalOverlay.classList.contains('active')) {
        closeLogInModalWindow();
    } else if (e.key === 'Escape' && registerModalOverlay.classList.contains('active')) {
        closeRegisterModalWindow();
    }
});


if (togglePassword && passwordInput) {
    togglePassword.addEventListener('click', function () {
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            togglePassword.textContent = '👁️‍🗨️';
        } else {
            passwordInput.type = 'password';
            togglePassword.textContent = '👁';
        }
    });
}


const loginForm = document.getElementById('loginModalForm');


const registerForm = document.getElementById('registerModalForm');

if (showRegister) {
    showRegister.addEventListener('click', function (e) {
        e.preventDefault();

        openRegisterModalWindow();
    });
}

if (showLogin) {
    showLogin.addEventListener('click', function (e) {
        e.preventDefault();

        openLoginModalWindow();

    });
}


function closeLogInModalWindow() {

    wrapper.classList.remove('blurred');

    modalOverlay.classList.remove('active');


    document.body.style.overflow = '';


    if (loginForm) {
        loginForm.reset();
    }


    if (passwordInput && togglePassword) {
        passwordInput.type = 'password';
        togglePassword.textContent = '👁';
    }
}

function closeRegisterModalWindow() {
    wrapper.classList.remove('blurred');

    registerModalOverlay.classList.remove('active');

    document.body.style.overflow = '';

    if (registerForm) {
        registerForm.reset();
        if (registerButton.textContent === "Completed") {
            registerButton.textContent = "Register";
            registerButton.style.background = "linear-gradient(135deg, #667eea 0%, #764ba2 100%)"
        }
    }
    if (fieldErrors) {
        fieldErrors.classList.remove("active");
        fieldErrors.textContent = "";
    }

}

function openLoginModalWindow() {

    if (registerModalOverlay.classList.contains('active')) {
        closeRegisterModalWindow();
    }

    wrapper.classList.add('blurred');

    modalOverlay.classList.add('active');

    document.body.style.overflow = 'hidden';

    setTimeout(() => {
        document.getElementById('email').focus();
    }, 300);
}

function openRegisterModalWindow() {
    if (modalOverlay.classList.contains('active')) {
        closeLogInModalWindow();
    }

    wrapper.classList.add('blurred');

    registerModalOverlay.classList.add('active');

    document.body.style.overflow = 'hidden';

    setTimeout(() => {
        document.getElementById('registerEmail').focus();
    }, 300);
}

registerForm.addEventListener('submit', register);

async function register(e) {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(registerForm));


    const res = await fetch('/auth/register', {
        method: 'POST',
        headers: {'Content-Type' : 'application/json'},
        body: JSON.stringify(data)
    });
    if (res.ok) {

        registerButton.classList.add("success");
        registerButton.textContent = "Completed";

    } else {
        const text = await res.text();

        switch (text) {
            case "PASSWORD":
                fieldErrors.textContent = 'Password should contain at least 8 characters';
                break;
            case "EMAIL_BLANK":
                fieldErrors.textContent = 'Email field cannot be empty';
                break;
            case "PASSWORDS_NOT_MATCHING":
                fieldErrors.textContent = 'Passwords you have provided are not matching';
                break;
            case "USER_TAKEN":
                fieldErrors.textContent = 'User with such email already exists';
                break;
        }
        fieldErrors.classList.add('active');

    }
}
