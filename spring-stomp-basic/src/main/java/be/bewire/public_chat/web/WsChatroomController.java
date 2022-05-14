package be.bewire.public_chat.web;

import be.bewire.public_chat.dto.receive.PublicMessageReceiveDto;
import be.bewire.public_chat.dto.send.PublicMessageSendDto;
import be.bewire.public_chat.service.ChatRoomService;
import be.bewire.public_chat.websocket.util.WsUtil;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class WsChatroomController {

    private final ChatRoomService chatRoomService;
    private final HashMap<String, List<PublicMessageSendDto>> chatRoomMessages = new HashMap<>();
    private final WsUtil wsUtil;

    @SubscribeMapping("/chatroom.old.messages")
    public List<PublicMessageSendDto> getOldPublicMessagesOnSubscribe(SimpMessageHeaderAccessor headerAccessor) {
        String chatRoomId = wsUtil.getSessionAttr("SESSION_CHATROOM_ID", headerAccessor);

        return getMessagesFromCache(chatRoomId);
    }

    @MessageMapping("/send.message.chatroom")
    public void sendPublicMessage(@Payload PublicMessageReceiveDto msgDto, SimpMessageHeaderAccessor headerAccessor) {
        PublicMessageSendDto msgSendDto = PublicMessageSendDto
                .builder()
                .chatRoomId(msgDto.getChatRoomId())
                .message(msgDto.getMessage())
                .fromUserNickName(msgDto.getNickName())
                .date(new Date())
                .build();

        cacheChatRoomMessage(msgDto.getChatRoomId(), msgSendDto);
        chatRoomService.sendPublicMessage(msgSendDto);
    }

    private List<PublicMessageSendDto> getMessagesFromCache(String chatRoomId) {
        List<PublicMessageSendDto> messages = chatRoomMessages.get(chatRoomId);
        return messages == null
                ? new ArrayList<>()
                : messages;
    }

    private void cacheChatRoomMessage(String chatRoomId, PublicMessageSendDto msgDto) {
        List<PublicMessageSendDto> chatMessages = chatRoomMessages.get(chatRoomId);

        chatRoomMessages
                .computeIfAbsent(msgDto.getChatRoomId(), k -> new ArrayList<>())
                .add(msgDto);

    }

}
