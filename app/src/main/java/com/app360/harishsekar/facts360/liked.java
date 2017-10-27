package com.app360.harishsekar.facts360;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private static int index_left = 0;


     static ArrayList<String> favourite_facts_list = new ArrayList<>();
    private static LocalFavouriteDatabaseHelper localFavouriteDatabaseHelper_1 ;

    TextView fav_fact_display,Actual_Facts_from_liked_to_home; ;
    ImageButton share_button,left_button,right_button,voice_button,Delete_Fav_fact_from_local_DB__button;
//    AdView FavAvtivity_adView;
    private TextToSpeech textToSpeech_object;
    private int textTOspeech_result;

    ImageButton search_option_like;
    TextView current_index_like, total_index_like;

    EditText get_facts_number_like, search_fact_like;

    final String ADMOB_LIKE_APP_ID = "ca-app-pub-7359656895588552/4447639109";

    AdView adView ;
    AdRequest adRequest ;



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {






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

        search_option_like = view.findViewById(R.id.search_display_like_option_ID);
        current_index_like = view.findViewById(R.id.current_like_text_ID);
        total_index_like = view.findViewById(R.id.total_like_facts_ID);

        get_facts_number_like =  view.findViewById(R.id.get_like_facts_num_ID);
        search_fact_like = view.findViewById(R.id.search_like_fact_new_ID);




        update_index();
        search_fact_like.setTag("INVISIBLE");
        search_fact_like.setVisibility(View.INVISIBLE);
        get_facts_number_like.setTag("INVISIBLE");
        get_facts_number_like.setVisibility(View.INVISIBLE);
        search_option_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(search_fact_like.getTag() == "INVISIBLE") {

                    search_fact_like.setVisibility(View.VISIBLE);
                    search_fact_like.setTag("VISIBLE");
                    get_facts_number_like.setVisibility(View.VISIBLE);
                    get_facts_number_like.setTag("VISIBLE");
                    search_option_like.setImageResource(R.drawable.ic_youtube_searched_for_white_24dp);


                }else if(search_fact_like.getTag() == "VISIBLE")
                {
                    search_fact_like.setVisibility(View.INVISIBLE);
                    search_fact_like.setTag("INVISIBLE");
                    get_facts_number_like.setVisibility(View.INVISIBLE);
                    get_facts_number_like.setTag("INVISIBLE");
                    search_option_like.setImageResource(R.drawable.ic_search_white_24dp);


                }
            }
        });

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
                    Toast.makeText(getActivity().getApplicationContext(),"Feature not available in your Device :(",Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(getActivity().getApplicationContext(),"Feature not available in your Device :(",Toast.LENGTH_SHORT).show();

                }
                else{

                    textToSpeech_object.speak(fav_fact_display.getText().toString(), TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });

        //----------------------------------------------------------------------------------

        if(!favourite_facts_list.isEmpty() && favourite_facts_list.size()!=0) {

            fav_fact_display.setText(favourite_facts_list.get(index_left));

        }
        else{
            fav_fact_display.setText("No favourite Facts yet !!");
            current_index_like.setText(""+0);
            total_index_like.setText(""+0);

        }

        //----------------------------------------------------------------------------------

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!favourite_facts_list.isEmpty()){

                    if(index_left>0) {
                        index_left--;
                        fav_fact_display.setText(favourite_facts_list.get(index_left));

                    }

                    update_index();

                }else {
                    fav_fact_display.setText("No favourite Facts yet !!");
                    current_index_like.setText(""+0);
                    total_index_like.setText(""+0);

                }


            }
        });

        //----------------------------------------------------------------------------------

        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!favourite_facts_list.isEmpty()){

                    if(index_left != (favourite_facts_list.size()-1)){
                        index_left++;
                        fav_fact_display.setText(favourite_facts_list.get(index_left));

                    }
//                    else {
//                        index = 0;
//                        fav_fact_display.setText(favourite_facts_list.get(index));

//                    }
                    update_index();


                }else {
                    fav_fact_display.setText("No favourite Facts yet !!");
                    current_index_like.setText(""+0);
                    total_index_like.setText(""+0);

                }


            }
        });

        //----------------------------------------------------------------------------------

        Delete_Fav_fact_from_local_DB__button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String GET_FAV_FACT_TO_DELETE = fav_fact_display.getText().toString();
                if(GET_FAV_FACT_TO_DELETE == "No favourite Facts yet !!" && localFavouriteDatabaseHelper_1.databasehasFacts()) {
                    Toast.makeText(getActivity().getApplicationContext(),"No favourite Facts yet !!",Toast.LENGTH_LONG).show();
                    current_index_like.setText(""+0);
                    total_index_like.setText(favourite_facts_list.size());
                }
                else{
                    if(localFavouriteDatabaseHelper_1.delete_from_Table(GET_FAV_FACT_TO_DELETE)){
                        Toast.makeText(getActivity().getApplicationContext(),"Fact Removed !!",Toast.LENGTH_LONG).show();
                       favourite_facts_list = localFavouriteDatabaseHelper_1.databaseToString();


                        if(!favourite_facts_list.isEmpty() && favourite_facts_list.size()!=0) {
                            index_left=0;
                            fav_fact_display.setText(favourite_facts_list.get(index_left));
                            update_index();

                        }
                        else{
                            fav_fact_display.setText("No favourite Facts yet !!");
                            current_index_like.setText(""+0);
                            total_index_like.setText(""+0);

                        }
                    }


                }

            }
        });

        search_fact_like.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    if(event.getAction() == KeyEvent.ACTION_UP){

                        fav_fact_display.setText(search_string(search_fact_like.getText().toString()));


                        return  true;
                    }
                }
                return false;
            }
        });


        get_facts_number_like.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    if(event.getAction() == KeyEvent.ACTION_UP){

                        fav_fact_display.setText(getFactat(Integer.parseInt(get_facts_number_like.getText().toString())-1));


                        return  true;
                    }
                }
                return false;
            }
        });







        return view;
    }


//    void update_index__current_total(){
//
//
//        total_index_like.setText(favourite_facts_list.size());
//
//    }


    void update_index(){
        int num = index_left +1;
        int len = favourite_facts_list.size();

        current_index_like.setText(""+num);
        total_index_like.setText(""+len);

    }

    private String getFactat(int x){
        if(x>=0 && x<favourite_facts_list.size()){
            current_index_like.setText(""+(x+1));
            return favourite_facts_list.get(x);
        }
        return fav_fact_display.getText().toString();
    }




    public String search_string(String x){

        for(int index=0;index<favourite_facts_list.size();index++){
            if(favourite_facts_list.get(index).contains(x)){
                index_left =index;
                update_index();

                return favourite_facts_list.get(index);
            }
        }

        Toast.makeText(getActivity().getApplicationContext(),"Requested Fact not Found",Toast.LENGTH_LONG).show();


        return fav_fact_display.getText().toString() ;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){


            favourite_facts_list = localFavouriteDatabaseHelper_1.databaseToString();

            if(!favourite_facts_list.isEmpty() && favourite_facts_list.size()!=0) {

                fav_fact_display.setText(favourite_facts_list.get(0));
                if(index_left == (favourite_facts_list.size())-1 ) {
                    index_left=0;
                }
                else {
                    index_left++;
                }
            }
            else{
                fav_fact_display.setText("No favourite Facts yet !!");



            }
        }



    }






}
