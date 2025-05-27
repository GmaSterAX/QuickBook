function goToReservation() {
  const isLoggedIn = localStorage.getItem("isLoggedIn");
  console.log("Login durumu:", isLoggedIn);

  if (isLoggedIn !== "true") {
    Swal.fire({
      icon: 'warning',
      title: 'Warning',
      text: 'You need to login before booking!',
      confirmButtonText: 'Login',
      confirmButtonColor: '#212529',
      background: '#f0f0f0'
    }).then((result) => {
      if (result.isConfirmed) {
        setTimeout(() => {
          window.location.href = '/login';
        }, 100);
      }
    });
  } else {
    window.location.href = '/reservation';
  }
}

function logOut(){
    localStorage.setItem("isLoggedIn", "false");
    fetch("/logout", {
        method: "POST"
    }).then(() => window.location.href = "/");
}

function fetchMyAccount() {
    window.location.href = "/my-account";
}

function getUserNotifications() {
  fetch("/notifications", {
    method: "GET"
  })
  .then(response => response.text())
  .then(html => {
    document.body.innerHTML = html; // örnek: sayfayı günceller
  })
  .catch(error => {
    console.error("Hata oluştu:", error);
  });
}