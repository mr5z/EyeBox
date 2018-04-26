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
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.adapters.BaseListAdapter;

import java.util.List;

import butterknife.BindView;

public abstract class ListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    interface ContextualMenuListener {
        boolean onRemove(int position);
    }

    static final int MENU_CONTEXT_DELETE_ID = 1;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.list_view)
    RecyclerView listView;

    @BindView(R.id.list_header)
    LinearLayout listHeader;

    @BindView(R.id.list_footer)
    LinearLayout listFooter;

    private ContextualMenuListener contextualMenuListener;

    @Override
    void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        configureHeader();
        configureFooter();

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(getAdapter());
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

    abstract BaseListAdapter getAdapter();

    @Override
    public void onRefresh() {

    }

    public void setContextualMenuListener(ContextualMenuListener contextualMenuListener) {
        this.contextualMenuListener = contextualMenuListener;
    }

    <Model> void addContextAction(ContextAction<Model> contextAction) {

//        BaseListAdapter adapter = getAdapter();
//        adapter.setOnLongClickListener((data) -> {
//            ActionMode actionMode = startActionMode(new ActionBarCallBack<>(contextAction));
//
//            adapter.notifyDataSetChanged();
//            return true;
//        });
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

    static class ContextAction<T> {
        interface Action<T> {
            boolean onClick(T data);
        }
        final Action<T> destructiveAction;
        final String title;
        public ContextAction(Action<T> destructiveAction, String title) {
            this.destructiveAction = destructiveAction;
            this.title = title;
        }
    }

    static class ActionBarCallBack<T> implements android.view.ActionMode.Callback {

        private final ContextAction<T> contextAction;

        public ActionBarCallBack(ContextAction<T> contextAction) {
            this.contextAction = contextAction;
        }

        @Override
        public boolean onCreateActionMode(android.view.ActionMode actionMode, Menu menu) {
            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu menu) {
            actionMode.setTitle(contextAction.title);
            return false;
        }

        @Override
        public boolean onActionItemClicked(android.view.ActionMode actionMode, MenuItem menuItem) {
            return contextAction.destructiveAction.onClick(null);
        }

        @Override
        public void onDestroyActionMode(android.view.ActionMode actionMode) {

        }

//        @Override
//        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            // TODO Auto-generated method stub
//            return false;
//        }
//
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            // TODO Auto-generated method stub
//            mode.getMenuInflater().inflate(R.menu.menu_contextual, menu);
//            return true;
//        }
//
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            // TODO Auto-generated method stub
//            mode.setTitle("Delete row");
//            return false;
//        }

    }
}
