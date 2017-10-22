package com.app360.harishsekar.facts360;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by harishsekar on 9/9/17.
 */

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentitlelist = new ArrayList<>();

//    private Map<Integer,String> mFrag;
//    private FragmentManager mFragManager;

    public void addFragment(Fragment fragment,String title){
        fragmentList.add(fragment);
        fragmentitlelist.add(title);
    }

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
//        mFrag = new HashMap<>();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentitlelist.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Object obj= super.instantiateItem(container, position);
//        if(obj instanceof Fragment){
//            Fragment f = (Fragment) obj;
//            String tag = f.getTag();
//            mFrag.put(position,tag);
//
//        }
//        return obj;
//    }
//
//    public Fragment getFrag(int position) {
//        String tag = mFrag.get(position);
//        if(tag == null){
//            return null;
//        }
//
//        return mFragManager.findFragmentByTag(tag);
//    }
}
