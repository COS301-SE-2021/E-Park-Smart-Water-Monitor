import io from 'socket.io-client';
let socket;
export const initiateSocket = (room) => {
    // the server connection
    socket = io('http://localhost:5000'); // server address
    console.log(`Connecting to chat...`);
    if (socket && room) {
        socket.emit('join', room)
    }
    
}
export const disconnectSocket = () => {
    console.log('Disconnecting from chat...');
    if(socket) socket.disconnect();
}

// will get other users chat messages when they are sent from a specific inspection
export const subscribeToChat = (cb) => {
    if (!socket) {
        return(true);
    }
    socket.on('chat', msg => {
        return cb(null, msg);
    });
}


export const sendMessage = (message, room, token) => {
    if (socket) {
        socket.emit('chat', { message, room, token });
    }
}