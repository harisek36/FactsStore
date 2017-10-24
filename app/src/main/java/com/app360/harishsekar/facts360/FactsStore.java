package com.app360.harishsekar.facts360;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by harishsekar on 7/20/17.
 */

public class FactsStore extends ContextWrapper {

    BufferedReader reader;
    private static ArrayList<String> TrueFacts  = new ArrayList<String>();
    private static int all_Facts_index_position[] = {0,0,0,0,0,0,0,0};
    private static int category_position ;



    public InputStream file;


    private String fileName = "ALL";

    public FactsStore(Context activity_context) {
        super(activity_context);
        setFileName_and_initialFact("ALL",0);

//        try{
//
//            file = getResources().getAssets().open(fileName+".txt");
//
//            reader = new BufferedReader(new InputStreamReader(file));
//            String line = reader.readLine();
//            while(line != null){
//                TrueFacts.add(line);
//                line = reader.readLine();
//            }
//        } catch(IOException ioe){
//            ioe.printStackTrace();
//        }

    }

    public void setFileName_and_initialFact(String file_Name, int category_position) {




        this.fileName = file_Name;
        this.category_position = category_position;


        try{
            TrueFacts.clear();
            file = getResources().getAssets().open(fileName+".txt");

            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                TrueFacts.add(line);
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }



    }

    public int[] get_current_index_totalFacts(){

        int[] current_index = new int[2];

        current_index[0] = all_Facts_index_position[category_position];
        current_index[1] = TrueFacts.size();

        return  current_index;
    }

     public String setInitialFact(){
        return TrueFacts.get(all_Facts_index_position[category_position]);
    }


//    public int getRandomNumber(){
//
//        Random random = new Random();
//        int randomIndex = random.nextInt(TrueFacts.size());
//        return randomIndex;
//
//    }


    public String get_right_Fatcs() {

        Log.v(TAG,"index == in_right  A"+all_Facts_index_position[0]+all_Facts_index_position[1]+all_Facts_index_position[2]+all_Facts_index_position[3]+all_Facts_index_position[4]+all_Facts_index_position[5]+all_Facts_index_position[6]+all_Facts_index_position[7]);


        if((all_Facts_index_position[category_position] >= 0) && (all_Facts_index_position[category_position] < TrueFacts.size()-1)){

            Log.v(TAG,"index == in_right_yes  >=0 && t.size()-1");
            all_Facts_index_position[category_position]++;
            Log.v(TAG,"index == in_right B "+all_Facts_index_position[0]+all_Facts_index_position[1]+all_Facts_index_position[2]+all_Facts_index_position[3]+all_Facts_index_position[4]+all_Facts_index_position[5]+all_Facts_index_position[6]+all_Facts_index_position[7]);

            return TrueFacts.get(all_Facts_index_position[category_position]);

        }else{
            Log.v(TAG,"index == in_right_no");

            all_Facts_index_position[category_position] = 0;
            Log.v(TAG,"index == in_right C "+all_Facts_index_position[0]+all_Facts_index_position[1]+all_Facts_index_position[2]+all_Facts_index_position[3]+all_Facts_index_position[4]+all_Facts_index_position[5]+all_Facts_index_position[6]+all_Facts_index_position[7]);

            return TrueFacts.get(all_Facts_index_position[category_position]);
        }


    }

    public String get_left_Fatcs() {
        Log.v(TAG,"index == left D "+all_Facts_index_position[0]+all_Facts_index_position[1]+all_Facts_index_position[2]+all_Facts_index_position[3]+all_Facts_index_position[4]+all_Facts_index_position[5]+all_Facts_index_position[6]+all_Facts_index_position[7]);


        if(all_Facts_index_position[category_position]==0){

            Log.v(TAG,"index == in_left_yes  == 0");

            all_Facts_index_position[category_position] = TrueFacts.size() - 1;
            Log.v(TAG,"index == in_left E "+all_Facts_index_position[0]+all_Facts_index_position[1]+all_Facts_index_position[2]+all_Facts_index_position[3]+all_Facts_index_position[4]+all_Facts_index_position[5]+all_Facts_index_position[6]+all_Facts_index_position[7]);

            return TrueFacts.get(all_Facts_index_position[category_position]);
        }

        else{

            Log.v(TAG,"index == in_left_no   && t.size()-1");
            if(all_Facts_index_position[category_position]> 0) {
                Log.v(TAG,"index == in_left_no   >0 && t.size()-1");
                all_Facts_index_position[category_position]--;
                Log.v(TAG,"index == in_right F "+all_Facts_index_position[0]+all_Facts_index_position[1]+all_Facts_index_position[2]+all_Facts_index_position[3]+all_Facts_index_position[4]+all_Facts_index_position[5]+all_Facts_index_position[6]+all_Facts_index_position[7]);

            }
            Log.v(TAG,"index == in_right  G"+all_Facts_index_position[0]+all_Facts_index_position[1]+all_Facts_index_position[2]+all_Facts_index_position[3]+all_Facts_index_position[4]+all_Facts_index_position[5]+all_Facts_index_position[6]+all_Facts_index_position[7]);

            return TrueFacts.get(all_Facts_index_position[category_position]);


        }
    }
//        if(all_Facts_index_position[category_position] == 0){
//
//
//
//            all_Facts_index_position[category_position] = TrueFacts.size()-1;
//            return TrueFacts.get(all_Facts_index_position[category_position]);
//
//        } else{
//
//
//            all_Facts_index_position[category_position]--;
//            return TrueFacts.get(all_Facts_index_position[category_position]);
//
//
//        }








//    public int get_index(){
//        return current_random_index;
//    }


    public String get_Fact_at_position(int position){
        return TrueFacts.get(position);
    }


    public String search_string(String x){

        for(int index=0;index<TrueFacts.size();index++){
            if(TrueFacts.get(index).contains(x)){
                return TrueFacts.get(index);
            }
        }

         return "Requested Fact not Found";
    }


}
