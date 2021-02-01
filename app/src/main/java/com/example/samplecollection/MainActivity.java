package com.example.samplecollection;

import com.example.config.AppConfig;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.util.*;

public class MainActivity extends ListActivity {

    public static String sGlobalVariable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppConfig.init(getBaseContext());
        Intent intent = getIntent();
        String path = intent.getStringExtra("com.example.samplecollection.Path");

        if (path == null) {
            path = "";
        }

        setListAdapter(new SimpleAdapter(this, getData(path),
                R.layout.view_simple_list_item_2, new String[] { "title", "classify", "memo", "folder" },
                new int[] { R.id.text1, R.id.text2, R.id.text3 , R.id.text4}));
        getListView().setTextFilterEnabled(true);
        sGlobalVariable = "GlobalVariable";
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("kihoon.kim", "onRestoreInstanceState() MainActivity");
    }

    protected List<Map<String, Object>> getData(String prefix) {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("android.intent.category.SAMPLE_CODE");

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;
        String prefixWithSlash = prefix;

        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals("com.example.samplecollection") == false) {
                continue;
            }
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;

            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {

                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];
                String memo = "";
                String classify="";

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    try {
                        String className = info.activityInfo.name;
                        Class <?> clazz = Class.forName(className);
                        if (clazz.isAnnotationPresent(MyMaker.class)) {
                            MyMaker clazzAnnot = clazz.getAnnotation(MyMaker.class);
                            memo = clazzAnnot.memo();
                            classify = clazzAnnot.classify();
                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    addItem(myData, nextLabel, classify, memo, "",activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel, "", "", "Folder" ,browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

        Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
                private final Collator   collator = Collator.getInstance();

                public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                    return collator.compare(map1.get("title"), map2.get("title"));
                }
            };

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, MainActivity.class);
        result.putExtra("com.example.samplecollection.Path", path);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String name, String classify, String memo, String folder, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("classify", classify);
        temp.put("memo", memo);
        temp.put("folder", folder);
        temp.put("intent", intent);
        data.add(temp);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);

        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }
}