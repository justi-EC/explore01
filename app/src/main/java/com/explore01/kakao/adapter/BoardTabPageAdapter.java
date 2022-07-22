package com.explore01.kakao.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.explore01.kakao.BoardAnonymousFragment;
import com.explore01.kakao.BoardClassEstimFragment;
import com.explore01.kakao.BoardLossFragment;
import com.explore01.kakao.BoardTradeFragment;

public class BoardTabPageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public BoardTabPageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new BoardClassEstimFragment();

            case 1:
                return new BoardTradeFragment();

            case 2:
                return new BoardAnonymousFragment();

            case 3:
                return new BoardLossFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

