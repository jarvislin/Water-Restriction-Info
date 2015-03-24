package com.jarvislin.waterrestrictioninfo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jarvislin.waterrestrictioninfo.model.DetailNews;

import java.util.HashMap;


public class DetailNewsActivity extends ActionBarActivity implements FetchTask.OnFetchListener {

    private TextView mContent;
    private LinearLayout mDetailLayout;
    private ProgressBar mDetailProgress;
    private FetchTask mFetchTask = new FetchTask();
    public static String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        //set actionbar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        //get url
        Intent intent = getIntent();
        link = intent.getStringExtra("link");

        //find views
        mContent = (TextView)findViewById(R.id.detail_content);
        mDetailProgress = (ProgressBar)findViewById(R.id.detail_progress);
        mDetailLayout = (LinearLayout)findViewById(R.id.detail_layout);

        mFetchTask.setOnFetchListener(this);
        mFetchTask.execute(ActionType.DETAIL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void OnHomepageFetchFinished() {

    }

    @Override
    public void OnDetailFetchFinished() {

        DetailNews detailNews = DataFetcher.getInstance().getDetailNews();
        if(null == detailNews.getDetail()){
            Toast.makeText(this, "訊息讀取失敗，請稍候再重試。", Toast.LENGTH_LONG).show();
        } else {
            mContent.setText(detailNews.getDetail());
        }

        HashMap<String, String> map = detailNews.getAttachment();
        for(String fileName : map.keySet()) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.cell_attachment, null);
            TextView tv = (TextView)v.findViewById(R.id.file_name);
            tv.setText(fileName);
            tv.setOnClickListener(clickFile(map.get(fileName)));
            mDetailLayout.addView(v);
        }
        mDetailProgress.setVisibility(View.GONE);
    }

    @Override
    public void OnReservoirFetchFinished() {

    }

    private View.OnClickListener clickFile(final String link) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.replace("..", "http://www.water.gov.tw")));
                startActivity(intent);
            }
        };
    }
}
