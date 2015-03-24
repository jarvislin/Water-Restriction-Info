package com.jarvislin.waterrestrictioninfo;

import android.util.Log;

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
    private DetailNews detailNews;
    private ArrayList<Reservoir> reservoir = new ArrayList<Reservoir>();
    private int retryHomepage = 0;
    private int retryReservoir = 0;
    private int retryDetail = 0;
    private String tempLink;

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
                if (aTags.get(i).attr("href").contains("01a_main.asp")) {
                    continue;
                } else {
                    HomepageNews temp = new HomepageNews();
                    temp.setTitle(aTags.get(i).text());
                    temp.setLink(aTags.get(i).attr("href"));
                    homepageNews.add(temp);
                }
            }

        } catch (Exception ex) {
            if (retryHomepage < 3) {
                fetchHomepageNews();
                retryHomepage++;
            } else {
                ex.printStackTrace();
                retryHomepage = 0;
            }
        }
    }

    public ArrayList<HomepageNews> getHomepageNews() {
        return homepageNews;
    }

    public void fetchDetailNews(String link) {

        tempLink = link;

        try {
            Document doc = Jsoup.connect("http://www.water.gov.tw/subject/" + link).get();
            Elements rootTdTags = doc.getElementsByAttributeValue("summary", "內容顯示區").select("td");
            Elements trTags = rootTdTags.select("tr").attr("valign", "top");
            Elements tdTag = trTags.select("td").not("td[width]").not("td[class]").not("td[valign=top]");
            Elements aTags = tdTag.select("a");

            detailNews = new DetailNews();
            detailNews.setDetail(tdTag.get(0).text().split("附加檔案")[0]);

            for (int i = 0; i < aTags.size() - 1; i++) {
                if (aTags.get(i).attr("href").contains("01a_main.asp")) {
                    continue;
                } else {
                    detailNews.putAttachment(aTags.get(i).text(), aTags.get(i).attr("href"));
                }
            }

        } catch (Exception ex) {
            if (retryDetail < 3) {
                fetchDetailNews(tempLink);
                retryDetail++;
            } else {
                ex.printStackTrace();
                retryDetail = 0;
            }
        }
    }

    public DetailNews getDetailNews() {
        return detailNews;
    }

    public void fetchReservoir() {
        try {
            Document doc = Jsoup.connect("http://fhy.wra.gov.tw/ReservoirPage_2011/StorageCapacity.aspx").get();
            Elements tableTags = doc.getElementsByAttributeValue("class", "list nowrap").select("table");
            Elements tdTags = tableTags.select("td").not("td[colspan]");

            for (int i = 0; i < tdTags.size(); i += 12) {
                if (i + 12 <= tdTags.size() - 1) {
                    if ((i + 11) % 12 == 11 && tdTags.get(i + 11).text().equals("--")) {
                        continue;
                    } else {
                        Reservoir temp = new Reservoir();
                        temp.setName(tdTags.get(i).text());
                        temp.setTime(tdTags.get(i + 8).text());
                        temp.setDifferentialLevel(tdTags.get(i + 6).text());
                        temp.setCapacity(Float.valueOf(tdTags.get(i + 11).text().replace(" %", "")));
                        reservoir.add(temp);
                    }
                }
            }
        } catch (Exception ex) {
            if (retryReservoir < 3) {
                fetchReservoir();
                retryReservoir++;
            } else {
                ex.printStackTrace();
                retryReservoir = 0;
            }
        }
    }

    public ArrayList<Reservoir> getReservoir() {
        return reservoir;
    }
}
