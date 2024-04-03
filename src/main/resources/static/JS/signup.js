document.addEventListener('DOMContentLoaded', function () {
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirm_password');
    const form = document.getElementById('signupForm');

    function validatePassword() {
        if (password.value !== confirmPassword.value) {
            confirmPassword.setCustomValidity('Passwords do not match');
        } else {
            confirmPassword.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirmPassword.onkeyup = validatePassword;

    form.onsubmit = function(event) {
        event.preventDefault(); // Prevent default form submission

        // Password Validation Regex
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_\-+=|\\?/<>{}\[\]]).{8,}$/;

        if (!passwordRegex.test(password.value)) {
            alert('Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character.');
            return false;
        }

        if (this.checkValidity()) {
            this.submit(); // Submit form if validation passes
        }
    };
});
