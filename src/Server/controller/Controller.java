package Server.controller;

import Server.UI.StartServerWindow;
import Server.UI.WhiteBoardWindow;
import Server.net.TCPServer;
import Server.shape.Shape;
import com.google.gson.Gson;
import Server.shape.*;

public class Controller {
    private TCPServer tcpServer;
    private StartServerWindow startserverWindow;
    private WhiteBoardWindow whiteBoardWindow;

    public void setStartserverWindow(StartServerWindow startserverWindow) {
        this.startserverWindow = startserverWindow;
    }

    public TCPServer getTcpServer() {
        return tcpServer;
    }

    public void setTcpServer(TCPServer tcpServer) {
        this.tcpServer = tcpServer;
    }

    public StartServerWindow getStartserverWindow() {
        return startserverWindow;
    }

    public void startServer(int serverPort) {
        this.tcpServer = new TCPServer(serverPort);
        this.tcpServer.setController(this);
        new Thread(this.tcpServer).start();
    }

    public void showWhiteBoardWindow() {
        this.whiteBoardWindow = new WhiteBoardWindow("Mini-Canvas Server", this);
        this.startserverWindow.finish();
    }

    public void serverDisconnect(){
        // server socket closed
    }

    public void newClientConnected(TCPServer.ClientSocket clientSocket) {
        //approve or not
    }

    public void approveNewClient(TCPServer.ClientSocket clientSocket){
        tcpServer.approveClient(clientSocket);
    }

    public void rejectNewClient(TCPServer.ClientSocket clientSocket){
        tcpServer.rejectClient(clientSocket);
    }

    public void addNewClient(String username, String IP, String port){

    }

    public void addShape(Shape shape, String msg) {
        this.whiteBoardWindow.getDrawarea().addShape(shape, "client");
        this.sendToClients(msg);
    }

    public void sendToClients(String msg) {
        for (TCPServer.ClientSocket socket : this.tcpServer.getAccpetpedClientSocketMap().values()) {
            socket.sendData(msg);
        }
    }
}
