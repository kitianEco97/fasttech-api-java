package com.mosainfo.fstechapi.repository;

import com.mosainfo.fstechapi.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    Optional<File> findByName(String name);
    boolean existsByName(String name);
}
