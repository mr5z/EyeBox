package com.nkraft.eyebox.activities;

import android.content.Intent;
import android.view.View;

import com.nkraft.eyebox.R;

import butterknife.OnClick;

public class VisitsActivity extends BaseActivity {

    @OnClick(R.id.fab_select_client)
    void onFabClick(View view) {
        Intent intent = new Intent(this, ClientsActivity.class);
        intent.putExtra("isMakingSignature", true);
        startActivity(intent);
    }

    @Override
    int contentLayout() {
        return R.layout.activity_visits;
    }
}
