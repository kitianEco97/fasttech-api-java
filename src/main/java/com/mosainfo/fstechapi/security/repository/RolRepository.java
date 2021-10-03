package com.mosainfo.fstechapi.security.repository;


import com.mosainfo.fstechapi.security.entity.Rol;
import com.mosainfo.fstechapi.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
