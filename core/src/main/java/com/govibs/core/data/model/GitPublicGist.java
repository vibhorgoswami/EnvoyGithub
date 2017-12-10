package com.govibs.core.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Public Gist https://api.github.com/gists/public
 * Created by Vibhor on 12/8/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GitPublicGist implements Parcelable {

    private String url;
    @JsonProperty("forks_url")
    private String forksUrl;
    @JsonProperty("commits_url")
    private String commitUrl;
    private String id;
    @JsonProperty("git_pull_url")
    private String gitPullUrl;
    @JsonProperty("git_push_url")
    private String gitPushUrl;
    @JsonProperty("html_url")
    private String htmlUrl;
    private int comments;
    @JsonProperty("comments_url")
    private String commentUrl;
    private GitUserModel owner;
    private String description;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getForksUrl() {
        return forksUrl;
    }

    public void setForksUrl(String forksUrl) {
        this.forksUrl = forksUrl;
    }

    public String getCommitUrl() {
        return commitUrl;
    }

    public void setCommitUrl(String commitUrl) {
        this.commitUrl = commitUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGitPullUrl() {
        return gitPullUrl;
    }

    public void setGitPullUrl(String gitPullUrl) {
        this.gitPullUrl = gitPullUrl;
    }

    public String getGitPushUrl() {
        return gitPushUrl;
    }

    public void setGitPushUrl(String gitPushUrl) {
        this.gitPushUrl = gitPushUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    public GitUserModel getOwner() {
        return owner;
    }

    public void setOwner(GitUserModel owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GitPublicGist() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.forksUrl);
        dest.writeString(this.commitUrl);
        dest.writeString(this.id);
        dest.writeString(this.gitPullUrl);
        dest.writeString(this.gitPushUrl);
        dest.writeString(this.htmlUrl);
        dest.writeInt(this.comments);
        dest.writeString(this.commentUrl);
        dest.writeParcelable(this.owner, flags);
        dest.writeString(this.description);
    }

    protected GitPublicGist(Parcel in) {
        this.url = in.readString();
        this.forksUrl = in.readString();
        this.commitUrl = in.readString();
        this.id = in.readString();
        this.gitPullUrl = in.readString();
        this.gitPushUrl = in.readString();
        this.htmlUrl = in.readString();
        this.comments = in.readInt();
        this.commentUrl = in.readString();
        this.owner = in.readParcelable(GitUserModel.class.getClassLoader());
        this.description = in.readString();
    }

    public static final Creator<GitPublicGist> CREATOR = new Creator<GitPublicGist>() {
        @Override
        public GitPublicGist createFromParcel(Parcel source) {
            return new GitPublicGist(source);
        }

        @Override
        public GitPublicGist[] newArray(int size) {
            return new GitPublicGist[size];
        }
    };
}
