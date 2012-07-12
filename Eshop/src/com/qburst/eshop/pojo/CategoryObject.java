package com.qburst.eshop.pojo;

import java.util.List;

public class CategoryObject {
	private List<Category> category;
	public static CategoryObject categoryObject;

	public static CategoryObject getCategoryObject(){
		if( categoryObject == null){
			categoryObject = new CategoryObject();
			//return _classObject;
		}
		return categoryObject;
	}
	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

}
