package com.govibs.envoygithub.ui.gistdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.govibs.core.data.model.GitPublicGist;
import com.govibs.envoygithub.R;
import com.govibs.envoygithub.ui.base.BaseActivity;

/**
 * Created by Vibhor on 12/10/17.
 */

public class GistDetailActivity extends BaseActivity {

    private static final String EXTRA_GIT_PUBLIC_GIST = "extra_git_public_gist";

    public static Intent newStartIntent(@NonNull Context context, @NonNull GitPublicGist gitPublicGist) {
        Intent intent = new Intent(context, GistDetailActivity.class);
        intent.putExtra(EXTRA_GIT_PUBLIC_GIST, gitPublicGist);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GitPublicGist gitPublicGist = getIntent().getParcelableExtra(EXTRA_GIT_PUBLIC_GIST);
        if (gitPublicGist == null) {
            throw new IllegalArgumentException("This can be handled better..");
        }
        setContentView(R.layout.activity_gist_detail);
        supportPostponeEnterTransition();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flGistFileContainer, GistDetailFragment.newInstance(gitPublicGist))
                    .commit();
        }
    }
}
