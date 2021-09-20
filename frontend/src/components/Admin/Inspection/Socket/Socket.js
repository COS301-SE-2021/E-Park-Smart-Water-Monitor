import io from 'socket.io-client';
let socket;
export const initiateSocket = (room) => {
    // the server connection
    socket = io('https://9517-41-216-201-32.ngrok.io:5000'); // server address
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