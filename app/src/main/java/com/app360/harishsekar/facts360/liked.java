package com.app360.harishsekar.facts360;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by harishsekar on 9/9/17.
 */

public class liked extends Fragment {


//  static ArrayList<String> favourite_facts_list = new ArrayList<>();
    private static int index = 0;

    ArrayList<String> favourite_facts_list = new ArrayList<>();
    private static LocalFavouriteDatabaseHelper localFavouriteDatabaseHelper_1 ;

    TextView fav_fact_display,Actual_Facts_from_liked_to_home; ;
    ImageButton share_button,left_button,right_button,voice_button,Delete_Fav_fact_from_local_DB__button;
//    AdView FavAvtivity_adView;
    private TextToSpeech textToSpeech_object;
    private int textTOspeech_result;

    final String ADMOB_LIKE_APP_ID = "ca-app-pub-7359656895588552/4447639109";

    AdView adView ;
    AdRequest adRequest ;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {






        View view = inflater.inflate(R.layout.likesfacts_fragment, container, false);

        MobileAds.initialize(getContext(),ADMOB_LIKE_APP_ID);
        adView = (AdView) view.findViewById(R.id.like_AD);
        adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);
        //Object instantiation

       localFavouriteDatabaseHelper_1 = new LocalFavouriteDatabaseHelper(getActivity().getApplicationContext(),null,null,1);
        favourite_facts_list = localFavouriteDatabaseHelper_1.databaseToString();

        //Finding and MApping ID

        Actual_Facts_from_liked_to_home = (TextView) view.findViewById(R.id.home_actualfact_ID);
        fav_fact_display = (TextView) view.findViewById(R.id.fav_facts_text_ID);
        share_button = (ImageButton) view.findViewById(R.id.fav_share_ID);
        left_button = (ImageButton) view.findViewById(R.id.fav_leftarrow_ID);
        voice_button = (ImageButton) view.findViewById(R.id.fav_voice_ID);
        right_button = (ImageButton) view.findViewById(R.id.fav_rightarrow_ID);
        Delete_Fav_fact_from_local_DB__button = (ImageButton) view.findViewById(R.id.fav_delete_ID);

        //----------------------------------------------------------------------------------

        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share_intent= new Intent(Intent.ACTION_SEND);
                share_intent.setType("text/pain");

                String share_message = fav_fact_display.getText().toString();
                String share_message_body = "message body";
                share_intent.putExtra(Intent.EXTRA_SUBJECT,share_message);
                share_intent.putExtra(Intent.EXTRA_TEXT,share_message_body);
                startActivity(Intent.createChooser(share_intent,"Share the Facts using ..."));
            }
        });

        //----------------------------------------------------------------------------------

        textToSpeech_object = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){

                    textTOspeech_result = textToSpeech_object.setLanguage(Locale.getDefault());
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"Feature not avaliable in your Device :(",Toast.LENGTH_SHORT).show();
                }
            }
        });


        voice_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(textToSpeech_object != null){
                    textToSpeech_object.stop();
                }
                if(textTOspeech_result == TextToSpeech.LANG_NOT_SUPPORTED || textTOspeech_result == TextToSpeech.LANG_MISSING_DATA){

                    Toast.makeText(getActivity().getApplicationContext(),"Feature not avaliable in your Device :(",Toast.LENGTH_SHORT).show();

                }
                else{

                    textToSpeech_object.speak(fav_fact_display.getText().toString(), TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });

        //----------------------------------------------------------------------------------

        if(!favourite_facts_list.isEmpty() && favourite_facts_list.size()!=0) {

            fav_fact_display.setText(favourite_facts_list.get(0));
            if(index == (favourite_facts_list.size())-1 ) {
                index=0;
            }
            else {
                index++;
            }
        }
        else{
            fav_fact_display.setText("No favourites yet !!");
        }

        //----------------------------------------------------------------------------------

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favourite_facts_list.isEmpty()) {
                    if (index > 0) {
                        index--;
                    }
                    fav_fact_display.setText(favourite_facts_list.get(index));

                } else {

                    fav_fact_display.setText("No favourites yet !!");
                }
            }
        });

        //----------------------------------------------------------------------------------

        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favourite_facts_list.isEmpty()) {
                    if (index != favourite_facts_list.size() - 1) {
                        index++;
                    }
                    fav_fact_display.setText(favourite_facts_list.get(index));

                } else {
                    fav_fact_display.setText("No favourites yet !!");

                }
            }
        });

        //----------------------------------------------------------------------------------

        Delete_Fav_fact_from_local_DB__button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String GET_FAV_FACT_TO_DELETE = fav_fact_display.getText().toString();
                if(GET_FAV_FACT_TO_DELETE == "No favourites yet !!" && localFavouriteDatabaseHelper_1.databasehasFacts()) {
                    Toast.makeText(getActivity().getApplicationContext(),"NO Fav yet !!",Toast.LENGTH_LONG).show();
                }
                else{
                    if(localFavouriteDatabaseHelper_1.delete_from_Table(GET_FAV_FACT_TO_DELETE)){
                        Toast.makeText(getActivity().getApplicationContext(),"Fact Removed !!",Toast.LENGTH_LONG).show();
                       favourite_facts_list = localFavouriteDatabaseHelper_1.databaseToString();


                        if(!favourite_facts_list.isEmpty() && favourite_facts_list.size()!=0) {
                            index=0;
                            fav_fact_display.setText(favourite_facts_list.get(index));
                        }
                        else{
                            fav_fact_display.setText("No favourites yet !!");
                        }
                    }
                }

            }
        });






        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){


            favourite_facts_list = localFavouriteDatabaseHelper_1.databaseToString();

            if(!favourite_facts_list.isEmpty() && favourite_facts_list.size()!=0) {

                fav_fact_display.setText(favourite_facts_list.get(0));
                if(index == (favourite_facts_list.size())-1 ) {
                    index=0;
                }
                else {
                    index++;
                }
            }
            else{
                fav_fact_display.setText("No favourites yet !!");

            }
        }


    }






}
