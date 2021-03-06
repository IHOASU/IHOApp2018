package com.iho.asu_iho.DisplayDataFromJSON;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.iho.asu_iho.Utilities.AppController;
import com.iho.asu_iho.Comparators.NewsComparator;
import com.iho.asu_iho.Constants.FragmentFieldsMapping;
import com.iho.asu_iho.Model.News;
import com.iho.asu_iho.Utilities.JSONCache;
import com.iho.asu_iho.Utilities.JSONResourceReader;
import com.iho.asu_iho.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.iho.asu_iho.Constants.IHOConstants.FEATURED_NEWS_IDS;
import static com.iho.asu_iho.Constants.IHOConstants.FEATURED_NEWS_URL;
import static com.iho.asu_iho.Constants.IHOConstants.NEWS_DATE;
import static com.iho.asu_iho.Constants.IHOConstants.NEWS_DESC;
import static com.iho.asu_iho.Constants.IHOConstants.NEWS_ID;
import static com.iho.asu_iho.Constants.IHOConstants.NEWS_IMAGE;
import static com.iho.asu_iho.Constants.IHOConstants.NEWS_LINK;
import static com.iho.asu_iho.Constants.IHOConstants.NEWS_TITLE;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FeaturedNewsFragment extends ListFragment {

    private static final String TAG = "FeaturedNews";

    private ArrayList<String> newsTitle = new ArrayList<String>();
    protected HashMap<String, News> newsItems = new HashMap<String, News>();
    private boolean isContentChanged = false;
    private ArrayList<String> newsIds = new ArrayList<String>();

    private File path = null;
    private File file = null;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = null;
        try {
            v = inflater.inflate(
                    R.layout.fragment_featured_news, container, false);

            Context context = v.getContext();
            path = context.getFilesDir();
            file = new File(path, "featured_news.json");

            Log.i(TAG, "fetching Contents...");
            if (JSONCache.fNewsItems.size() == 0) {
                //Cache Empty, Fetch  News Objects
                Log.i(TAG, "Cache Empty, Fetch  News Objects...");
                getNewsObjectJson();
            } else {
                //Cache not empty, checking if contents are modified
                Log.i(TAG, "Cache not empty, checking if contents are modified...");
                getNewsIdsJSON();
            }
        } catch (Exception e) {

        }

        return v;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        try {
            Intent i = new Intent(this.getActivity(), ViewActivity.class);
            String name = newsTitle.get(position);
            News news = newsItems.get(name);
            i.putExtra(FragmentFieldsMapping.KEY_NEWS_TITLE.getColumnName(), name);
            i.putExtra(FragmentFieldsMapping.KEY_NEWS_IMAGE.getColumnName(), news.getImg());
            i.putExtra(FragmentFieldsMapping.KEY_NEWS_LINK.getColumnName(), news.getNewsLink());
            i.putExtra(FragmentFieldsMapping.KEY_NEWS_TEXT.getColumnName(), news.getText());
            i.putExtra("ViewNeeded", "FeaturedNews");
            startActivity(i);
        } catch (Exception e) {
            Log.e(TAG, "onListItemClick: Error=" + e.getMessage());
        }

    }

    private void getNewsObjectJson() {
        Log.i(TAG, "getNewsObjectJson");
        isContentChanged = false;
        JsonArrayRequest request = new JsonArrayRequest(FEATURED_NEWS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray result) {

                        Log.i(TAG, "onResponse: Result = " + result.toString());
                        parseJSONResult(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: Error= " + error);
                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                        fetchJSONRaw();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseJSONResult(JSONArray jsonArray) {
        try {
            Log.i(TAG, "writing JSONArray to file storage");
            Files.write(jsonArray.toString().getBytes(), file);

            Log.i(TAG, "parseJSONResult");
            String id = null, title = null, newsDesc = null, newsLink = null, image = null, dateStr = null;
            JSONCache.fNewsItems.clear();
            JSONCache.fNewsTitle.clear();
            JSONCache.fNewsIds.clear();
            newsItems.clear();
            newsTitle.clear();
            newsIds.clear();

            List<News> newsList = new ArrayList<News>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject news = jsonArray.getJSONObject(i);

                if (!news.isNull(NEWS_ID)) {
                    id = news.getString(NEWS_ID);
                }

                if (!news.isNull(NEWS_TITLE)) {
                    title = news.getString(NEWS_TITLE);
                }

                if (!news.isNull(NEWS_DESC)) {
                    newsDesc = news.getString(NEWS_DESC);
                }

                if (!news.isNull(NEWS_LINK)) {
                    newsLink = news.getString(NEWS_LINK);
                }

                if (!news.isNull(NEWS_IMAGE)) {
                    image = news.getString(NEWS_IMAGE);
                }

                if (!news.isNull(NEWS_DATE)) {
                    dateStr = news.getString(NEWS_DATE);
                }


                News n = new News();
                n.setId(id);
                n.setTitle(title);
                n.setText(newsDesc);
                n.setImg(Base64.decode(image, Base64.DEFAULT));
                n.setNewsLink(newsLink);
                n.setCreationDate(new Date(dateStr));

                Log.i(TAG, i + ": " + n.toString());

                newsList.add(n);
                newsItems.put(title, n);
            }

            Collections.sort(newsList, Collections.<News>reverseOrder(new NewsComparator()));
            for (News news : newsList) {
                newsTitle.add(news.getTitle());
                newsIds.add(news.getId());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "Updating local cache...");
            JSONCache.fNewsItems = (HashMap<String, News>) newsItems.clone();
            JSONCache.fNewsTitle = (ArrayList<String>) newsTitle.clone();
            JSONCache.fNewsIds = (ArrayList<String>) newsIds.clone();


        } catch (JSONException e) {

            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "parseJSONResult: Error=" + e.getMessage());
        }
    }

    private void getNewsIdsJSON() {
        Log.i(TAG, "getNewsIdsJSON");

        JsonArrayRequest request = new JsonArrayRequest(FEATURED_NEWS_IDS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray result) {

                        Log.i(TAG, "onResponse: Result = " + result.toString());
                        parseJSONIDResult(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: Error= " + error);
                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                        getJSONCache();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseJSONIDResult(JSONArray jsonArray) {
        try {

            Log.i(TAG, "parseJSONIDResult");

            String id = null;

            List<String> newsIDs = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject news = jsonArray.getJSONObject(i);

                if (!news.isNull(NEWS_ID)) {
                    id = news.getString(NEWS_ID);
                }

                newsIDs.add(id);
            }

            ArrayList<String> oldListIds = (ArrayList<String>) JSONCache.fNewsIds.clone();
            if (oldListIds.size() == 0) {
                Log.i(TAG, "Local IdList is empty");
                isContentChanged = true;
            } else if (oldListIds.size() != newsIDs.size()) {
                Log.i(TAG, "Local IdList Size: " + oldListIds.size());
                Log.i(TAG, "Server IdList Size: " + newsIDs.size());
                isContentChanged = true;
            } else {
                for (String i: newsIDs) {
                    if (!oldListIds.contains(i)) {
                        isContentChanged = true;
                        break;
                    }
                }
            }
            if (isContentChanged) {
                Log.i(TAG, "Content Changed on server, fetching from server...");
                getNewsObjectJson();
            } else {
                Log.i(TAG, "Content not Changed on server, fetching from local cache...");
                getJSONCache();
            }

        } catch (JSONException e) {

            isContentChanged = false;
            getJSONCache();

            Log.e(TAG, "parseJSONIDResult: Error=" + e.getMessage());

        } catch (Exception e) {

            isContentChanged = false;
            getJSONCache();

            Log.e(TAG, "parseJSONIDResult: Error=" + e.getMessage());
        }

    }

    public void getJSONCache() {
        try {
            Log.i(TAG, "getJSONCache");
            newsItems.clear();
            newsTitle.clear();

            newsItems = (HashMap<String, News>) JSONCache.fNewsItems.clone();
            List<News> newsList = new ArrayList<>(JSONCache.fNewsItems.values());
            Collections.sort(newsList, Collections.<News>reverseOrder(new NewsComparator()));
            for (News news: newsList) {
                newsTitle.add(news.getTitle());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "getJSONCache: Error=" + e.getMessage());
        }

    }

    private void fetchJSONRaw(){
        try {

            JSONCache.fNewsItems.clear();
            JSONCache.fNewsTitle.clear();
            JSONCache.fNewsIds.clear();
            newsItems.clear();
            newsTitle.clear();
            newsIds.clear();
            String contents = null;

            if (file.length() == 0) {
                Log.i(TAG,"File storage empty, fetching from resources...");
                JSONResourceReader jsonResourceReader = new JSONResourceReader(getResources(), R.raw.featurednewsobjects);
                contents = jsonResourceReader.jsonString;
            } else {
                Log.i(TAG, "fetching JSON from filestorage");
                contents = Files.toString(file, Charset.forName("UTF-8"));
            }

            Gson gson = new Gson();
            News[] newsArray = gson.fromJson(contents, News[].class);
            List<News> newsList = new ArrayList<>();
            for (News news: newsArray) {
                news.setImg(Base64.decode(news.getImage(), Base64.DEFAULT));
                news.setCreationDate(new Date(news.getDate()));
                Log.i(TAG,news.toString());
                newsList.add(news);
                newsItems.put(news.getTitle(),news);
            }

            Collections.sort(newsList, Collections.<News>reverseOrder(new NewsComparator()));
            for (News news: newsList) {
                newsTitle.add(news.getTitle());
                newsIds.add(news.getId());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsTitle);
            this.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.i(TAG, "Updating local cache...");
            JSONCache.fNewsItems = (HashMap<String, News>) newsItems.clone();
            JSONCache.fNewsTitle = (ArrayList<String>) newsTitle.clone();
            JSONCache.fNewsIds = (ArrayList<String>) newsIds.clone();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "fetchJSONRaw: Error= " + e.getMessage());
        }
    }


}
