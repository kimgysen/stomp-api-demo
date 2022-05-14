package be.bewire.public_chat.dto.send;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class PublicMessageSendDto {
    private final String chatRoomId;
    private final Date date;
    private final String fromUserNickName;
    private final String message;
}
