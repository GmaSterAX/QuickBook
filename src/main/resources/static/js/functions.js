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
    window.location.href = "/notifications";
  })
  .catch(error => {
    console.error("Hata oluştu:", error);
  });
}

function getUserReservations() {
  fetch("/my-reservations", {
    method: "GET"
  })
  .then(response => response.text())
  .then(html => {
    window.location.href = "/my-reservations";
  })
  .catch(error => {
    console.error("Hata oluştu:", error);
  });
}

function getUserFavorites() {
  fetch("/my-favorites", {
    method: "GET"
  })
  .then(response => response.text())
  .then(html => {
    window.location.href = "/my-favorites";
  })
  .catch(error => {
    console.error("Hata oluştu:", error);
  });
}

function getUserPayments() {
  fetch("/my-payments", {
    method: "GET"
  })
  .then(response => response.text())
  .then(html => {
    window.location.href = "/my-payments";
  })
  .catch(error => {
    console.error("Hata oluştu:", error);
  });
}

function getHotelDetails(button) {
  const hotelId = button.getAttribute('data-hotel-id');

  fetch(`/hotel/${hotelId}`)
    .then(response => {
      if (!response.ok) throw new Error("Hotel not found");
      return response.text();
    })
    .then(() => {
      window.location.href = `/hotel/${hotelId}`;
    })
    .catch(error => {
      console.error("Hata:", error);
      alert("Otel detayına gidilemedi.");
    });
}

function addToFavorites(button) {
    const hotelId = button.getAttribute('data-hotel-id');
    console.log('Hotel ID:', hotelId);
    
    fetch('/favorites/add/' + hotelId, { method: 'POST'})
    .then(response => {
        console.log('Response status:', response.status);
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(text || "Favorilere eklenemedi.");
            });
        }
        return response.text();
    })
    .then(data => {
        console.log('Success:', data);
        Swal.fire({
            title: 'Başarılı!',
            text: data || "Otel favorilere eklendi.",
            icon: 'success',
            confirmButtonText: 'Tamam',
            confirmButtonColor: '#212529',
            background: '#f0f0f0'
        });
    })
    .catch(error => {
        console.error('Error:', error);
        Swal.fire({
            title: 'Hata!',
            text: error.message,
            icon: 'error',
            confirmButtonText: 'Tamam',
            confirmButtonColor: '#212529',
            background: '#fff0f0'
        });
    });
}