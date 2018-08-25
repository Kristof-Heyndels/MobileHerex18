package be.kristofheyndels.mobdev.mobileherex18;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.kristofheyndels.mobdev.factory.Details;
import be.kristofheyndels.mobdev.factory.DetailsFactory;
import be.kristofheyndels.mobdev.model.SwapiObject;


public class DetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private SwapiObject selectedItem;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (selectedItem != null) {
            fillDetails();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setSelectedItem(SwapiObject selectedItem) {
        this.selectedItem = selectedItem;
        if (this.getView() != null) {
            fillDetails();
        }
    }

    private void fillDetails(){
        DetailsFactory factory = new DetailsFactory();
        Details details = factory.buildDetails(selectedItem);
        details.generateLayout(this);
    }
}
