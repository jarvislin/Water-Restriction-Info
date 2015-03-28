package com.jarvislin.waterrestrictioninfo;

import java.util.ArrayList;
import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.filippudak.ProgressPieView.ProgressPieView;
import com.jarvislin.waterrestrictioninfo.Util.ToolsHelper;
import com.jarvislin.waterrestrictioninfo.model.Reservoir;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, FetchTask.OnFetchListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private static FetchTask mFetchHomepageTask;
    private static FetchTask mFetchReservoirTask;
    private static ProgressBar mHomepageProgress;
    private static RecyclerView mHomepageList;
    private static RecyclerView mReservoirList;
    private static ProgressBar mReservoirProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


        mFetchHomepageTask = new FetchTask();
        mFetchReservoirTask = new FetchTask();
        mFetchHomepageTask.setOnFetchListener(this);
        mFetchReservoirTask.setOnFetchListener(this);

    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void OnHomepageFetchFinished() {
        mHomepageProgress.setVisibility(View.GONE);
        mHomepageList.setAdapter(new HomepageNewsAdapter(this, DataFetcher.getInstance().getHomepageNews()));
        if (DataFetcher.getInstance().getHomepageNews().size() == 0) {
            Toast.makeText(this, "訊息公告讀取失敗，請稍候再重試。", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void OnDetailFetchFinished() {

    }

    @Override
    protected void onDestroy() {

        Thread.interrupted();

        super.onDestroy();
    }

    @Override
    public void OnReservoirFetchFinished() {
        mReservoirProgress.setVisibility(View.GONE);
        mReservoirList.setAdapter(new ReservoirAdapter(this, DataFetcher.getInstance().getReservoir()));
        if (DataFetcher.getInstance().getReservoir().size() == 0) {
            Toast.makeText(this, "水庫資訊讀取失敗，請稍候再重試。", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;
            Bundle args = getArguments();
            int page = args.getInt(ARG_SECTION_NUMBER);

            switch (page) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_homepage, container, false);
                    mHomepageProgress = (ProgressBar) rootView.findViewById(R.id.homepage_progress);
                    mHomepageList = (RecyclerView) rootView.findViewById(R.id.homepage_list);
                    mHomepageList.setLayoutManager(new LinearLayoutManager(getActivity()));
                    if (ToolsHelper.isNetworkAvailable(getActivity())) {
                        mFetchHomepageTask.execute(ActionType.HOMEPAGE);
                    } else {
                        Toast.makeText(getActivity(), "未偵測到網路，請確認網路狀況。", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }

                    break;

                case 2:
                    rootView = inflater.inflate(R.layout.fragment_reservoir, container, false);
                    mReservoirProgress = (ProgressBar) rootView.findViewById(R.id.reservoir_progress);
                    mReservoirList = (RecyclerView) rootView.findViewById(R.id.reservoir_list);
                    mReservoirList.setLayoutManager(new LinearLayoutManager(getActivity()));
                    if (ToolsHelper.isNetworkAvailable(getActivity())) {
                        mFetchReservoirTask.execute(ActionType.RESERVOIR);
                    } else {
                        Toast.makeText(getActivity(), "未偵測到網路，請確認網路狀況。", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                    break;
            }
            return rootView;
        }
    }

}
