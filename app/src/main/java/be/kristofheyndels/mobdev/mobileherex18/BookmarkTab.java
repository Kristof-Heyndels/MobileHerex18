package be.kristofheyndels.mobdev.mobileherex18;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import be.kristofheyndels.mobdev.data.DetailsRoomDatabase;
import be.kristofheyndels.mobdev.helpers.MyArrayAdapter;
import be.kristofheyndels.mobdev.model.SwapiObject;

public class BookmarkTab extends Fragment {

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    private Spinner dropBookmarkCategories;
    private Spinner dropBookmarkCategoryItems;

    private int selectedCategory = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_bookmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dropBookmarkCategories = view.findViewById(R.id.drop_bookmark_categories);
        dropBookmarkCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                populateCategoryItemsStart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dropBookmarkCategoryItems = view.findViewById(R.id.drop_bookmark_category_items);

        if (savedInstanceState != null) {
            selectedCategory = savedInstanceState.getInt("selectedItem");
            ;
        }

        populateCategoriesStart();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible()) {
            populateCategoryItemsStart();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("selectedItem", dropBookmarkCategories.getSelectedItemPosition());
    }

    private void populateCategoriesStart() {
        final String TABLE_NAME_QUERY = "SELECT name FROM sqlite_master " +
                "WHERE type='table' " +
                "AND name!='android_metadata' " +
                "AND name!='room_master_table' ";

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Cursor c = DetailsRoomDatabase.getDatabase(getContext()).query(TABLE_NAME_QUERY, null);
                populateCategoriesEnd(c);
            }
        });
    }

    private void populateCategoriesEnd(Cursor c) {
        ArrayList<String> bookmarkedCategories = new ArrayList<>();

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                bookmarkedCategories.add(c.getString(0));
                c.moveToNext();
            }
        }

        final ArrayAdapter<String> catAdapter = new MyArrayAdapter(getContext(), R.layout.spinner_item, bookmarkedCategories);
        catAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        MainActivity.appActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dropBookmarkCategories.setAdapter(catAdapter);
                dropBookmarkCategories.setSelection(selectedCategory);
            }
        });
    }

    private void populateCategoryItemsStart() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String category = (String) dropBookmarkCategories.getSelectedItem();
                List<? extends SwapiObject> resultList = null;

                if (category != null) {
                    if (category.toLowerCase().equals("films")) {
                        resultList = new ArrayList<>(DetailsRoomDatabase.getDatabase(getContext()).filmsDao().getAll());
                    } else if (category.toLowerCase().equals("people")) {
                        resultList = new ArrayList<>(DetailsRoomDatabase.getDatabase(getContext()).personDao().getAll());
                    } else if (category.toLowerCase().equals("planets")) {
                        resultList = new ArrayList<>(DetailsRoomDatabase.getDatabase(getContext()).planetDao().getAll());
                    } else if (category.toLowerCase().equals("species")) {
                        resultList = new ArrayList<>(DetailsRoomDatabase.getDatabase(getContext()).speciesDao().getAll());
                    } else if (category.toLowerCase().equals("starships")) {
                        resultList = new ArrayList<>(DetailsRoomDatabase.getDatabase(getContext()).starshipDao().getAll());
                    } else if (category.toLowerCase().equals("vehicles")) {
                        resultList = new ArrayList<>(DetailsRoomDatabase.getDatabase(getContext()).vehicleDao().getAll());
                    }

                    populateCategoryItemsEnd(resultList);
                }
            }
        });
    }

    private void populateCategoryItemsEnd(List<? extends SwapiObject> resultList) {
        ArrayList<String> stringList = new ArrayList<>();

        for (SwapiObject item : resultList) {
            stringList.add(item.getDisplayName());
        }

        final ArrayAdapter<String> bookmarkedItemsAdapter = new MyArrayAdapter(getContext(), R.layout.spinner_dropdown_item_distant_galaxies_font, stringList);
        bookmarkedItemsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_distant_galaxies_font);

        MainActivity.appActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dropBookmarkCategoryItems.setAdapter(bookmarkedItemsAdapter);
            }
        });
    }
}
