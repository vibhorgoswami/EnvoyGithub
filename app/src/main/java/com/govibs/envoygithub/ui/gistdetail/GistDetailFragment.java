package com.govibs.envoygithub.ui.gistdetail;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.govibs.core.constant.AppConstants;
import com.govibs.core.data.DataManager;
import com.govibs.core.data.model.GitPublicGist;
import com.govibs.core.data.model.GitUserModel;
import com.govibs.core.ui.gist.file.GistDetailContract;
import com.govibs.core.ui.gist.file.GistDetailPresenter;
import com.govibs.envoygithub.R;
import com.govibs.envoygithub.ui.owner.OwnerActivity;
import com.govibs.envoygithub.utils.AppUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Gist Detail
 * Created by Vibhor on 12/10/17.
 */

public class GistDetailFragment extends Fragment implements GistDetailContract.GistView {


    private static final String EXTRA_GIT_PUBLIC_GIST = "extra_git_public_gist";

    private GistDetailPresenter mPresenter;
    private GitPublicGist mGitPublicGist;

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.iv_header)
    public AppCompatImageView mHeaderImage;
    @BindView(R.id.progress)
    public ProgressBar mContentLoadingProgress;
    @BindView(R.id.llEmptyMessage)
    public View mMessageLayout;
    @BindView(R.id.btnTryAgain)
    public Button btnTryAgain;
    @BindView(R.id.ivMessage)
    public ImageView mMessageImage;
    @BindView(R.id.tvEmptyMessage)
    public TextView mMessageEmpty;
    @BindView(R.id.llContentContainer)
    public View mContentContainer;
    @BindView(R.id.webViewFiles)
    public WebView webViewFiles;

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
        mPresenter = new GistDetailPresenter(DataManager.getInstance());
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

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_gist_detail, container, false);
        ButterKnife.bind(this, view);
        mPresenter.attachView(this);
        setupViews();
        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void showProgress() {
        mContentLoadingProgress.setVisibility(View.VISIBLE);
        mMessageLayout.setVisibility(View.GONE);
        mContentContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mContentLoadingProgress.setVisibility(View.GONE);
        mMessageLayout.setVisibility(View.GONE);
        mContentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUnauthorizedError() {
        if (getActivity() == null) {
            return;
        }
        mMessageImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_error_secondary_24dp));
        mMessageEmpty.setText(getString(R.string.text_error_generic_server_error, "Unauthorized"));
        btnTryAgain.setText(getString(R.string.action_check_again));
        mContentContainer.setVisibility(View.GONE);
        showMessageLayout(true);
    }

    @Override
    public void showEmpty() {
        if (getActivity() == null) {
            return;
        }
        mMessageImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_sentiment_very_dissatisfied_secondary_24dp));
        mMessageEmpty.setText(R.string.text_error_no_items_to_display);
        btnTryAgain.setText(R.string.action_check_again);
        showMessageLayout(true);
    }

    @Override
    public void showError(String errorMessage) {
        if (getActivity() == null) {
            return;
        }
        mMessageImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_error_secondary_24dp));
        mMessageEmpty.setText(getString(R.string.text_error_generic_server_error, errorMessage));
        btnTryAgain.setText(getString(R.string.action_check_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        mMessageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        mContentContainer.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showFilesList(String infoToLoad) {
        webViewFiles.loadUrl(mGitPublicGist.getHtmlUrl());
    }

    @Override
    public void showSnackBar() {
        Snackbar.make(mContentContainer, R.string.text_error_no_user_information, Snackbar.LENGTH_LONG).show();
    }

    @OnClick({R.id.tvOwnerInformation, R.id.ivOwnerMore})
    public void onViewMoreOwnerInfo(View view) {
        if (mGitPublicGist.getOwner() == null || getActivity() == null) {
            Snackbar.make(view, R.string.text_error_no_user_information, Snackbar.LENGTH_LONG).show();
            return;
        }
        mPresenter.onRequestOwnerInfo(Uri.parse(mGitPublicGist.getOwner().getLogin()).getLastPathSegment());
    }

    @OnClick(R.id.ivFilesOpenInBrowser)
    public void onViewFilesInBrowser(View view) {
        Intent intent = AppUtils.getIntentForView();
        intent.setDataAndType(Uri.parse(mGitPublicGist.getHtmlUrl()), "text/html");
        startActivity(intent);
    }

    @Override
    public void showOwnerInformation(GitUserModel gitUserModel) {
        if (getActivity() == null) {
            return;
        }
        startActivity(OwnerActivity.createOwnerActivityIntent(getActivity(), gitUserModel));
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
        this.setupWebView();
        if (!TextUtils.isEmpty(mGitPublicGist.getHtmlUrl())) {
            showFilesList(mGitPublicGist.getHtmlUrl());
        }
    }

    private void setupWebView() {
        webViewFiles.getSettings().setJavaScriptEnabled(true);
        webViewFiles.setWebViewClient(new WebViewClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.v(AppConstants.TAG, "Url: " + request.getUrl());
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.v(AppConstants.TAG, "Url: " + url);
                return false;
            }
        });
    }
}
