package uz.avaz.instagramclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.avaz.instagramclone.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);

    Role getByName(String name);
}
