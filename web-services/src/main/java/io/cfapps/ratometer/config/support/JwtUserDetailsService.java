package io.cfapps.ratometer.config.support;

import io.cfapps.ratometer.config.MessageSourceConfig;
import io.cfapps.ratometer.service.RoleService;
import io.cfapps.ratometer.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserService userService;
    private RoleService roleService;


    public JwtUserDetailsService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        io.cfapps.ratometer.entity.User user = userService.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException(MessageSourceConfig.getMessage("usr.err.username.does-not-exist"));

        User.UserBuilder userBuilder = User.builder();
        userBuilder
                .username(username)
                .password(user.getPassword())
                .roles(roleService.getRoleNamesByUserId(user.getUsersId()).toArray(new String[] {}))
                .accountLocked(!user.getActive())
                .accountExpired(user.getDeleted());

        return userBuilder.build();
    }
}
