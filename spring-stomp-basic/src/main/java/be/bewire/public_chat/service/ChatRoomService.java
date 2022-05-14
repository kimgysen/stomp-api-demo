package be.bewire.public_chat.service;

import be.bewire.public_chat.dto.send.PublicMessageSendDto;

public interface ChatRoomService {
    void sendPublicMessage(PublicMessageSendDto msgDto);
}
