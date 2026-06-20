package com.example.SmartComplaintRouter.dto;

import org.springframework.web.multipart.MultipartFile;

public class ComplaintRequest {

    private String description;
    private MultipartFile image;
    private Long categoryId;

    // getters & setters

    public String getDescription() {
        return description;
    }

    public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public Long getCategoryId() {
        return categoryId;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
