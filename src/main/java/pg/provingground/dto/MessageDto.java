package pg.provingground.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Getter
@AllArgsConstructor
public class MessageDto {

    private String message;
    private String redirectUrl;
    private RequestMethod method;
    // 화면으로 전달할 데이터
    private Map<String, Object> data;

}
