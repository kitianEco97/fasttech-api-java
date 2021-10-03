package com.mosainfo.fstechapi.controller;

import com.mosainfo.fstechapi.dto.FileDto;
import com.mosainfo.fstechapi.dto.Mensaje;
import com.mosainfo.fstechapi.entity.File;
import com.mosainfo.fstechapi.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "http://localhost:4200")
public class FileController {

    @Autowired
    FileService fileService;

    @ApiOperation("Muestra una lista de productos")
    @GetMapping("/list")
    public ResponseEntity<List<File>> getAll() {
        List<File> list = fileService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiIgnore
    @GetMapping("/detail/{id}")
    public ResponseEntity<File> getById(@PathVariable("id") Integer id) {
        if (!fileService.existById( id ))
            return new ResponseEntity( new Mensaje( "Ese producto no existe" ), HttpStatus.NOT_FOUND);
        File file = fileService.getOne( id ).get();
        return new ResponseEntity( file, HttpStatus.OK );
    }

    @ApiIgnore
    @GetMapping("/detailname/{name}")
    public ResponseEntity<File> getByNombre(@PathVariable("name")String name) {
        if (!fileService.existByNombre( name ))
            return new ResponseEntity( new Mensaje( "Ese producto no existe" ), HttpStatus.NOT_FOUND );
        File file = fileService.getByNombre( name ).get();
        return new ResponseEntity( file, HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ADMIN')" )
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody FileDto fileDto) {
        if (fileService.existByNombre( fileDto.getName()))
            return new ResponseEntity( new Mensaje( "Ese nombre de producto ya existe" ), HttpStatus.BAD_REQUEST );
        if (StringUtils.isBlank( fileDto.getName() ))
            return new ResponseEntity( new Mensaje( "El nombre del producto es obligatorio" ), HttpStatus.BAD_REQUEST );
        if (StringUtils.isBlank( fileDto.getDescription() ) )
            return new ResponseEntity( new Mensaje( "La descripción es obligatoria" ), HttpStatus.BAD_REQUEST );
        /*if (StringUtils.isBlank( fileDto.getImg() ))
            return new ResponseEntity( new Mensaje( " es obligatorio" ), HttpStatus.BAD_REQUEST );*/

        File file = new File(fileDto.getName(), fileDto.getDescription(), fileDto.getImg(), fileDto.getStatus());
        fileService.save( file );
        return new ResponseEntity(new Mensaje( "Producto creado" ), HttpStatus.CREATED);
    }

    @PreAuthorize( "hasRole('ADMIN')" )
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody FileDto fileDto) {
        if (!fileService.existById( id ))
            return new ResponseEntity( new Mensaje( "Ese producto no existe" ), HttpStatus.NOT_FOUND);
        if (fileService.existByNombre( fileDto.getName()) && fileService.getByNombre( fileDto.getName()).get().getId() != id)
            return new ResponseEntity( new Mensaje( "Ese nombre de producto ya existe" ), HttpStatus.BAD_REQUEST );
        if (StringUtils.isBlank( fileDto.getName() ))
            return new ResponseEntity( new Mensaje( "El nombre del producto es obligatorio" ), HttpStatus.BAD_REQUEST );

        File file = fileService.getOne( id ).get();
        file.setName(fileDto.getName());
        file.setDescription(fileDto.getDescription());
        file.setImg(fileDto.getImg());
        file.setStatus(fileDto.getStatus());

        fileService.save( file );
        return new ResponseEntity( new Mensaje( "Producto actualizado" ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ADMIN')" )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!fileService.existById( id ))
            return new ResponseEntity( new Mensaje( "Ese producto no existe" ), HttpStatus.NOT_FOUND);
        fileService.delete( id );
        return new ResponseEntity( new Mensaje( "Producto eliminado correctanente" ), HttpStatus.OK );
    }
}
