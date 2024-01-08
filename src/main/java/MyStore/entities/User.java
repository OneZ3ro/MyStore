package MyStore.entities;

import MyStore.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"password", "authorities", "orders", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})
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
    @ManyToOne
    @JoinColumn(name = "municipalities_id")
    private Municipality municipality;
    private String address;
    @Column(name = "img_profile")
    private String imgProfile;
    @Enumerated(EnumType.STRING)
    private List<Role> roles;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Order> orders;
    @OneToMany(mappedBy = "userSeller", fetch = FetchType.EAGER)
    private List<Product> productSold;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "favproducts_users", joinColumns = @JoinColumn(name = "products_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<Product> favouriteProducts;
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
