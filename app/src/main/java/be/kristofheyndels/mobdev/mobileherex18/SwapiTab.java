package be.kristofheyndels.mobdev.mobileherex18;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.kristofheyndels.mobdev.helpers.Categories;
import be.kristofheyndels.mobdev.model.SwapiObject;

public class SwapiTab extends Fragment{

    public enum ActiveFragment {
        ListFragment,
        DetailFragment
    }

    private static final String LIST_FRAGMENT_TAG = "listFragment";
    private static final String DETAIL_FRAGMENT_TAG = "detailFragment";
    private static ActiveFragment activeFragment = ActiveFragment.ListFragment;
    private static SwapiObject selectedItem;

    private ListFragment listFragment;
    private DetailFragment detailFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.swapiTab = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();

        // Always create new detailsFragment, since you cannot change fragment container id (fragContainer <-> frag_detail)
        detailFragment = new DetailFragment();

        if (savedInstanceState != null) {
            listFragment = (ListFragment) getActivity().getSupportFragmentManager().getFragment(savedInstanceState, LIST_FRAGMENT_TAG);
            detailFragment.setSelectedItem(selectedItem, Categories.selected);
        }

        if (listFragment == null) {
            listFragment = new ListFragment();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (activeFragment == ActiveFragment.ListFragment) {
                trans.replace(R.id.frag_container, listFragment);
            } else {
                trans.replace(R.id.frag_container, detailFragment);
            }
            trans.commit();
        } else {
            trans.replace(R.id.frag_container, listFragment);
            trans.replace(R.id.frag_detail, detailFragment);
            trans.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (listFragment.isAdded())
            getActivity().getSupportFragmentManager().putFragment(outState, LIST_FRAGMENT_TAG, listFragment);

        if (detailFragment.isAdded()) {
            getActivity().getSupportFragmentManager().putFragment(outState, DETAIL_FRAGMENT_TAG, detailFragment);
        }
    }

    public void onUserCategorySelectionMade() {
        selectedItem = null;
        activeFragment = ActiveFragment.ListFragment;
    }

    public void onUserListItemSelectionMade(SwapiObject selectedItem) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();

            trans.replace(R.id.frag_container, detailFragment);
            trans.addToBackStack(null);
            trans.commit();
        }

        activeFragment = ActiveFragment.DetailFragment;
        SwapiTab.selectedItem = selectedItem;
        detailFragment.setSelectedItem(selectedItem, Categories.selected);
    }

    public void onFragmentInteraction(Uri uri) {

    }

}
