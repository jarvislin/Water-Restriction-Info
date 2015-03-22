package com.jarvislin.waterrestrictioninfo;

import com.jarvislin.waterrestrictioninfo.model.DetailNews;
import com.jarvislin.waterrestrictioninfo.model.HomepageNews;
import com.jarvislin.waterrestrictioninfo.model.Reservoir;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Jarvis Lin on 2015/3/22.
 */
public class DataFetcher {
    private ArrayList<HomepageNews> homepageNews = new ArrayList<HomepageNews>();
    private ArrayList<DetailNews> detailNews = new ArrayList<DetailNews>();
    private ArrayList<Reservoir> reservoir = new ArrayList<Reservoir>();

    private DataFetcher() {
    }

    private static DataFetcher mFetcher;

    public static DataFetcher getInstance() {
        if (null == mFetcher) {
            mFetcher = new DataFetcher();
        }
        return mFetcher;
    }

    public void fetchHomepageNews() {

        try {
            Document doc = Jsoup.connect("http://www.water.gov.tw/subject/index.asp").get();
            Elements tdTags = doc.getElementsByAttributeValue("summary", "內容顯示區").select("td");
            Elements aTags = tdTags.select("a").not("a[title]").not("a[onMouseOut]").not("a[class]").not("a[alt]");

            for (int i = 0; i < aTags.size(); i++) {
                HomepageNews temp = new HomepageNews();
                temp.setTitle(aTags.get(i).text());
                temp.setLink(aTags.get(i).attr("href"));
                homepageNews.add(temp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<HomepageNews> getHomepageNews() {
        return homepageNews;
    }
}
