package com.mosainfo.fstechapi.dto;

import javax.validation.constraints.NotBlank;

public class FileDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String img;
    @NotBlank
    private Boolean status;

    public FileDto() {
    }

    public FileDto(String name, String description, String img, Boolean status) {
        this.name = name;
        this.description = description;
        this.img = img;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
