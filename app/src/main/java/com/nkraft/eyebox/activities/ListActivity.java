package com.nkraft.eyebox.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;

import java.util.List;

import butterknife.BindView;

public abstract class ListActivity extends BaseActivity {

    @BindView(R.id.list_view)
    RecyclerView listView;

    @BindView(R.id.list_header)
    LinearLayout listHeader;

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);

        List<Header> headers = headerList();
        if (headers != null) {
            for(Header header : headers) {
                int padding = utils().dpToPx(4);
                listHeader.setPadding(padding, padding, padding, padding);
                listHeader.addView(new android.support.v7.widget.AppCompatTextView(this) {{
                    setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    if (header.columnName != null) {
                        setText(header.columnName);
                    }
                    else if (header.columnNameId > 0) {
                        setText(header.columnNameId);
                    }
                    setTextColor(color(android.R.color.white));
                    setGravity(Gravity.CENTER);
                }});
            }
        }

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(buildAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_searchable, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            if (searchView != null) {
                assert searchManager != null;
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    int contentLayout() {
        return R.layout.activity_list;
    }

    @ColorInt
    int color(int resId) {
        return getResources().getColor(resId);
    }

    List<Header> headerList() {
        return null;
    }

    abstract BaseListAdapter buildAdapter();

    public static class Header {
        public String columnName;
        public int columnNameId;
        Header(String columnName) {
            this.columnName = columnName;
        }
        Header(int columnNameId) {
            this.columnNameId = columnNameId;
        }
    }
}
