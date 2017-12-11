package com.govibs.envoygithub.ui.owner;

import android.content.Intent;
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
import android.widget.TextView;

import com.govibs.core.data.model.GitUserModel;
import com.govibs.envoygithub.R;
import com.govibs.envoygithub.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Vibhor on 12/10/17.
 */

public class OwnerFragment extends Fragment {

    private static final String EXTRA_OWNER = "extra_owner";

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.iv_header)
    public AppCompatImageView mHeaderImage;
    @BindView(R.id.tvFollowingCount)
    public TextView mFollowingCount;
    @BindView(R.id.tvFollowersCount)
    public TextView mFollowersCount;
    @BindView(R.id.tvOwnerName)
    public TextView mOwnerName;

    private GitUserModel mGitUserModel;

    public OwnerFragment() {

    }

    public static OwnerFragment newInstance(@NonNull GitUserModel gitUserModel) {
        OwnerFragment ownerFragment = new OwnerFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_OWNER, gitUserModel);
        ownerFragment.setArguments(args);
        return ownerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState != null) { // coming from config changes
            mGitUserModel = savedInstanceState.getParcelable(EXTRA_OWNER);
        } else if (getArguments() != null) {
            mGitUserModel = getArguments().getParcelable(EXTRA_OWNER);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(EXTRA_OWNER, mGitUserModel);
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
        final View view = inflater.inflate(R.layout.fragment_owner, container, false);
        ButterKnife.bind(this, view);
        setupViews();
        return view;
    }

    @OnClick(R.id.btnViewInGitHub)
    public void onViewInGitHub(View view) {
        Intent intent = AppUtils.getIntentForView();
        intent.setDataAndType(Uri.parse(mGitUserModel.getUrl()), "text/html");
        Intent.createChooser(intent, getString(R.string.text_owner_view_in_github));
    }

    private void setupViews() {
        if (getActivity() == null) {
            return;
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (TextUtils.isEmpty(mGitUserModel.getName())) {
            mToolbar.setTitle(getString(R.string.app_name));
        } else {
            mToolbar.setTitle(mGitUserModel.getName());
        }
        if (TextUtils.isEmpty(mGitUserModel.getAvatar_url())) {
            final String url = "https://picsum.photos/1024/768/?blur";
            Picasso.with(getContext()).load(Uri.parse(url))
                    .centerCrop()
                    .fit().error(R.drawable.ic_cloud_circle_white_24dp).into(mHeaderImage);
        } else {
            final String url = mGitUserModel.getAvatar_url();
            Picasso.with(getContext()).load(Uri.parse(url)).centerCrop()
                    .fit().error(R.drawable.ic_cloud_circle_white_24dp).into(mHeaderImage);
        }
        mFollowersCount.setText(String.format(Locale.getDefault(), "%1$d", mGitUserModel.getFollowers()));
        mFollowingCount.setText(String.format(Locale.getDefault(), "%1$d", mGitUserModel.getFollowing()));
        mOwnerName.setText(mGitUserModel.getLogin());
    }

}
