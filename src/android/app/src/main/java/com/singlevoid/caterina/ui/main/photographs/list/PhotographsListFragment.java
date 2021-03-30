package com.singlevoid.caterina.ui.main.photographs.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.photograph.PhotographManager;
import com.singlevoid.caterina.data.DataViewModel;


public class PhotographsListFragment extends Fragment{


    private View root;
    private RecyclerView recyclerView;
    private DataViewModel data;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);
        initRecycler();
        initDataViewModel();
        return root;
    }


    private void loadPhotographs(PhotographManager photographs){
        data.getFilters(requireContext()).observe(getViewLifecycleOwner(), filters ->
            updateDataSet(photographs, filters)
        );
    }


    private void updateDataSet(PhotographManager photographs, FilterManager filters){
        if(recyclerView.getAdapter() == null){
            PhotographsListAdapter adapter = new PhotographsListAdapter(photographs,
                                                                        getContext(),
                                                                        filters);
            recyclerView.setAdapter(adapter);
        }
        else{
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }


    private void initDataViewModel(){
        data = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        data.getPhotographs(requireContext())
                .observe(getViewLifecycleOwner(), this::loadPhotographs);
    }

    private void initRecycler(){
        recyclerView = root.findViewById(R.id.fragment_list_recycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
    }

}