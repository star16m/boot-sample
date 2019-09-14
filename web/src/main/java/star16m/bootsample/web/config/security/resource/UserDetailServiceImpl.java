package star16m.bootsample.web.config.security.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService, AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = this.userRepository.findByUserId(userId);
        if (user == null) {
            log.error("Not found user for userId[{}]", userId);
            throw new UsernameNotFoundException(userId);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getPrivilege().split(",")));
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken) throws UsernameNotFoundException {
        log.debug("preAuthenticatedAuthenticationToken: [{}]", preAuthenticatedAuthenticationToken);
        return null;
    }
}
