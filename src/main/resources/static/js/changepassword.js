document.addEventListener('DOMContentLoaded', function () {
    const toggleButtons = document.querySelectorAll('.toggle-password');
    toggleButtons.forEach(function (button) {
      button.addEventListener('click', function () {
        const targetId = this.getAttribute('data-target');
        const passwordInput = document.getElementById(targetId);
        const icon = this.querySelector('i');

        if (!passwordInput || !icon) return;

        const isHidden = passwordInput.type === 'password';
        passwordInput.type = isHidden ? 'text' : 'password';
        icon.classList.toggle('fa-eye', isHidden);
        icon.classList.toggle('fa-eye-slash', !isHidden);
      });
    });
  });

  function changePassword(event) {
    event.preventDefault(); 

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const confirmPassword = document.getElementById('confirmPassword').value.trim();

    if (password !== confirmPassword) {
      Swal.fire({
        icon: 'error',
        title: 'Passwoeds dont match',
        text: 'Please enter same passwords.'
      });
      return false;
    }

    fetch('/change-the-password', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        email: email,
        newPassword: password
      })
    })
    .then(response => {
      if (!response.ok) {
        return response.text().then(text => { throw new Error(text); });
      }
      return response.text();
    })
    .then(data => {
      Swal.fire({
        icon: 'success',
        title: 'Success',
        text: data
      });

      document.getElementById('email').value = '';
      document.getElementById('password').value = '';
      document.getElementById('confirmPassword').value = '';
    })
    .catch(error => {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: error.message || 'Password couldnot changed.'
      });
    });

    return false; 
  }