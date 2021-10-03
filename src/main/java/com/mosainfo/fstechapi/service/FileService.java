package com.mosainfo.fstechapi.service;

import com.mosainfo.fstechapi.entity.File;
import com.mosainfo.fstechapi.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FileService {

    @Autowired
    FileRepository fileRepository;

    public List<File> list() {
        return fileRepository.findAll();
    }

    public Optional<File> getOne(int id) {
        return fileRepository.findById( id );
    }

    public Optional<File> getByNombre(String name) {
        return fileRepository.findByName( name );
    }

    public void save(File file) {
        fileRepository.save( file );
    }

    public void delete(int id) {
        fileRepository.deleteById( id );
    }

    public boolean existById(int id) {
        return fileRepository.existsById( id );
    }

    public boolean existByNombre(String nombre) {
        return fileRepository.existsByName(nombre);
    }
}
