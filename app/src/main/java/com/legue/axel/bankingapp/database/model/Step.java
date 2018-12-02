package com.legue.axel.bankingapp.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipe_id",
        onDelete = ForeignKey.CASCADE))
public class Step implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int stepId;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    @ColumnInfo(name = "recipe_id")
    private int recipeId;

    public Step(int stepId, String shortDescription, String description, String videoURL, String thumbnailURL, int recipeId) {
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
        this.recipeId = recipeId;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.stepId);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
        dest.writeInt(this.recipeId);
    }

    protected Step(Parcel in) {
        this.stepId = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
        this.recipeId = in.readInt();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
