package com.mosainfo.fstechapi.util;

import com.mosainfo.fstechapi.security.entity.Rol;
import com.mosainfo.fstechapi.security.enums.RolNombre;
import com.mosainfo.fstechapi.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateRoles {
    //implements CommandLineRunner
    /*@Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
        Rol rolAdmin = new Rol( RolNombre.ROLE_ADMIN);
        Rol rolUser = new Rol( RolNombre.ROLE_USER);
        rolService.save( rolAdmin );
        rolService.save( rolUser );
    }*/
}
