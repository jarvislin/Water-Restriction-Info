package com.jarvislin.waterrestrictioninfo;

import android.os.AsyncTask;

/**
 * Created by Jarvis Lin on 2015/3/22.
 */
enum ActionType{
    HOMEPAGE, DETAIL, RESERVOIR
}

public class FetchTask extends AsyncTask<ActionType, Void, ActionType> {

    interface OnFetchListener {
        public void OnHomepageFetchFinished();
        public void OnDetailFetchFinished();
        public void OnReservoirFetchFinished();
    }

    private OnFetchListener onFetchListener;

    public void setOnFetchListener(OnFetchListener listener){
        onFetchListener = listener;
    }

    @Override
    protected ActionType doInBackground(ActionType[] params) {
        switch (params[0]){
            case HOMEPAGE:
                DataFetcher.getInstance().fetchHomepageNews();
                break;
            case DETAIL:
                break;
            case RESERVOIR:
                break;
        }

        return params[0];
    }

    @Override
    protected void onPostExecute(ActionType param){
        switch (param){
            case HOMEPAGE:
                onFetchListener.OnHomepageFetchFinished();
                break;
            case DETAIL:
                onFetchListener.OnDetailFetchFinished();
                break;
            case RESERVOIR:
                onFetchListener.OnReservoirFetchFinished();
                break;
        }
    }
}
