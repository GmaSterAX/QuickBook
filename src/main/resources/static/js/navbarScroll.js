let prevScrollPos = window.pageYOffset;
const navbar = document.getElementById('mainNavbar');

if (navbar) {
    window.addEventListener('scroll', () => {
    const currentScrollPos = window.pageYOffset;

    if (currentScrollPos > 100) {
        navbar.classList.toggle('hidden', currentScrollPos > prevScrollPos);
        navbar.classList.toggle('visible', currentScrollPos <= prevScrollPos);
    } else {
        navbar.classList.remove('hidden');
        navbar.classList.add('visible');
    }

    prevScrollPos = currentScrollPos;
    });
}else{
    console.error("Navbar element with ID 'mainNavbar' not found.");
}