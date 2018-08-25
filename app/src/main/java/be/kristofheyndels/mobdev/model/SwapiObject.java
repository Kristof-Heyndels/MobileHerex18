package be.kristofheyndels.mobdev.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import be.kristofheyndels.mobdev.helpers.Categories;

public class SwapiObject {

    @PrimaryKey
    @NonNull
    private String url;
    private String created;
    private String edited;

    @Ignore
    private Categories.SelectedCategory category;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getDisplayName() {
        return null;
    }

    public Categories.SelectedCategory getCategory() {
        return category;
    }

    public void setCategory(Categories.SelectedCategory category) {
        this.category = category;
    }
}
