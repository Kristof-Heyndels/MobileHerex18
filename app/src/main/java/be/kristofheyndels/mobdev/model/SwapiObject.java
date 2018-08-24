package be.kristofheyndels.mobdev.model;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

public abstract class SwapiObject {

    @PrimaryKey
    @NonNull
    private String url;
    private String created;
    private String edited;

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
}
