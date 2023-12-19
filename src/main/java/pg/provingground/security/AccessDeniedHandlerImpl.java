package pg.provingground.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    /** 권한이 없을 경우, alert 메시지 전달 및 메인 페이지 리디렉션 */
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
        String script = "<script>alert('접근 권한이 없습니다. 메인 페이지로 이동합니다.'); window.location = '/';</script>";
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(script);
        response.getWriter().flush();
    }

}
