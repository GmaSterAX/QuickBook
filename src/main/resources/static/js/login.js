document.addEventListener('DOMContentLoaded', function () {
  // 🔐 Şifre göster/gizle işlemi
  const toggleButtons = document.querySelectorAll('.toggle-password');
  toggleButtons.forEach(function (button) {
    button.addEventListener('click', function () {
      const targetId = this.getAttribute('data-target');
      const passwordInput = document.getElementById(targetId);

      if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        this.querySelector('i').classList.remove('fa-eye-slash');
        this.querySelector('i').classList.add('fa-eye');
      } else {
        passwordInput.type = 'password';
        this.querySelector('i').classList.remove('fa-eye');
        this.querySelector('i').classList.add('fa-eye-slash');
      }
    });
  });

  document.getElementById('loginSubmit').addEventListener('click', async function(event) {
    event.preventDefault(); // formun kendi submitini engelle

    const mail = document.getElementById('mail').value;
    const password = document.getElementById('password').value;

    const response = await fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ mail, password })
    });

    if (response.redirected) {
        window.location.href = response.url;
        localStorage.setItem("isLoggedIn","true");
    } else {
        const text = await response.text();
        showError("Unable to login! Please control your credentails.");
    }
  });

  function showError(message) {
  const errorBox = document.getElementById('errorMessage');
  errorBox.textContent = message;
  errorBox.classList.remove('d-none');
  }

});