package GDX_Game_server.server.ws;

import org.springframework.web.socket.WebSocketSession;
public interface DisconnectListener {
     void handle(WebSocketSession session);
    }
