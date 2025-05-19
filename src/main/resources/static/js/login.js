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

  const loginForm = document.querySelector('#loginForm');
  const submitBtn = document.querySelector('#loginSubmit');

  if (loginForm) {
    loginForm.addEventListener('submit', function (e) {
      e.preventDefault(); // Sayfa yenilenmesin

      // Butonu devre dışı bırak UX için
      if (submitBtn) {
        submitBtn.disabled = true;
        submitBtn.textContent = 'Logging in...';
      }

      const formData = {
        mail: document.getElementById('mail').value,
        password: document.getElementById('password').value
      };

      console.log('Giriş verileri gönderiliyor:', formData);

      fetch('/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      })
        .then(response => {
          console.log('Response status:', response.status);

          if (response.ok) {
            // Başarılı login işlemi
            return response.text();
          } else {
            return response.text().then(errorText => {
              throw new Error(`Giriş başarısız oldu (${response.status}): ${errorText || 'Bilinmeyen hata'}`);
            });
          }
        })
        .then(data => {
          if (data) {
            console.log('Giriş başarılı:', data);

            localStorage.setItem('isLoggedIn', 'true');

            // Ana sayfaya yönlendir
            window.location.href = '/';

            //userDropdown.style.display = "inline-block";
            //loginButton.style.display = "none";
          }else{
            localStorage.setItem('isLoggedIn', 'false');
            //userDropdown.style.display = "none";
            //loginButton.style.display = "inline-block";
          }
        })
        .catch(error => {
          console.error('Hata:', error);
          alert("Giriş sırasında bir hata oluştu: " + error.message);
        })
        .finally(() => {
          // Butonu tekrar aktif et
          if (submitBtn) {
            submitBtn.disabled = false;
            submitBtn.textContent = 'Giriş Yap';
          }
        });
    });
  } else {
    console.error('Login formu bulunamadı!');
  }
});