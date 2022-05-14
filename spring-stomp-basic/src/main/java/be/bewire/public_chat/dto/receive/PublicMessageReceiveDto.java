package be.bewire.public_chat.dto.receive;


import lombok.Data;

@Data
public class PublicMessageReceiveDto {
    String nickName;
    String chatRoomId;
    String message;
}
