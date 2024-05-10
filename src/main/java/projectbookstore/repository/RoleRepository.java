package projectbookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbookstore.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRole(Role.RoleName role);
}
