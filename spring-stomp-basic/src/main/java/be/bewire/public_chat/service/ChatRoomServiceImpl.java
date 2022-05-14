package be.bewire.public_chat.service;

import be.bewire.public_chat.dto.receive.PublicMessageReceiveDto;
import be.bewire.public_chat.dto.send.PublicMessageSendDto;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;


@AllArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final SimpMessagingTemplate wsTemplate;

    @Override
    public void sendPublicMessage(PublicMessageSendDto msgDto) {
        wsTemplate.convertAndSend(
                publicMsgDestination(msgDto.getChatRoomId()), msgDto);
    }

    private String publicMsgDestination(String chatRoomId) {
        return "/topic/" + chatRoomId + ".chatroom.messages";
    }

    private String connectedUsersDestination(String chatRoomId) {
        return "/topic/" + chatRoomId + ".connected.users";
    }

}
