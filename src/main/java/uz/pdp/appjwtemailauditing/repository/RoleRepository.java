package uz.pdp.appjwtemailauditing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appjwtemailauditing.entity.Role;
import uz.pdp.appjwtemailauditing.entity.enums.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRoleName(RoleName roleName);

}
