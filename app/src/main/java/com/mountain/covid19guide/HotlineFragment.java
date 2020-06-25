package com.mountain.covid19guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HotlineFragment extends Fragment {

    static TextView call004;
    static TextView call005;
    static TextView call700;
    static TextView call868;
    static TextView call311;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView =  inflater.inflate(R.layout.fragment_hotline, container, false);

        call004 = rootView.findViewById(R.id.call004);
        call005 = rootView.findViewById(R.id.call005);
        call700 = rootView.findViewById(R.id.call700);
        call868 = rootView.findViewById(R.id.call868);
        call311 = rootView.findViewById(R.id.call311);

        return rootView;
    }




}
