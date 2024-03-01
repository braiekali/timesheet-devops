package tn.esprit.ProjetSpring.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ProjetSpring.Services.IRoleService;
import tn.esprit.ProjetSpring.entities.Role;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")

public class RoleController {
    IRoleService roleService ;
    @PostMapping("/addrole")
    Role addRole (@RequestBody Role role){return roleService.addRole(role);}
    @GetMapping("/role/{id}")
    Role retrieveUser (@PathVariable Long id){return roleService.getRole(id);}
    @GetMapping("/roles")
    List<Role> retreiveRoles(){return roleService.getAllRoles();}
    @DeleteMapping("/deleterole/{id}")
    void deleteUser (@PathVariable Long id){
        roleService.deleteRole(id);
    }
    @PutMapping("/updaterole")
    Role updateUser(@RequestBody Role role) {
        return roleService.updateRole(role);
    }
    @GetMapping("/role/{name}")
    Role findByName(@PathVariable String name){return roleService.findByName(name);}

}
