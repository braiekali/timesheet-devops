package tn.esprit.ProjetSpring.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ProjetSpring.Repositories.RoleRepository;
import tn.esprit.ProjetSpring.entities.Role;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService implements IRoleService{

    RoleRepository roleRepository;

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }
    @Override
    public Role getRole(long idRole) {
        return roleRepository.findById(idRole).orElse(null);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteRole(long idRole) {
        roleRepository.deleteById(idRole);
    }

    @Override
    public Role updateRole(Role role) {
        Role ro = roleRepository.findById(role.getIdRole()).orElse(null);
        if (ro != null)
            roleRepository.save(role);

        return ro;

    }

    @Override
    public Role findByName(String name) {
        return  roleRepository.findByName(name);
    }
}
