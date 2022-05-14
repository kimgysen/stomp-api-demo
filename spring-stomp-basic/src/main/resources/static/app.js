
let stompClient = null;

let chatRoomId = 'chatroom-1';

const setConnected = (connected) => {
    const $conversation = $("#conversation");

    $("#btn-connect").prop("disabled", connected);
    $("#btn-disconnect").prop("disabled", !connected);
    connected
        ? $conversation.show()
        : $conversation.hide();

    $("#chat").html("");
}

const connect = () => {
    var socket = new SockJS('http://localhost:8003/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({ HEADER_CHATROOM_ID: chatRoomId }, function (frame) {
        setConnected(true);

        stompClient.subscribe("/app/chatroom.old.messages", message => {
            const messages = JSON.parse(message.body);
            showMessages(messages);
        });

        stompClient.subscribe('/topic/' + chatRoomId + '.chatroom.messages', message => {
            const msg = JSON.parse(message.body);
            showMessage(msg);
        });
    });
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

const sendMessage = () => {
    stompClient.send("/app/send.message.chatroom", {}, JSON.stringify(
        {
            nickName: $("#input-nickname").val(),
            chatRoomId,
            message: $("#input-message").val()
        }));
}

const showMessages = messages => {
    $("#chat-messages").empty();
    messages.forEach(msg => showMessage(msg));
}

const showMessage = msg => {
    $("#chat-messages")
        .append("<tr><td>" + msg.fromUserNickName + ": " + msg.message + "</td></tr>");
}

// Events
$(function () {
    $('#select-chatroom').on('change', function() {
        chatRoomId = this.value;
        disconnect();
        connect();
    });
    $("form").on('submit', e => {
        e.preventDefault();
    });
    $("#btn-connect").on('click', connect);
    $("#btn-disconnect").on('click', disconnect);
    $("#btn-send").on('click', sendMessage);
});