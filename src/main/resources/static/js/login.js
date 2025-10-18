document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    fetch('http://localhost:8080/api/v1/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
    })
    .then(res => res.json())
    .then(data => {
        window.jwtToken = data.token;
        window.myId = data.id;

        document.getElementById('login-container').style.display = 'none';
        document.getElementById('chat-container').style.display = 'block';

        fetchUsers();
        connectWebSocket();
    })
    .catch(err => alert('Login failed: ' + err));
});
