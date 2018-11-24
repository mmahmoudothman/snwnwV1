package com.snwnw.snwnw.presentation.presenters.interfaces;

import com.snwnw.snwnw.domain.models.service_cat_model;

/**
 * Created by fifi elshafie on 2/23/2018.
 */

public interface categoryPresenterListener {

   public void onCategoryClicked(service_cat_model model);
   public void onSwitchClicked (service_cat_model model , Boolean status,int pos) ;
}
