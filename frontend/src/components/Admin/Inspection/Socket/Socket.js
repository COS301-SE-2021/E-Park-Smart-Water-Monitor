import io from 'socket.io-client';
let socket;
export const initiateSocket = (room) => {
    // the server connection
    socket = io('http://3.142.210.254:5555'); // server address
    if (socket && room) {
        socket.emit('join', room)
    }
    
}
export const disconnectSocket = () => {
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