package pg.provingground.security;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pg.provingground.service.CarRentalService;
import pg.provingground.service.OwnershipService;
import pg.provingground.service.UserService;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAspect {

    private final ApplicationContext context;
    private final UserService userService;

    @Before("@annotation(checkOwnership) && args(id,..)")
    public void checkOwnership(JoinPoint joinPoint, CheckOwnership checkOwnership, Long id) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String serviceName = checkOwnership.serviceName();// 서비스 이름을 결정하는 로직;
        OwnershipService service = (OwnershipService) context.getBean(serviceName);

        if (!service.isOwnerMatched(id, userService.getLoginUserByUsername(auth.getName()))) {
            throw new AccessDeniedException("해당 권한이 없습니다!");
        }
    }
}
