package com.govibs.envoygithub.ui.gist;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.govibs.core.data.DataManager;
import com.govibs.core.data.model.GitPublicGist;
import com.govibs.core.ui.gist.GistContract;
import com.govibs.core.ui.gist.GistPresenter;
import com.govibs.envoygithub.R;
import com.govibs.envoygithub.ui.gistdetail.GistDetailActivity;
import com.govibs.envoygithub.utils.AppUtils;
import com.govibs.envoygithub.utils.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by Vibhor on 12/8/17.
 */

public class ListFragment extends Fragment implements GistContract.GistView, ListAdapter.InteractionListener {

    private static final int TAB_LAYOUT_SPAN_SIZE = 2;
    private static final int TAB_LAYOUT_ITEM_SPAN_SIZE = 1;

    private GistPresenter mPresenter;
    private ListAdapter mListAdapter;

    @BindView(R.id.strViewLayout)
    public SwipeRefreshLayout mSwipeToRefreshLayout;
    @BindView(R.id.progress)
    public ProgressBar mContentLoadingProgress;
    @BindView(R.id.llEmptyMessage)
    public View mMessageLayout;
    @BindView(R.id.rvGists)
    public RecyclerView mGistRecyclerView;
    @BindView(R.id.btnTryAgain)
    public Button btnTryAgain;
    @BindView(R.id.ivMessage)
    public ImageView mMessageImage;
    @BindView(R.id.tvEmptyMessage)
    public TextView mMessageEmpty;

    private SwipeRefreshLayout.OnRefreshListener mOnSwipeRefreshListener = () -> {
        mListAdapter.removeAll();
        mPresenter.onGistRequested();
    };

    public ListFragment() {

    }

    public static ListFragment newInstance() {
        return newInstance(null);
    }

    public static ListFragment newInstance(@Nullable Bundle args) {
        ListFragment fragment = new ListFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setEnterTransition(new Slide());
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new GistPresenter(DataManager.getInstance());
        mListAdapter = new ListAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        setupViews();
        mPresenter.attachView(this);
        if (mListAdapter.isEmpty()) {
            mPresenter.onGistRequested();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        mGistRecyclerView.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        if (mListAdapter.isEmpty() && !mSwipeToRefreshLayout.isRefreshing()) {
            mContentLoadingProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        mSwipeToRefreshLayout.setRefreshing(false);
        mContentLoadingProgress.setVisibility(View.GONE);
        mListAdapter.removeLoadingView();
    }

    @Override
    public void showUnauthorizedError() {
        if (getActivity() == null) {
            return;
        }
        mMessageImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_error_secondary_24dp));
        mMessageEmpty.setText(getString(R.string.text_error_generic_server_error, "Unauthorized"));
        btnTryAgain.setText(getString(R.string.action_check_again));
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
        mGistRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onListClick(GitPublicGist publicGist, View sharedElementView, int adapterPosition) {
        if (getActivity() == null) {
            return;
        }
        startActivity(GistDetailActivity.newStartIntent(getActivity(), publicGist),
                UiUtils.makeTransitionBundle(getActivity(), sharedElementView));
    }

    @Override
    public void showGistList(List<GitPublicGist> gitPublicGists) {
        if (mListAdapter.getViewType() != ListAdapter.VIEW_TYPE_PUBLIC_GIST) {
            mListAdapter.removeAll();
            mListAdapter.setViewType(ListAdapter.VIEW_TYPE_PUBLIC_GIST);
        }

        if (!mSwipeToRefreshLayout.isActivated()) {
            mSwipeToRefreshLayout.setEnabled(true);
        }
        mListAdapter.addItems(gitPublicGists);
    }

    private void setupViews() {
        if (getActivity() == null) {
            return;
        }
        mSwipeToRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mSwipeToRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), android.R.color.white));
        mSwipeToRefreshLayout.setOnRefreshListener(mOnSwipeRefreshListener);
        mGistRecyclerView.setHasFixedSize(true);
        mGistRecyclerView.setMotionEventSplittingEnabled(false);
        boolean isTablet = AppUtils.isTablet(getActivity());
        RecyclerView.LayoutManager layoutManager = setUpLayoutManager(getActivity(), isTablet);
        mGistRecyclerView.setLayoutManager(layoutManager);
        mGistRecyclerView.addOnScrollListener(setupScrollListener(isTablet, layoutManager));
        mGistRecyclerView.setAdapter(mListAdapter);
        mListAdapter.setListInteractionListener(this);
    }

    private RecyclerView.LayoutManager setUpLayoutManager(@NonNull Context context, boolean isTabletLayout) {
        RecyclerView.LayoutManager layoutManager;
        if (!isTabletLayout) {
            layoutManager = new LinearLayoutManager(context);
            UiUtils.addSpacingItemDividerDecorationToRecyclerView(mGistRecyclerView);
        } else {
            layoutManager = initGridLayoutManager(context, TAB_LAYOUT_SPAN_SIZE, TAB_LAYOUT_ITEM_SPAN_SIZE);
            UiUtils.addGridItemDividerDecorationToRecyclerView(mGistRecyclerView, TAB_LAYOUT_SPAN_SIZE, TAB_LAYOUT_ITEM_SPAN_SIZE, true);
        }
        return layoutManager;
    }

    private RecyclerView.LayoutManager initGridLayoutManager(@NonNull Context context, final int spanCount, final int itemSpanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mListAdapter.getItemViewType(position)) {
                    case ListAdapter.VIEW_TYPE_LOADING:
                        // If it is a loading view we wish to accomplish a single item per row
                        return spanCount;
                    default:
                        // Else, define the number of items per row (considering TAB_LAYOUT_SPAN_SIZE).
                        return itemSpanCount;
                }
            }
        });
        return gridLayoutManager;
    }

    private EndlessRecyclerViewOnScrollListener setupScrollListener(boolean isTabletLayout,
                                                                    RecyclerView.LayoutManager layoutManager) {
        return new EndlessRecyclerViewOnScrollListener(isTabletLayout ?
                (GridLayoutManager) layoutManager : (LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (mListAdapter.addLoadingView()) {
                    mPresenter.onListEndReached(totalItemsCount, null);
                }
            }
        };
    }

}
