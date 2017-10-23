package com.app360.harishsekar.facts360;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by harishsekar on 7/20/17.
 */

public class FactsStore extends ContextWrapper {

    BufferedReader reader;
    private static ArrayList<String> TrueFacts  = new ArrayList<String>();

    home home_obj;
    private static int current_random_index;
    public InputStream file;


    private String fileName = "ALL";




    public FactsStore(Context activity_context) {
        super(activity_context);
        setFileName("ALL");

        try{

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

    public void setFileName(String file_Name) {
        this.fileName = file_Name;
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


    public int getRandomNumber(){

        Random random = new Random();
        int randomIndex = random.nextInt(TrueFacts.size());
        return randomIndex;

    }


    public String getFatcs() {

        this.current_random_index = getRandomNumber();
        return TrueFacts.get(current_random_index);
    }


    public int get_index(){
        return current_random_index;
    }


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
