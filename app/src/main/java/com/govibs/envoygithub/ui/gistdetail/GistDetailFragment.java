package com.govibs.envoygithub.ui.gistdetail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.govibs.core.data.DataManager;
import com.govibs.core.data.model.GitFiles;
import com.govibs.core.data.model.GitPublicGist;
import com.govibs.core.ui.gist.file.GistDetailContract;
import com.govibs.core.ui.gist.file.GistDetailPresenter;
import com.govibs.envoygithub.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vibhor on 12/10/17.
 */

public class GistDetailFragment extends Fragment implements GistDetailContract.GistView, GistDetailContract.ViewActions {


    private static final String EXTRA_GIT_PUBLIC_GIST = "extra_git_public_gist";

    private GistDetailPresenter mGistDetailPresenter;
    private GitPublicGist mGitPublicGist;

    private LinearLayout mContentFrame;
    private ProgressBar mContentLoadingProgress;

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.iv_header)
    public AppCompatImageView mHeaderImage;

    public GistDetailFragment() {

    }

    public static GistDetailFragment newInstance(@NonNull GitPublicGist gitPublicGist) {
        GistDetailFragment fragment = new GistDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_GIT_PUBLIC_GIST, gitPublicGist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mGistDetailPresenter = new GistDetailPresenter(DataManager.getInstance());
        if (savedInstanceState != null) { // coming from config changes
            mGitPublicGist = savedInstanceState.getParcelable(EXTRA_GIT_PUBLIC_GIST);
        } else if (getArguments() != null) {
            mGitPublicGist = getArguments().getParcelable(EXTRA_GIT_PUBLIC_GIST);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(EXTRA_GIT_PUBLIC_GIST, mGitPublicGist);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstanceState) {
        super.onActivityCreated(onSavedInstanceState);
        if (getActivity() == null) {
            return;
        }
        getActivity().supportStartPostponedEnterTransition();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_gist_detail, container, false);
        ButterKnife.bind(this, view);
        setupViews();
        return view;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showUnauthorizedError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    @Override
    public void onGistFileRequested(String userId) {

    }

    @Override
    public void showFilesList(List<GitFiles> gitFilesList) {

    }

    private void setupViews() {
        if (getActivity() == null) {
            return;
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (TextUtils.isEmpty(mGitPublicGist.getDescription())) {
            mToolbar.setTitle(getString(R.string.app_name));
        } else {
            mToolbar.setTitle(mGitPublicGist.getDescription());
        }
        if (mGitPublicGist.getOwner() == null || TextUtils.isEmpty(mGitPublicGist.getOwner().getAvatar_url())) {
            final String url = "https://picsum.photos/1024/768/?blur";
            Picasso.with(getContext()).load(Uri.parse(url))
                    .centerCrop()
                    .fit().error(R.drawable.ic_cloud_circle_white_24dp).into(mHeaderImage);
        } else {
            final String url = mGitPublicGist.getOwner().getAvatar_url();
            Picasso.with(getContext()).load(Uri.parse(url)).centerCrop()
                    .fit().error(R.drawable.ic_cloud_circle_white_24dp).into(mHeaderImage);
        }
    }
}
