document.addEventListener('DOMContentLoaded', function () {
  // Toggle password visibility
  const toggleButtons = document.querySelectorAll('.toggle-password');
  toggleButtons.forEach(function (button) {
    button.addEventListener('click', function () {
      const targetId = this.getAttribute('data-target');
      const passwordInput = document.getElementById(targetId);
      if (passwordInput) {
        if (passwordInput.type === 'password') {
          passwordInput.type = 'text';
          this.querySelector('i').classList.remove('fa-eye-slash');
          this.querySelector('i').classList.add('fa-eye');
        } else {
          passwordInput.type = 'password';
          this.querySelector('i').classList.remove('fa-eye');
          this.querySelector('i').classList.add('fa-eye-slash');
        }
      } else {
        console.error('Password input not found with ID:', targetId);
      }
    });
  });

  // JSON form submit
  const form = document.getElementById('registerForm');
  if (!form) {
    console.error('Register form not found!');
    return;
  }

  form.addEventListener('submit', function (e) {
    e.preventDefault();
    
    // Form elementlerini kontrol et
    const nameInput = document.getElementById('name');
    const mailInput = document.getElementById('mail');
    const phoneInput = document.getElementById('phone');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('passwordConfirm');
    const error = document.getElementById("error");
    
    // Form elemanları bulunamadıysa hata logla
    if (!nameInput || !mailInput || !phoneInput || !passwordInput || !confirmPasswordInput) {
      console.error('Form elements not found:', {
        name: !!nameInput,
        mail: !!mailInput,
        phone: !!phoneInput,
        password: !!passwordInput,
        confirmPassword: !!confirmPasswordInput
      });
      alert('Form elemanları bulunamadı. Lütfen sayfayı yenileyip tekrar deneyin.');
      return;
    }

    // Compare password values, not the DOM elements
    if (passwordInput.value !== confirmPasswordInput.value) {
      error.textContent = "Passwords don't match!";
    } else {
      error.textContent = "";

      const formData = {
        name: nameInput.value,
        mail: mailInput.value,
        phone: phoneInput.value,
        password: passwordInput.value,
      };

      fetch('/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      })
      .then(response => {
        console.log('Response status:', response.status);
        
        if (response.ok) {
          return response.text().catch(err => {
            console.error('JSON parse error:', err);
            return { message: 'Register was successful but server answer could not processed' };
          });
        } else {
          // Hata detaylarını almaya çalış
          return response.text().then(errorText => {
            console.error('Error response:', errorText);
            throw new Error(`Register failed (${response.status}): ${errorText || 'Unknown error'}`);
          });
        }
      })
      .then(data => {
        Swal.fire({
          title: 'Succesful!',
          text: data,
          icon: 'success',
          confirmButtonText: 'Okey',
          confirmButtonColor: '#212529',
          background: '#f0f0f0'
        }).then(() => {
          window.location.href = "/login";
        });
      })
      .catch(error => {
        Swal.fire({
          title: 'Hata!',
          text: error.message,
          icon: 'error',
          confirmButtonText: 'Okey',
          confirmButtonColor: '#212529',
          background: '#fff0f0'
        });
      });
    }
  });
});