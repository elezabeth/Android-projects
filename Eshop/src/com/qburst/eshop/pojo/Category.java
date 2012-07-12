package com.qburst.eshop.pojo;

public class Category   {


    private Integer id;
    private String name;
    private byte[] image;

   public Category() {
   }

   public Category(String name, byte[] image) {
      this.name = name;
      this.image = image;
   }
  
   public Integer getId() {
       return this.id;
   }
   
   public void setId(Integer id) {
       this.id = id;
   }
   public String getName() {
       return this.name;
   }
   
   public void setName(String name) {
       this.name = name;
   }
   public byte[] getImage() {
       return this.image;
   }
   
   public void setImage(byte[] image) {
       this.image = image;
   }

}