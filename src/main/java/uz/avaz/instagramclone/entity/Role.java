package uz.avaz.instagramclone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import uz.avaz.instagramclone.entity.template.AbsEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "roles")
public class Role extends AbsEntity implements GrantedAuthority {

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
