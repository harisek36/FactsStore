package com.app360.harishsekar.facts360;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by harishsekar on 9/9/17.
 */



public class about extends Fragment {



    private ImageButton Button1;
    MainActivity mainActivity;
    ToggleButton toggle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.aboutus_fragment,container,false);


         toggle = (ToggleButton) view.findViewById(R.id.notification_toggleButton_ID);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    mainActivity.setTimerforNotification(21, 35);

                }
//
//                        // The toggle is disabled
//                    }
            }
        });
        return view;
    }


}
