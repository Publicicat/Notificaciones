package com.publicicat.pgram;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentThree extends Fragment implements FragmentThreeInt {

    private RecyclerView rvMascotaInternet;
    private TextView tvF3;
    private LinearLayout wrapper;
    String tvF3String;
    private FragmentThreeIntPres presenterThree;
    Context context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "fullNameInternet";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentThree() {
        // Required empty public constructor
    }

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentThree.
     */
    //TODO: Rename and change types and number of parameters
    public static FragmentThree newInstance(String param1, String param2) {
        FragmentThree fragment = new FragmentThree();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {
            tvF3String = getArguments().getString(ARG_PARAM1);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_three, container, false);
        tvF3 = v.findViewById(R.id.tv_f3_title);
        tvF3.setText(tvF3String);
        rvMascotaInternet = v.findViewById(R.id.rv_mascotasInternet);
        presenterThree = new FragmentThreePres(this, getContext());
        return v;
    }

    /*Mètode també provat per  ntentar omplir de contingut els elements que
    provenen d'una Activity*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*if (getArguments()!= null) {
            tvF3String = getArguments().getString(ARG_PARAM1);
            if (tvF3String != null) {

                tvF3.setText(tvF3String);
                //mParam2 = getArguments().getString(ARG_PARAM2);
                Log.d(tvF3String, "What is passed");
            }
        }*/
    }

    @Override
    public void generarGridLayour() {
        rvMascotaInternet.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //rvMascotaInternet.setLayoutManager(gridLayoutManager);
    }

    @Override
    public AdapterThree crearAdaptador(ArrayList<ConstructorInternet> mascotaInternet) {
        AdapterThree adapterThree = new AdapterThree(mascotaInternet, getActivity());
        return adapterThree;
    }

    @Override
    public void initAdapterRV(AdapterThree adapterThree) {
        rvMascotaInternet.setAdapter(adapterThree);
    }


}
