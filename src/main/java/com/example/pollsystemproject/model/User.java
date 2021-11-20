package com.example.pollsystemproject.model;
import java.security.MessageDigest;
public class User {

        private int userId;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String hash;

        public User(){

        }

        public User(int userId, String firstName, String lastName, String email, String password) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            hashPassword(password);
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

        public String getHash() { return hash; }

        public void setPassword(String password) {
            this.password = password;
        }

        public void hashPassword(String password) {
            try {
                byte[] bytesOfMessage = password.getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] hash = md.digest(bytesOfMessage);
                this.hash = String.format("%1$032X", hash);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", hashed password='" + hash + '\'' +
                '}';
    }
}
