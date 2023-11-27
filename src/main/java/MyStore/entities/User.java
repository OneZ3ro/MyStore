package MyStore.entities;

import MyStore.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@JsonIgnoreProperties({"password", "authorities", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "users_id")
    private UUID userId;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private LocalDate born;
    private String imgProfile;
    private List<Role> roles;
    private Location location;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (this.roles != null) {
            authorities.addAll(this.roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))
                    .toList());
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
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
