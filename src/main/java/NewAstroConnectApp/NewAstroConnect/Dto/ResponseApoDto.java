package NewAstroConnectApp.NewAstroConnect.Dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ResponseApoDto<T> {

    private T data;
    private int status;
    private String message;
    private Instant timeStamp;

    public ResponseApoDto() {
    }

    public ResponseApoDto(T data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.timeStamp = Instant.now();
    }
}
