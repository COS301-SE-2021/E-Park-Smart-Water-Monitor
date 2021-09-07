import io from 'socket.io-client';
let socket;
export const initiateSocket = (room) => {
    // the server connection
    socket = io('http://localhost:5000'); // server address
    console.log(`Connecting socket...`);
    if (socket && room) {
        alert("joining")
        socket.emit('join', room)
    }
    
}
export const disconnectSocket = () => {
    console.log('Disconnecting socket...');
    if(socket) socket.disconnect();
}

// will get other users chat messages when they are sent from a specific inspection
export const subscribeToChat = (cb) => {
    if (!socket) return(true);
    socket.on('chat', msg => {
        console.log('Websocket event received!');
        return cb(null, msg);
    });
}


export const sendMessage = (room, message, token) => {
    if (socket) {
        alert(`${room}  ${message}`)
        socket.emit('chat', { message, room, token });
    }
}