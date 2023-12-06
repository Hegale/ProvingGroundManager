package pg.provingground.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pg.provingground.domain.User;
import pg.provingground.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByLoginId(username);
        // 해당하는 유저가 존재할 경우
        if (user != null) {
            return new UserDetailsImpl(user);
        }
        // 존재하지 않을 경우
        return null;
    }
}
