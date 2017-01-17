/**
 * 
 */
package co.expertic.training.web.configuration;

import co.expertic.training.model.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import co.expertic.training.service.user.UserService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:edwin.gomez@expertla.com">Edwin Gomez</a>
 * @project training-service
 * @class SecurityUserDetailsService
 * @since 11/07/2016
 *
 */
/**
 *
 * UserDetails service that reads the user credentials from the database, using a JPA repository.
 *
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private UserService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //UserDTO user = usuarioService.findUserByUsername(username);
          UserDTO user = null;
        if (user == null) {
            String message = "Username not found" + username;
            LOGGER.info(message);
            throw new UsernameNotFoundException(message);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        LOGGER.info("Found user in database: " + user);

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }
}
