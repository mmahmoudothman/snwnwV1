package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by fifi elshafie on 6/29/2018.
 */

public class offerModel
{
    @SerializedName("msg")
    String msg ;
    @SerializedName("offers")
    ArrayList<offer>AllOffers  ;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<offer> getAllOffers() {
        return AllOffers;
    }

    public void setAllOffers(ArrayList<offer> allOffers) {
        AllOffers = allOffers;
    }

    public  class  offer {
        @SerializedName("id")
        int id ;
        @SerializedName("title")
        String title ;
        @SerializedName("message")
        String message ;
        @SerializedName("image")
        String image ;
        @SerializedName("duration")
         String duration ;
        @SerializedName("created_at")
        String created_at ;
        @SerializedName("place")
        place place ;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public offerModel.place getPlace() {
            return place;
        }

        public void setPlace(offerModel.place place) {
            this.place = place;
        }
    }
    public class place {
        @SerializedName("id")
        public  int id ;
        @SerializedName("longitude")
        public double longitude;
        @SerializedName("latitude")
        public  double latitude ;
        @SerializedName("logo")
        public String logo ;
        @SerializedName("address")
        public String address;
        @SerializedName("work_hours")
        public String work_hours ;
        @SerializedName("sub_category_id")
        public int sub_category_id ;
        @SerializedName("email")
        public String email ;
        @SerializedName("mobile")
        public String mobile ;
        @SerializedName("phone")
        public String phone ;
        @SerializedName("fb_page")
        public String fb_page;
        @SerializedName("created_at")
        public String created_at ;
        @SerializedName("title")
        public String title ;
        @SerializedName("description")
        public String description ;
        @SerializedName("is_favourite")
        public int is_favourite;
        @SerializedName("rate")
        public int rate ;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getWork_hours() {
            return work_hours;
        }

        public void setWork_hours(String work_hours) {
            this.work_hours = work_hours;
        }

        public int getSub_category_id() {
            return sub_category_id;
        }

        public void setSub_category_id(int sub_category_id) {
            this.sub_category_id = sub_category_id;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFb_page() {
            return fb_page;
        }

        public void setFb_page(String fb_page) {
            this.fb_page = fb_page;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getIs_favourite() {
            return is_favourite;
        }

        public void setIs_favourite(int is_favourite) {
            this.is_favourite = is_favourite;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }
    }
}
