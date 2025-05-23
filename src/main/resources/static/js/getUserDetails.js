function fetchMyAccount() {
    const userMail = localStorage.getItem("userMail");

    if (!userMail) {
        console.error("User mail not found in localStorage");
        return;
    }

    // Sayfaya yönlendir:
    window.location.href = `/my-account?userMail=${encodeURIComponent(userMail)}`;
}
