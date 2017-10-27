package com.app360.harishsekar.facts360;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import static android.content.Context.MODE_PRIVATE;

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
    EditText searchFact_text,fact_num;
    TextView current_index, Total_index;
    final String ADMOB_HOME_APP_ID = "ca-app-pub-7359656895588552/3709272501";




    AdView adView ;
    AdRequest adRequest ;

    public static final String MY_PREFS_NAME = "RetriveFile";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefacts_fragment,container,false);


        SharedPreferences sharedPref = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        editor.putString("current_category",factsStore.getFileName());
//        editor.putInt("current_index", Integer.parseInt(current_index.getText().toString()));
//        editor.putInt("current_Category_position", factsStore.getCategory_position());







        MobileAds.initialize(getActivity().getApplicationContext(),ADMOB_HOME_APP_ID);
        adView = (AdView) view.findViewById(R.id.home_AD);
        adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);
        //Object Creation
        factsStore = new FactsStore(getActivity().getApplicationContext());

        localFavouriteDatabaseHelper = new LocalFavouriteDatabaseHelper(getActivity().getApplicationContext(),null,null,1);



        ActualFactDisplay =  view.findViewById(R.id.home_actualfact_ID);
        share_button  =  view.findViewById(R.id.home_share_ID);
        left_button =  view.findViewById(R.id.home_leftarrow_ID);
        voice_button =  view.findViewById(R.id.home_voice_ID);
        right_button =  view.findViewById(R.id.home_rightarrow_ID);
        like_unlike_button =  view.findViewById(R.id.home_like_unlike_ID);
        spinner_Facts_Category = view.findViewById(R.id.spinner_ID);
        search_home_fact_Button = view.findViewById(R.id.searchFact_button_Home_ID);
        searchFact_text = view.findViewById(R.id.search_fact_text_ID);
        fact_num = view.findViewById(R.id.get_Fact_num_ID);

        current_index = view.findViewById(R.id.current_like_index_ID);
        Total_index = view.findViewById(R.id.Total_facts_ID);



        search_home_fact_Button.setImageResource(R.drawable.ic_search_white_24dp);


        searchFact_text.setTag("INVISIBLE");
        searchFact_text.setVisibility(View.INVISIBLE);
        fact_num.setTag("INVISIBLE");
        fact_num.setVisibility(View.INVISIBLE);
        search_home_fact_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchFact_text.getTag() == "INVISIBLE") {
                    searchFact_text.setVisibility(View.VISIBLE);
                    searchFact_text.setTag("VISIBLE");
                    fact_num.setTag("VISIBLE");
                    fact_num.setVisibility(View.VISIBLE);
                    search_home_fact_Button.setImageResource(R.drawable.ic_youtube_searched_for_white_24dp);


                }else if(searchFact_text.getTag() == "VISIBLE")
                {
                    searchFact_text.setVisibility(View.INVISIBLE);
                    searchFact_text.setTag("INVISIBLE");
                    fact_num.setTag("INVISIBLE");
                    fact_num.setVisibility(View.INVISIBLE);
                    search_home_fact_Button.setImageResource(R.drawable.ic_search_white_24dp);

                }
            }
        });



        fact_num.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    if(fact_num.getText().toString()!="") {
                        if (event.getAction() == KeyEvent.ACTION_UP) {
                            try {


                                ActualFactDisplay.setText(factsStore.get_Fact_at_position(Integer.parseInt(fact_num.getText().toString()) - 1));
                                current_index_Total = factsStore.get_current_index_totalFacts();
                                update_current_total_index_number();
                                return true;

                            } catch (NumberFormatException e) {

                            }
                        }
                    }
                }
                return false;
            }
        });

        searchFact_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    if(event.getAction() == KeyEvent.ACTION_UP){

                            ActualFactDisplay.setText(factsStore.search_string(searchFact_text.getText().toString()));
                            current_index_Total = factsStore.get_current_index_totalFacts();
                            update_current_total_index_number();

                            return true;

                    }
                }
                return false;
            }
        });



        update_current_total_index_number();

        spinner_Facts_Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.v(TAG,"from home "+spinner_Facts_Category.getSelectedItem().toString()+" "+position);

//                factsStore.setFileName_and_initialFact(spinner_Facts_Category.getSelectedItem().toString(),position);
                factsStore.setFileName_and_initialFact(spinner_Facts_Category.getSelectedItem().toString(),position);
                ActualFactDisplay.setText(factsStore.setInitialFact());

                current_index_Total = factsStore.get_current_index_totalFacts();

                Log.v(TAG," Cuurent index : " +current_index_Total[0]+ "/"+current_index_Total[1]);
                update_current_total_index_number();
                set_like_fact_star(ActualFactDisplay.getText().toString());






            };

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity().getApplicationContext(),"Nothing selected",Toast.LENGTH_SHORT).show();


            }
        });

        ActualFactDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActualFactDisplay.setText(factsStore.get_right_Fatcs());
                current_index_Total = factsStore.get_current_index_totalFacts();
                Log.v(TAG," Cuurent index : " +current_index_Total[0]+ "/"+current_index_Total[1]);
                update_current_total_index_number();
                set_like_fact_star(ActualFactDisplay.getText().toString());


            }
        });

        //----------------------------------------------------------------------------------


        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ActualFactDisplay.setText(factsStore.get_right_Fatcs());
                current_index_Total = factsStore.get_current_index_totalFacts();
                Log.v(TAG," Cuurent index : " +current_index_Total[0]+ "/"+current_index_Total[1]);
                update_current_total_index_number();
                set_like_fact_star(ActualFactDisplay.getText().toString());





            }
        });

        //----------------------------------------------------------------------------------

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActualFactDisplay.setText(factsStore.get_left_Fatcs());
                current_index_Total = factsStore.get_current_index_totalFacts();
                Log.v(TAG," Cuurent index : " +current_index_Total[0]+ "/"+current_index_Total[1]);
                update_current_total_index_number();
                set_like_fact_star(ActualFactDisplay.getText().toString());


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


            }
        });




        return view;
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
//            set_like_fact_star(ActualFactDisplay.getText().toString());
        }
    }

    void update_current_total_index_number(){
        int current = current_index_Total[0]+1;
        int total = current_index_Total[1];

        current_index.setText(""+current);
        Total_index.setText(""+total);
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
