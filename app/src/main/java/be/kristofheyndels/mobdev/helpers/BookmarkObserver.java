package be.kristofheyndels.mobdev.helpers;

public interface BookmarkObserver {
    void bookmarkToggled(Boolean isBookmarked, Categories.SelectedCategory currentCategory);
}
