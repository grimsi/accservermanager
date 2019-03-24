package grimsi.accservermanager.backend.service;

import grimsi.accservermanager.backend.dto.UserDto;
import grimsi.accservermanager.backend.entity.User;
import grimsi.accservermanager.backend.repository.UserRepository;
import grimsi.accservermanager.backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("User " + s + "could not be found."));
        return new UserPrincipal(user, new SimpleGrantedAuthority("admin"));
    }

    public boolean authenticate(String username, String password) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + "could not be found."));
        return passwordEncoder.matches(password, user.password);
    }

    public boolean authenticate(UserDto userDto) {
        return authenticate(userDto.getUsername(), userDto.getPassword());
    }

    public User registerUser(UserDto userDto) {
        User user = new User();
        user.username = userDto.getUsername();
        user.password = passwordEncoder.encode(userDto.getPassword());
        return userRepository.save(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
