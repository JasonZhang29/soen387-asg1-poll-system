package com.pollsystem.model;

import java.security.MessageDigest;

public class User {

        private int userId;
        private String firstName;
        private String lastName;
        private String email;
        private String password;


        public User(){

        }

        public User(int userId, String firstName, String lastName, String email, String password) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {return email;}

        public void setEmail(String email) {this.email = email;}

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String doHashing(String password){
            try{
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] bytesOfMessage = md.digest();
                byte[] hash = md.digest(bytesOfMessage);
                StringBuilder sb = new StringBuilder();
                for(byte b : bytesOfMessage){
                    sb.append(String.format("%02x", b));
                }
                String pass = sb.toString();
                return pass;

            }catch (Exception e){
                e.printStackTrace();
            }
            return "";
        }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
