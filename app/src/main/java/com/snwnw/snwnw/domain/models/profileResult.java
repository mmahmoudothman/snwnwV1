package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fifi elshafie on 2/6/2018.
 */

public class profileResult {
    @SerializedName("msg")
    String msg ;
    @SerializedName("user")
    User user ;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class  User{
        @SerializedName("id")
        int id ;
        @SerializedName("username")
        String username;
        @SerializedName("first_name")
        String first_name;
        @SerializedName("last_name")
        String last_name ;
        @SerializedName("email")
        String email;
        @SerializedName("mobile")
        String mobile;
        @SerializedName("image")
        String image;
        @SerializedName("bio")
        String bio;
        @SerializedName("home_country")
        String home_country ;
        @SerializedName("birthdate")
        String birthdate;
        @SerializedName("job")
        String job;
        @SerializedName("phone")
        String phone;
        @SerializedName("gender")
        String gender;
        @SerializedName("relgion")
        String  relgion;
        @SerializedName("address")
        String address;
        @SerializedName("created_at")
        String created_at;
        @SerializedName("updated_at")
        String updated_at;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getHome_country() {
            return home_country;
        }

        public void setHome_country(String home_country) {
            this.home_country = home_country;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getRelgion() {
            return relgion;
        }

        public void setRelgion(String relgion) {
            this.relgion = relgion;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }


}
