package com.snwnw.snwnw.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class AboutAhmedFragment extends Fragment {

    public AboutAhmedFragment() {
        // Required empty public constructor
    }

    public static AboutAhmedFragment newInstance() {
        AboutAhmedFragment fragment = new AboutAhmedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   //     return inflater.inflate(R.layout.fragment_my_services, container, false);
        return null ;
    }

}
