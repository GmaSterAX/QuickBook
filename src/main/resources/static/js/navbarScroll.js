let prevScrollPos = window.pageYOffset;
        // Corrected selector to get the navbar by its ID
        const navbar = document.getElementById('mainNavbar'); // Changed from 'navbar' to 'mainNavbar'

        if (navbar) { // Check if navbar element exists
            window.addEventListener('scroll', () => {
                const currentScrollPos = window.pageYOffset;

                if (currentScrollPos > 100) {
                    // Use classList.toggle with a boolean second argument
                    navbar.classList.toggle('hidden', currentScrollPos > prevScrollPos);
                    navbar.classList.toggle('visible', currentScrollPos <= prevScrollPos);
                } else {
                    navbar.classList.remove('hidden');
                    navbar.classList.add('visible'); // Ensure it's visible when near the top
                }

                prevScrollPos = currentScrollPos;
            });
        } else {
            console.error("Navbar element with ID 'mainNavbar' not found.");
        }