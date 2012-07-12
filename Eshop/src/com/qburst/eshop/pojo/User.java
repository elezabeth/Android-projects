package com.qburst.eshop.pojo;

public class User {


    private int id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String password;

   public User() {
   }

   public User(int id, String name, String phoneNumber, String emailAddress, String password) {
      this.id = id;
      this.name = name;
      this.phoneNumber = phoneNumber;
      this.emailAddress = emailAddress;
      this.password = password;
   }
  
   public int getId() {
       return this.id;
   }
   
   public void setId(int id) {
       this.id = id;
   }
   public String getName() {
       return this.name;
   }
   
   public void setName(String name) {
       this.name = name;
   }
   public String getPhoneNumber() {
       return this.phoneNumber;
   }
   
   public void setPhoneNumber(String phoneNumber) {
       this.phoneNumber = phoneNumber;
   }
   public String getEmailAddress() {
       return this.emailAddress;
   }
   
   public void setEmailAddress(String emailAddress) {
       this.emailAddress = emailAddress;
   }
   public String getPassword() {
       return this.password;
   }
   
   public void setPassword(String password) {
       this.password = password;
   }




}


