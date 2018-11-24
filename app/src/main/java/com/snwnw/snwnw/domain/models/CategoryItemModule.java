package com.snwnw.snwnw.domain.models;

import java.util.ArrayList;

/**
 * Created by elmalah on 3/7/2017.
 */

public class CategoryItemModule {
    public ArrayList<CategoryItem> categoryItems;
   public class CategoryItem{
        public int ItemCategory_Id;
        public int Category_Id;
        public String Description;
        public String Author;
        public String URLImage;
        public boolean IsPublished;
        public String Details;
        public String FullURLImage;
    }
}
