package tn.esprit.ProjetSpring.Services;

import tn.esprit.ProjetSpring.entities.Role;

import java.util.List;

public interface IRoleService {
    Role addRole(Role role);
    Role getRole (long idRole);

    List<Role> getAllRoles();

    void deleteRole (long idRole);

    Role updateRole (Role role);
    Role findByName(String name);

}
