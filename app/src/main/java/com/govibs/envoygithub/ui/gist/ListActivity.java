package com.govibs.envoygithub.ui.gist;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.govibs.envoygithub.R;
import com.govibs.envoygithub.ui.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Show a list inside a fragment.
 * Created by Vibhor on 12/8/17.
 */

public class ListActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.listContainer, ListFragment.newInstance())
                    .commit();
        }
    }
}
