package com.nicatec.twitceratops.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nicatec.twitceratops.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchTextViewFragment extends Fragment {


    @Bind(R.id.location_text_view_fragment_search_text_view)
    EditText searchTextView;

    //tengo un interface para comunicar a la actividad que me han puesto algo en el search
    private SearchedTextListened searchedTextListened;

    public SearchTextViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.search_text_view_fragment, container, false);

        ButterKnife.bind(this,root);

        //añado el listener para cuando pulsan aceptar en la localizacion
        searchTextView.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                switch (i) {
                    case 5:
                    case 6:
                        //han pulsado acpetar en el teclado
                        if ( searchedTextListened != null ) {
                            //le paso a la actividad lo que me han escrito a traves de un interface
                            searchedTextListened.OnNewLocationToSearch(searchTextView.getText().toString());
                            // y desaparezco

                        }
                }
                Log.v("SearchTextView", "Han pulsado algo " + i + " event=" + keyEvent);
                return handled;
            }
        });

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        searchedTextListened = (SearchedTextListened) activity;
    }


    @Override

    public void onAttach(Context context) {
        super.onAttach(context);

        searchedTextListened = (SearchedTextListened) getActivity();
    }


    @Override
    public void onDetach() {
        super.onDetach();

        searchedTextListened = null;
    }
    public interface SearchedTextListened {
        void OnNewLocationToSearch(String locationString);
    }
}
