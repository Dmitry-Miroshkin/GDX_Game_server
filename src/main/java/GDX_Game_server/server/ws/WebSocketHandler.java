package GDX_Game_server.server.ws;

import com.badlogic.gdx.utils.Array;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;


@Component
public class WebSocketHandler extends AbstractWebSocketHandler {
    private final Array<WebSocketSession> sessions = new Array<>();

    private ConnectListener connectListener;
    private DisconnectListener disconnectListener;
    private MessageListener messageListener;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        synchronized (sessions){
            sessions.add(session);
        }
        connectListener.handle(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        messageListener.handle(session, message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        synchronized (sessions){
            sessions.removeValue(session, true);
        }
        disconnectListener.handle(session);
    }

    public void setConnectListener(ConnectListener connectListener) {

        this.connectListener = connectListener;
    }

    public void setDisconnectListener(DisconnectListener disconnectListener) {
        this.disconnectListener = disconnectListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public Array<WebSocketSession> getSessions() {
        return sessions;
    }
}
