package com.explore01.kakao;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.explore01.kakao.adapter.BoardTabPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class BoardTabFragment extends Fragment {
    private ViewPager mViewPager;
    public static int getCurrentTab() {
        return currentTab;
    }
    private static int currentTab;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentTab=0;
        View view = inflater.inflate(R.layout.fragment_board, container, false);
//        ((BoardActivity) getActivity()).setToolbarTitle("Board");

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.board_tab);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.addTab(tabLayout.newTab().setText("수업평가"));
        tabLayout.addTab(tabLayout.newTab().setText("중고거래"));
        tabLayout.addTab(tabLayout.newTab().setText("익명자유"));
        tabLayout.addTab(tabLayout.newTab().setText("분실물신고"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) view.findViewById(R.id.board_view_pager);
        BoardTabPageAdapter boardTabPageAdapter = new BoardTabPageAdapter(getParentFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(boardTabPageAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                currentTab = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}