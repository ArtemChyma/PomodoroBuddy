console.log('Artem')

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


if (startButton) {
    startButton.addEventListener('click', function (e) {
        e.preventDefault();
        openLoginModalWindow();
    });
}

// Закрытие модального окна
if (closeModal && registerCloseModal) {
    closeModal.addEventListener('click', closeLogInModalWindow);
    registerCloseModal.addEventListener('click', closeRegisterModalWindow);
}

// Закрытие при клике на оверлей (фон)
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

// Закрытие по клавише Escape
document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape' && modalOverlay.classList.contains('active')) {
        closeLogInModalWindow();
    } else if (e.key === 'Escape' && registerModalOverlay.classList.contains('active')) {
        closeRegisterModalWindow();
    }
});

// Переключение видимости пароля
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

// Обработка формы
const loginForm = document.getElementById('loginModalForm');
if (loginForm) {
    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // Здесь будет ваша логика авторизации
        console.log('Логин:', {email, password});

        // Пример: закрытие модалки после успешного входа
        // closeModalWindow();
        // alert('Welcome back!');
    });
}

const registerForm = document.getElementById('registerModalForm');

// Кнопка "Create account"
if (showRegister) {
    showRegister.addEventListener('click', function (e) {
        e.preventDefault();
        // Здесь можно добавить переход на форму регистрации
        console.log('Переход на регистрацию');
        openRegisterModalWindow();
        // Например: открыть другое модальное окно
    });
}

if (showLogin) {
    showLogin.addEventListener('click', function (e) {
        e.preventDefault();
        // Здесь можно добавить переход на форму регистрации
        console.log('Переход на регистрацию');
        openLoginModalWindow();
        // Например: открыть другое модальное окно
    });
}

// Функция закрытия модального окна
function closeLogInModalWindow() {
    // Убираем затемнение
    wrapper.classList.remove('blurred');

    // Скрываем модальное окно
    modalOverlay.classList.remove('active');

    // Возвращаем скролл
    document.body.style.overflow = '';

    // Сбрасываем форму
    if (loginForm) {
        loginForm.reset();
    }

    // Возвращаем пароль в скрытый режим
    if (passwordInput && togglePassword) {
        passwordInput.type = 'password';
        togglePassword.textContent = '👁';
    }
}

function closeRegisterModalWindow() {
    wrapper.classList.remove('blurred');

    // Скрываем модальное окно
    registerModalOverlay.classList.remove('active');

    // Возвращаем скролл
    document.body.style.overflow = '';

    // Сбрасываем форму
    if (registerForm) {
        registerForm.reset();
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