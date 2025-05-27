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
    })
    .then(response => {
      if (response.status === 401) {
        // mail or password is wrong
        Swal.fire({
          icon: 'error',
          title: 'Unseccessful error',
          text: 'Invalid mail or password.',
          confirmButtonColor: '#212529'
        });
      } else if (response.status === 403) {
        // E-posta doğrulanmamış
        Swal.fire({
          icon: 'warning',
          title: 'Email is not verified',
          text: 'Please verify your email.',
          confirmButtonColor: '#212529'
        });   
      } else if (response.ok) {
        // Başarılı giriş
        localStorage.setItem("isLoggedIn","true")
        Swal.fire({
          icon: 'success',
          title: 'Successfully logged in',
          text: 'Directing...',
          confirmButtonColor: '#212529'
        }).then(() => {
          window.location.href = '/index';
        });
      } else {
        // Diğer tüm durumlar
        Swal.fire({
          icon: 'error',
          title: 'An error happened',
          text: 'Couldnot connect to server.',
          confirmButtonColor: '#212529'
        });
      }
    })
    .catch(error => {
      console.error('Request error:', error);
      Swal.fire({
        icon: 'error',
        title: 'Request wasnt successul',
        text: 'Something happened while connecting to server.',
          confirmButtonColor: '#212529'
      });
    });
  });
});