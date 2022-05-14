package be.bewire.public_chat.websocket.util;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WsUtil {

    public String getNativeHeader(String headerKey, SimpMessageHeaderAccessor headers) {
        List<String> nativeHeaders = headers.getNativeHeader(headerKey);

        if (nativeHeaders != null && nativeHeaders.size() > 0) {
            return nativeHeaders.get(0);
        }

        return null;
    }

    public void storeInSession(String sessionKey, String sessionValue, SimpMessageHeaderAccessor headers) {
        if (headers.getSessionAttributes() != null) {
            headers.getSessionAttributes()
                    .put(sessionKey, sessionValue);
        }
    }

    public String getSessionAttr(String sessionKey, SimpMessageHeaderAccessor headers) {
        if (headers.getSessionAttributes() == null || headers.getSessionAttributes().get(sessionKey) == null)
            return null;

        return headers
                .getSessionAttributes()
                .get(sessionKey)
                .toString();
    }

}
