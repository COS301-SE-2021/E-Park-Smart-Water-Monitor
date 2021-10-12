let app = require('express')();
const cors = require('cors');
app.use(cors());
let server = require('http').Server(app);
const io = require('socket.io')(server, {
    cors: {
        origin: '',
    }
});
// const readline = require('readline'); // terminal commands

// call "set PORT=7777" in terminal
let PORT = process.env.PORT || 5000;
server.listen(PORT, console.log(`Socket server running on port: ${PORT}`))

const axios = require('axios')
//https://flaviocopes.com/node-http-post/

// const clients = [];
// const users = []; //gets API
// let num =0;

// This happens every time a new client connects to the server
io.on('connection', (socket) => {
    // tells us which socket connected in the terminal
    console.log(`Connected: ${socket.id}`);

    // tells us when the client disconnects
    socket.on('disconnect', () =>
        console.log(`Disconnected: ${socket.id}`)
    );

    // when a client joins a room for an inspection chat
    socket.on('join', (room) => {
        console.log(`Socket ${socket.id} joining ${room}`);
        socket.join(room);
    });

    // the chat information sent to the server for that room
    // data contains inspection id (room) and the message sent
    socket.on('chat', (data) => {
        const { message, room, token } = data;
        console.log(`msg: ${message}, room: ${room}`);
        io.to(room).emit('chat', message);


        // sends the message to the db to keep it in storage
        let body = {
            inspectionId: room, // inspection id
            comments: message,
        }
        console.log("sending body: ", body)
        axios.post('https://c7df-105-229-81-248.ngrok.io/api/inspections/setComments', body, {
          headers: {
              'Authorization': "Bearer " + token
          }
        }).then((res)=>{
            // confirm that the inspection was set
            console.log("Message "+message+" sent successfully")
            console.log("res "+JSON.stringify(res))

        }).catch( (res)=> {
            console.log(JSON.stringify(res))
        });

        // sends the message back to the room for all other clients to be able to see

    });
});