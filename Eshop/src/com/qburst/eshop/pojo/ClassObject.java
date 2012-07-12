package com.qburst.eshop.pojo;

import java.util.List;

public class ClassObject {
private Product product;
private List<Product> productList;



public static ClassObject _classObject;

public static ClassObject getClassObject(){
	if( _classObject == null){
		_classObject = new ClassObject();
		//return _classObject;
	}
	return _classObject;
}

public Product getProduct() {
	return product;
}

public void setProduct(Product product) {
	this.product = product;
}
public List<Product> getProductList() {
	return productList;
}

public void setProductList(List<Product> productList) {
	this.productList = productList;
}
}
