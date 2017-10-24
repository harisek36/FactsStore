package com.app360.harishsekar.facts360;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by harishsekar on 9/9/17.
 */

public class home extends Fragment {

    boolean previousPosition = true;
    static int SpinnerPreviousPosition = 0;

    private static int current_back_button_index_position ;
    private ArrayList<Integer> back_button_buffer = new ArrayList<>();


    private TextToSpeech textToSpeech_object;
    private int textTOspeech_result;

    private FactsStore factsStore = null;
   public LocalFavouriteDatabaseHelper localFavouriteDatabaseHelper ;
    private static int[] current_index_Total = new int[2];


    ConstraintLayout home_constraintLayout;
    public static TextView ActualFactDisplay;
    ImageButton share_button,left_button,voice_button,right_button,like_unlike_button,search_home_fact_Button;
    Spinner spinner_Facts_Category;
    EditText searchFact_text;
    final String ADMOB_HOME_APP_ID = "ca-app-pub-7359656895588552/3709272501";




    static ArrayList<String> favourite_facts_list_forLiked= new ArrayList<>();

    AdView adView ;
    AdRequest adRequest ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefacts_fragment,container,false);

        MobileAds.initialize(getActivity().getApplicationContext(),ADMOB_HOME_APP_ID);
        adView = (AdView) view.findViewById(R.id.home_AD);
        adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);
        //Object Creation
        factsStore = new FactsStore(getActivity().getApplicationContext());

        localFavouriteDatabaseHelper = new LocalFavouriteDatabaseHelper(getActivity().getApplicationContext(),null,null,1);

        favourite_facts_list_forLiked = localFavouriteDatabaseHelper.databaseToString();


        ActualFactDisplay =  view.findViewById(R.id.home_actualfact_ID);
        share_button  =  view.findViewById(R.id.home_share_ID);
        left_button =  view.findViewById(R.id.home_leftarrow_ID);
        voice_button =  view.findViewById(R.id.home_voice_ID);
        right_button =  view.findViewById(R.id.home_rightarrow_ID);
        like_unlike_button =  view.findViewById(R.id.home_like_unlike_ID);
        spinner_Facts_Category = view.findViewById(R.id.spinner_ID);
        search_home_fact_Button = view.findViewById(R.id.searchFact_button_Home_ID);
        searchFact_text = view.findViewById(R.id.search_fact_text_ID);





        searchFact_text.setTag("INVISIBLE");
        searchFact_text.setVisibility(View.INVISIBLE);
        search_home_fact_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchFact_text.getTag() == "INVISIBLE") {
                    searchFact_text.setVisibility(View.VISIBLE);
                    searchFact_text.setTag("VISIBLE");

                }else if(searchFact_text.getTag() == "VISIBLE")
                {
                    searchFact_text.setVisibility(View.INVISIBLE);
                    searchFact_text.setTag("INVISIBLE");

                }
            }
        });




        searchFact_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    if(event.getAction() == KeyEvent.ACTION_UP){

//                        ActualFactDisplay.setText(factsStore.search_string(searchFact_text.getText().toString()));
                        return  true;
                    }
                }
                return false;
            }
        });




//        //For Actual Text
//        if(back_button_buffer.isEmpty()){
//
//
////            ActualFactDisplay.setText(factsStore.getFatcs());
////            back_button_buffer.add(factsStore.get_index());
////            set_like_fact_star(ActualFactDisplay.getText().toString());
////            current_back_button_index_position = back_button_buffer.size()-1;
//        }


        spinner_Facts_Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.v(TAG,"from home "+spinner_Facts_Category.getSelectedItem().toString()+" "+position);

//                factsStore.setFileName_and_initialFact(spinner_Facts_Category.getSelectedItem().toString(),position);
                factsStore.setFileName_and_initialFact(spinner_Facts_Category.getSelectedItem().toString(),position);
                ActualFactDisplay.setText(factsStore.setInitialFact());

                current_index_Total = factsStore.get_current_index_totalFacts();

                Log.v(TAG," Cuurent index : " +current_index_Total[0]+ "/"+current_index_Total[1]);
//                        back_button_buffer.add(factsStore.get_index());
//                        set_like_fact_star(ActualFactDisplay.getText().toString());
//                        current_back_button_index_position++;




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity().getApplicationContext(),"Nothing selected",Toast.LENGTH_SHORT).show();


            }
        });

//        ActualFactDisplay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(current_back_button_index_position == back_button_buffer.size()-1) {
//
//                    ActualFactDisplay.setText(factsStore.getFatcs());
//
////                    back_button_buffer.add(factsStore.get_index());
////                    set_like_fact_star(ActualFactDisplay.getText().toString());
////                    current_back_button_index_position++;
//
//                }else if(current_back_button_index_position <= back_button_buffer.size()-1 && !back_button_buffer.isEmpty()){
//                    current_back_button_index_position++;
//
//                    ActualFactDisplay.setText(factsStore.get_Fact_at_position(back_button_buffer.get(current_back_button_index_position)));
//                    set_like_fact_star(ActualFactDisplay.getText().toString());
//
//                }
//
//            }
//        });

        //----------------------------------------------------------------------------------


        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ActualFactDisplay.setText(factsStore.get_right_Fatcs());
                current_index_Total = factsStore.get_current_index_totalFacts();
                Log.v(TAG," Cuurent index : " +current_index_Total[0]+ "/"+current_index_Total[1]);




//                if(current_back_button_index_position == back_button_buffer.size()-1) {
//
//                    ActualFactDisplay.setText(factsStore.getFatcs());
//
//                    back_button_buffer.add(factsStore.get_index());
//                    set_like_fact_star(ActualFactDisplay.getText().toString());
//                    current_back_button_index_position++;
//
//                }else if(current_back_button_index_position <= back_button_buffer.size()-1 && !back_button_buffer.isEmpty()){
//                    current_back_button_index_position++;
//
//                    ActualFactDisplay.setText(factsStore.get_Fact_at_position(back_button_buffer.get(current_back_button_index_position)));
//                    set_like_fact_star(ActualFactDisplay.getText().toString());

//
//                }



            }
        });

        //----------------------------------------------------------------------------------

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActualFactDisplay.setText(factsStore.get_left_Fatcs());
                current_index_Total = factsStore.get_current_index_totalFacts();
                Log.v(TAG," Cuurent index : " +current_index_Total[0]+ "/"+current_index_Total[1]);



//                if(current_back_button_index_position != 0 && current_back_button_index_position < back_button_buffer.size()){
//
//                    current_back_button_index_position--;
//                    ActualFactDisplay.setText(factsStore.get_Fact_at_position(back_button_buffer.get(current_back_button_index_position)));
//                    set_like_fact_star(ActualFactDisplay.getText().toString());
//                }

            }
        });

        //----------------------------------------------------------------------------------

        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share_intent= new Intent(Intent.ACTION_SEND);
                share_intent.setType("text/pain");

                String share_message = ActualFactDisplay.getText().toString();
                String share_message_body = "message body";
                share_intent.putExtra(Intent.EXTRA_SUBJECT,share_message);
                share_intent.putExtra(Intent.EXTRA_TEXT,share_message_body);
                startActivity(Intent.createChooser(share_intent,"Share the Facts using ..."));
            }
        });

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

                    Toast.makeText(getActivity().getApplicationContext(),"Feature not avaliable in your Device :(",Toast.LENGTH_SHORT).show();

                }
                else{

                    textToSpeech_object.speak(ActualFactDisplay.getText().toString(), TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });

        like_unlike_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(like_unlike_button.getTag()=="unLiked"){
                    AddingFacts_to_localFavDB();
                }else if(like_unlike_button.getTag()=="Liked"){
                    if(localFavouriteDatabaseHelper.delete_from_Table(ActualFactDisplay.getText().toString())){
                        Toast.makeText(getActivity().getApplicationContext(),"Fact Removed !!",Toast.LENGTH_SHORT).show();
                        set_like_fact_star(ActualFactDisplay.getText().toString());
                    }
                }

                favourite_facts_list_forLiked = localFavouriteDatabaseHelper.databaseToString();

            }
        });




        return view;
    }



    void set_like_fact_star(String S){
        if(localFavouriteDatabaseHelper.isFacts_in_likedDB(S)){
            like_unlike_button.setImageResource(R.drawable.ic_grade_white_48dp);
            like_unlike_button.setTag("Liked");

        }
        else {
            like_unlike_button.setImageResource(R.drawable.ic_star_border_white_48dp);
            like_unlike_button.setTag("unLiked");

        }
    }


    public void AddingFacts_to_localFavDB(){
        FactsBroker factsBroker = new FactsBroker(ActualFactDisplay.getText().toString());
        if(localFavouriteDatabaseHelper.check_for_duplicate_insertion_of_Fact(factsBroker)){

            Toast.makeText(getActivity().getApplicationContext(),"Added to Favourites List !!",Toast.LENGTH_SHORT).show();

            set_like_fact_star(ActualFactDisplay.getText().toString());

        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Fact already Added !!",Toast.LENGTH_SHORT).show();
        }

    }


}
