package com.govibs.envoygithub.ui.owner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.govibs.core.data.model.GitUserModel;
import com.govibs.envoygithub.R;
import com.govibs.envoygithub.ui.base.BaseActivity;

/**
 * Created by Vibhor on 12/10/17.
 */

public class OwnerActivity extends BaseActivity {

    private static final String EXTRA_OWNER = "extra_owner";

    public static Intent createOwnerActivityIntent(@NonNull Context context, @NonNull GitUserModel gitUserModel) {
        Intent intent = new Intent(context, OwnerActivity.class);
        intent.putExtra(EXTRA_OWNER, gitUserModel);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GitUserModel gitUserModel = getIntent().getParcelableExtra(EXTRA_OWNER);
        if (gitUserModel == null) {
            throw new IllegalArgumentException("This can be handled better..");
        }
        setContentView(R.layout.activity_owner);
        supportPostponeEnterTransition();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flOwnerContainer, OwnerFragment.newInstance(gitUserModel))
                    .commit();
        }
    }
}
