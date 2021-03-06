// const { createServer } = require("http");
// const { Server } = require("socket.io");
const Client = require("socket.io-client");

let app = require('express')();
const cors = require('cors');
app.use(cors());
// let server = require('http').Server(app);
// const io = require('socket.io')(server)

describe("inspection comments chat", () => {
    let io, serverSocket, clientSocket;

    beforeAll((done) => {
        let server = require('http').Server(app);
        const io = require('socket.io')(server)
        server.listen(() => {
            const port = server.address().port;
            clientSocket = new Client(`http://localhost:${port}`);
            io.on("connection", (socket) => {
                serverSocket = socket;
            });
            clientSocket.on("connect", done);
        });
    });

    // close all the socket connections opened for testing
    afterAll(() => {
        io.close();
        clientSocket.close();
    });


    test("send chat message from server to all clients", (done) => {
        clientSocket.on("chat", (arg) => {
            expect(arg).toBe("inspection complete.");
            done();
        });
        serverSocket.emit("chat", "inspection complete.");
    });

    test("send message from client to server", (done) => {
        serverSocket.on("hi", (cb) => {
            cb("inspection complete.");
        });
        clientSocket.emit("hi", (arg) => {
            expect(arg).toBe("inspection complete.");
            done();
        });
    });
});