package com.nkraft.eyebox.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;

import java.util.List;

import butterknife.BindView;

public abstract class ListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.list_view)
    RecyclerView listView;

    @BindView(R.id.list_header)
    LinearLayout listHeader;

    @BindView(R.id.list_footer)
    LinearLayout listFooter;

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        configureHeader();
        configureFooter();

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(buildAdapter());
        refreshLayout.setOnRefreshListener(this);
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

    @LayoutRes
    @Override
    int contentLayout() {
        return R.layout.activity_list;
    }

    @ColorInt
    int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    void setRefreshing(boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
    }

    void configureHeader() {
        List<Header> headers = headerList();
        if (headers == null)
            return;

        int padding = utils().dpToPx(4);
        listHeader.setPadding(padding, padding, padding, padding);
        for(Header header : headers) {
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

    void configureFooter() {
        List<Footer> footers = footerList();
        if (footers == null)
            return;

        for(Footer footer : footers) {
            RelativeLayout layout = new RelativeLayout(this);
            layout.setLayoutParams(new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setBackgroundColor(color(footer.backgroundColor));
            Button button = footer.button;
            footer.button.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            utils().makeRippledBackground(button);
            layout.addView(button);
            listFooter.addView(layout, new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        }
    }

    List<Header> headerList() {
        return null;
    }

    List<Footer> footerList() {
        return null;
    }

    abstract BaseListAdapter buildAdapter();

    @Override
    public void onRefresh() {

    }

    static class Header {
        String columnName;
        int columnNameId;
        Header(String columnName) {
            this.columnName = columnName;
        }
        Header(int columnNameId) {
            this.columnNameId = columnNameId;
        }
    }

    static class Footer {

        interface ClickListener {
            void onClick();
        }

        private final Button button;
        final ClickListener clickListener;
        @ColorRes
        final int backgroundColor;

        Footer(ListActivity activity,
               String buttonText,
               @IdRes int buttonId,
               @ColorRes int textColor,
               @ColorRes int backgroundColor,
               ClickListener clickListener) {
            this.backgroundColor = backgroundColor;
            this.clickListener = clickListener;
            button = new Button(activity);
            button.setId(buttonId);
            button.setText(buttonText);
            button.setTextColor(activity.color(textColor));
            button.setOnClickListener((v) -> clickListener.onClick());
        }

    }
}
