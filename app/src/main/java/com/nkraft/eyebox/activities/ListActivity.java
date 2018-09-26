package com.nkraft.eyebox.activities;

import android.app.SearchManager;
import android.content.Context;
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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.models.IModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

public abstract class ListActivity<TModel extends IModel>
        extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.list_view)
    RecyclerView listView;

    @BindView(R.id.list_header)
    LinearLayout listHeader;

    @BindView(R.id.list_footer)
    LinearLayout listFooter;

    private List<TModel> temp;
    private List<TModel> dataList = new ArrayList<>();
    private BaseListAdapter adapter;

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        configureHeader();
        configureFooter();

        adapter = initializeAdapter();
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
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
                searchView.setOnQueryTextListener(this);
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

        int padding = views().dpToPx(4);
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
            Button button = new Button(this);
            button.setId(footer.buttonId);
            button.setText(footer.buttonText);
            button.setTextColor(color(footer.textColor));
            button.setOnClickListener((v) -> footer.clickListener.onClick());
            button.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            views().makeRippledBackground(button);
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

    void addData(TModel model) {
        dataList.add(model);
    }

    void setDataList(List<TModel> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
    }

    List<TModel> getDataList() {
        return dataList;
    }

    void clearDataList() {
        dataList.clear();
    }

    TModel getDataAt(int index) {
        return dataList.get(index);
    }

    TModel removeDataAt(int index) {
        return dataList.remove(index);
    }

    boolean removeData(TModel model) {
        return dataList.remove(model);
    }

    int getIndexOf(TModel data) {
        return dataList.indexOf(data);
    }

    boolean isDataListEmpty() {
        return dataList.isEmpty();
    }

    void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    void notifyItemRemoved(int position) {
        adapter.notifyItemRemoved(position);
    }

    abstract BaseListAdapter initializeAdapter();

    @Override
    public void onRefresh() {
        setRefreshing(false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (adapter == null)
            return false;

        boolean isEmpty = TextUtils.isEmpty(newText);
        if (!isEmpty) {
            newText = newText.toLowerCase();
            if (temp == null)
                temp = new ArrayList<>(dataList);
            Iterator<TModel> iterator = dataList.iterator();
            while (iterator.hasNext()) {
                TModel model = iterator.next();
                String[] searchField = getSearchableFields(model);
                if (searchField == null)
                    continue;

                boolean hasMatch = false;
                for (String field : searchField) {
                    if (TextUtils.isEmpty(field))
                        continue;

                    if (field.toLowerCase().contains(newText)) {
                        hasMatch = true;
                        break;
                    }
                }
                if (!hasMatch) {
                    iterator.remove();
                }
            }
            adapter.notifyDataSetChanged();
        }
        else {
            if (temp != null && !temp.isEmpty()) {
                dataList.clear();
                dataList.addAll(temp);
                adapter.notifyDataSetChanged();
            }
        }
        return true;
    }

    String[] getSearchableFields(TModel model) {
        return null;
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

        final ClickListener clickListener;
        @ColorRes
        final int backgroundColor;
        @IdRes
        final int buttonId;
        @ColorRes
        final int textColor;
        final String buttonText;

        Footer(String buttonText,
               @IdRes int buttonId,
               @ColorRes int textColor,
               @ColorRes int backgroundColor,
               ClickListener clickListener) {
            this.backgroundColor = backgroundColor;
            this.clickListener = clickListener;
            this.buttonId = buttonId;
            this.textColor = textColor;
            this.buttonText = buttonText;
        }
    }

}