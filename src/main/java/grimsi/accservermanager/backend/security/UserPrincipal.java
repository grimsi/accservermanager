package grimsi.accservermanager.backend.security;

import grimsi.accservermanager.backend.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 6850396258498499969L;
    private User user;
    private List<SimpleGrantedAuthority> authorities;

    public UserPrincipal(User user, SimpleGrantedAuthority authority) {
        this.user = user;
        authorities = Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
