package tr.edu.uludag.webprogramlama.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import tr.edu.uludag.webprogramlama.service.UserService;

import javax.servlet.http.HttpServletRequest;


@Component
public class ActiveDirectoryAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(ActiveDirectoryAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String tcNo = (String) authentication.getPrincipal();
        if (tcNo == null || tcNo.isEmpty()) {
            throw new BadCredentialsException("Invalid token");
        }
        // password and login ok
        // go to db fetch user and roles
        // TODO access request and get csrf data
        UserDetails user = userService.getByTcNo(tcNo);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities()
        );
        token.setDetails(user);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
