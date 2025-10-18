let stompClient;
window.jwtToken = null;
window.myId = null;

// LOGIN
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
        window.myId = data.id; // backend login-da id göndərməlidir

        document.getElementById('login-container').style.display = 'none';
        document.getElementById('chat-container').style.display = 'block';

        fetchUsers();
        connectWebSocket();
    })
    .catch(err => console.error(err));
});

// FETCH USERS
function fetchUsers() {
    fetch('http://localhost:8080/api/v1/users/All-users', {
        headers: { 'Authorization': 'Bearer ' + window.jwtToken }
    })
    .then(res => res.json())
    .then(users => {
        const select = document.getElementById('userSelect');
        select.innerHTML = '<option value="">Select User</option>';
        users.forEach(user => {
            if(user.id !== window.myId) {
                const option = document.createElement('option');
                option.value = user.id;
                option.textContent = user.fullName;
                select.appendChild(option);
            }
        });
    });
}

// CONNECT WEBSOCKET
function connectWebSocket() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect(
        { 'Authorization': 'Bearer ' + window.jwtToken },
        function(frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/user/' + window.myId + '/queue/messages', function(messageOutput) {
                const msg = JSON.parse(messageOutput.body);
                displayMessage(msg.senderId, msg.content);
            });
        }
    );
}

// SEND MESSAGE
document.getElementById('send-btn').addEventListener('click', function() {
    const content = document.getElementById('message-input').value;
    const receiverId = document.getElementById('userSelect').value;

    if(stompClient && content.trim() !== '' && receiverId) {
        stompClient.send('/app/sendPrivateMessage', {}, JSON.stringify({
            content: content,
            receiverId: parseInt(receiverId)
        }));
        document.getElementById('message-input').value = '';
    } else {
        alert('Select user and type a message');
    }
});

// DISPLAY MESSAGE
function displayMessage(senderId, content) {
    const div = document.createElement('div');
    div.textContent = senderId + ': ' + content;
    document.getElementById('messages').appendChild(div);
}
