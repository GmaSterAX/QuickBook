function goToReservation() {
  const isLoggedIn = localStorage.getItem("isLoggedIn");
  console.log("Login status:", isLoggedIn);

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
    console.error("error:", error);
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
    console.error("error:", error);
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
    console.error("error:", error);
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
    console.error("error:", error);
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
      alert("Couldnt go to hotel details.");
    });
}

function addToFavorites(button) {
    const hotelId = button.getAttribute('data-hotel-id');
    console.log('Hotel ID:', hotelId);
    
    fetch(`/favorites/add/${hotelId}`, { method: 'POST' })
    .then(response => {
        console.log('Response status:', response.status);
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(text || "Couldnt add to favorites.");
            });
        }
        return response.text();
    })
    .then(data => {
        console.log('Success:', data);
        Swal.fire({
            title: 'Success!',
            text: data || "Hotel added to Favorites.",
            icon: 'success',
            confirmButtonText: 'Tamam',
            confirmButtonColor: '#212529',
            background: '#f0f0f0'
        });
    })
    .catch(error => {
        console.error('Error:', error);
        Swal.fire({
            title: 'Error!',
            text: error.message,
            icon: 'error',
            confirmButtonText: 'Okey',
            confirmButtonColor: '#212529',
            background: '#fff0f0'
        });
    });
}

function deleteFromFavorites(button) {
    const hotelId = button.getAttribute("data-hotel-id");

    fetch(`/removefromfavourite/${hotelId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Removing from favorites failed.');
        }
        return response.text();
    })
    .then(data => {
        Swal.fire({
            icon: 'success',
            title: 'Removed',
            text: 'Hotel is removed from your favorites.',
            confirmButtonText: 'Okey',
            confirmButtonColor: '#212529',
            background: '#fff0f0'
        }).then(() => {
            // Kullanıcı onayladıktan sonra sayfayı yenile
            location.reload();
        });
    })
    .catch(error => {
        Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: error.message,
            confirmButtonText: 'Okey'
        });
    });
}
function submitComment() {
    const commentText = document.getElementById("userComment").value.trim();
    const hotelId = document.querySelector("button[onclick='submitComment()']").getAttribute("data-hotel-id");
    console.log("Hotel ID:", hotelId);
    
    if (!commentText) {
        alert("Comment cannot be empty.");
        return;
    }

    fetch("/add-new-comment", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            hotelId: hotelId,
            user_comment: commentText
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to submit comment.");
        }
        return response.json();
    })
    .then(data => {
        // Başarı mesajı gösterilebilir (istersen)
        document.getElementById("commentSuccess").style.display = "block";
        document.getElementById("userComment").value = "";

        // Sayfayı yenile
        window.location.reload();
    })
    .catch(error => {
        alert("An error occurred: " + error.message);
    });
}

async function bookRoom(button) {
    const hotelId = document.querySelector('button[onclick*="addToFavorites"]').getAttribute("data-hotel-id");
    const roomId = button.getAttribute("data-room-id");
    const price = button.getAttribute("data-room-price");

    const startDate = localStorage.getItem("checkIn");
    const endDate = localStorage.getItem("checkOut");

    const reservation = {
        h_id: hotelId,
        r_id: roomId,
        price: price,
        start_date: startDate,
        end_date: endDate
        // u_id gönderilmiyor
    };
    console.log("hotelID:", hotelId);
    console.log("Room ID:", roomId); // bu sana ne döndürüyor?
    console.log("price:", price);
    console.log("startDate:", startDate);
    console.log("endDate:", endDate);


    try {
        const response = await fetch("/reservation/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(reservation)
        });

        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Başarılı!',
                text: 'Reservation made successfully.'
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: 'Reservation couldnt made.'
            });
        }
    } catch (err) {
        console.error(err);
        Swal.fire({
            icon: 'error',
            title: 'Server error!',
            text: 'Something went wrong.'
        });
    }
}

function updatePayment(id, newStatus) {
    fetch(`/update-payment/${id}?status=${newStatus}`, {
        method: 'PUT'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to update payment");
        }
        return response.json();
    })
    .then(data => {
        Swal.fire({
            icon: 'success',
            title: `Payment ${newStatus === 'Paid' ? 'completed' : 'cancelled'} successfully`,
            showConfirmButton: false,
            timer: 1500
        }).then(() => {
            window.location.reload();
        });
    })
    .catch(error => {
        console.error("Error:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Could not update payment status.'
        });
    });
}

function payPayment(id) {
    updatePayment(id, 'Paid');
}

function cancelPayment(id) {
    updatePayment(id, 'Cancelled');
}  