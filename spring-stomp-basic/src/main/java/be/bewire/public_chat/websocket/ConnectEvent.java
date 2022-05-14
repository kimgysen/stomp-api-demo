package be.bewire.public_chat.websocket;

import be.bewire.public_chat.websocket.util.WsUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;


@AllArgsConstructor
@Component
public class ConnectEvent implements ApplicationListener<SessionConnectEvent> {

    private final WsUtil wsUtil;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        handleConnect(event);
    }

    private void handleConnect(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String chatRoomId = wsUtil.getNativeHeader("HEADER_CHATROOM_ID", headers);

        wsUtil.storeInSession("SESSION_CHATROOM_ID", chatRoomId, headers);
    }
}
