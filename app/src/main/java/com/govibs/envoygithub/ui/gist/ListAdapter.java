package com.govibs.envoygithub.ui.gist;

import android.net.Uri;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.govibs.core.constant.AppConstants;
import com.govibs.core.data.model.GitPublicGist;
import com.govibs.envoygithub.R;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Generic list adapter
 * Created by Vibhor on 12/10/17.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private InteractionListener mListInteractionListener;
    private List<GitPublicGist> mGitPublicGistList;

    static final int VIEW_TYPE_PUBLIC_GIST = 0;
    static final int VIEW_TYPE_LIST = 1;
    static final int VIEW_TYPE_LOADING = 2;

    @IntDef({VIEW_TYPE_LOADING, VIEW_TYPE_PUBLIC_GIST, VIEW_TYPE_LIST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    @ViewType
    private int mViewType;

    public ListAdapter() {
        mGitPublicGistList = new ArrayList<>();
        mViewType = VIEW_TYPE_PUBLIC_GIST;
        mListInteractionListener = null;
    }

    public void add(GitPublicGist gitPublicGist) {
        add(null, gitPublicGist);
    }

    public void add(@Nullable Integer position, GitPublicGist item) {
        if (position != null) {
            mGitPublicGistList.add(position, item);
            notifyItemInserted(position);
        } else {
            mGitPublicGistList.add(item);
            notifyItemInserted(mGitPublicGistList.size() - 1);
        }
    }

    public void addItems(List<GitPublicGist> itemList) {
        mGitPublicGistList.addAll(itemList);
        notifyItemRangeInserted(getItemCount(), mGitPublicGistList.size() - 1);
    }

    public void remove(int position) {
        if (mGitPublicGistList.size() < position) {
            Log.w(AppConstants.TAG, "The item at position: " + position + " doesn't exist");
            return;
        }
        mGitPublicGistList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        mGitPublicGistList.clear();
        notifyDataSetChanged();
    }

    public boolean addLoadingView() {
        if (getItemViewType(mGitPublicGistList.size() - 1) != VIEW_TYPE_LOADING) {
            add(null);
            return true;
        }
        return false;
    }

    public boolean removeLoadingView() {
        if (mGitPublicGistList.size() > 1) {
            int loadingViewPosition = mGitPublicGistList.size() - 1;
            if (getItemViewType(loadingViewPosition) == VIEW_TYPE_LOADING) {
                remove(loadingViewPosition);
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public int getViewType() {
        return mViewType;
    }

    public void setViewType(@ViewType int viewType) {
        mViewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return mGitPublicGistList.get(position) == null ? VIEW_TYPE_LOADING : mViewType;
    }

    @Override
    public int getItemCount() {
        return mGitPublicGistList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicationViewHolder(parent);
        }
        return onGenericItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LOADING) {
            return; // no-op
        }
        onBindGenericItemViewHolder((FileViewHolder) holder, position);
    }

    private void onBindGenericItemViewHolder(final FileViewHolder holder, int position) {
        GitPublicGist gitPublicGist = mGitPublicGistList.get(position);
        if (gitPublicGist.getOwner() == null || TextUtils.isEmpty(gitPublicGist.getOwner().getAvatar_url())) {
            final String url = "https://picsum.photos/1024/768/?blur";
            Picasso.with(holder.itemView.getContext()).load(Uri.parse(url)).error(R.drawable.ic_cloud_circle_white_24dp).into(holder.mImageView);
        } else {
            final String url = gitPublicGist.getOwner().getAvatar_url();
            Picasso.with(holder.itemView.getContext()).load(Uri.parse(url)).error(R.drawable.ic_cloud_circle_white_24dp).into(holder.mImageView);
        }
        if (TextUtils.isEmpty(gitPublicGist.getDescription())) {
            holder.mTextView.setText(holder.itemView.getContext().getString(R.string.app_name));
        } else {
            holder.mTextView.setText(gitPublicGist.getDescription());
        }
    }

    private RecyclerView.ViewHolder onGenericItemViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_PUBLIC_GIST:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_gist, parent, false);
                break;

            case VIEW_TYPE_LIST:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_file, parent, false);
                break;
        }
        return new FileViewHolder(view);
    }

    private RecyclerView.ViewHolder onIndicationViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_progress_bar, parent, false);
        return new ProgressBarViewHolder(view);
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvItemGistName)
        TextView mTextView;
        @BindView(R.id.ivGridCoverImage)
        AppCompatImageView mImageView;

        FileViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> {
                if (mListInteractionListener != null) {
                    mListInteractionListener.onListClick(mGitPublicGistList.get(getAdapterPosition()),
                            mImageView, getAdapterPosition());
                }
            });
        }

    }

    public class ProgressBarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        ProgressBarViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface InteractionListener {
        void onListClick(GitPublicGist publicGist, View sharedElementView, int adapterPosition);
    }

    public void setListInteractionListener(InteractionListener listInteractionListener) {
        mListInteractionListener = listInteractionListener;
    }

}
