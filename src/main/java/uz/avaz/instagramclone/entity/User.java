package uz.avaz.instagramclone.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.avaz.instagramclone.entity.template.AbsEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "users")
public class User extends AbsEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String fullName;

    @Column(nullable = false)
    LocalDate birthDate;

    String bio;

    boolean isPublic = true;
    boolean emailVerified;
    String emailCode;

    @OneToMany(mappedBy = "user1")
    List<Chat> chats;

    @OneToMany(mappedBy = "user2")
    List<Chat> chats2;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false)
    )
    Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
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

    @OneToOne
    @JoinColumn(name = "attachment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Attachment attachment;

    public User(String username, String password, String email, String fullName, LocalDate birthDate, String bio, boolean isPublic) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.bio = bio;
        this.isPublic = isPublic;
    }
}
