function logOut(){
    localStorage.clear();
    fetch("/logout", {
        method: "POST"
    }).then(() => window.location.href = "/");
    }