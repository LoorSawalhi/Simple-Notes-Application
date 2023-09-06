package com.example.a1190075_1190245_courseproject.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.a1190075_1190245_courseproject.MyApplication;
import com.example.a1190075_1190245_courseproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SortingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SortingFragment() {
        // Required empty public constructor
    }


    public static SortingFragment newInstance(String param1, String param2) {
        SortingFragment fragment = new SortingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sorting, container, false);

        String[] options = { "A-Z", "Z-A", "Date"};
        final Spinner genderSpinner = view.findViewById(R.id.sort_spinner);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, options);
        genderSpinner.setAdapter(objGenderArr);

        return view;
    }
}