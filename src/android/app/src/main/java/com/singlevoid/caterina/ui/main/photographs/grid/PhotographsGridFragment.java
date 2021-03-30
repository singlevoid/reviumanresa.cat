package com.singlevoid.caterina.ui.main.photographs.grid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.photograph.PhotographManager;

public class PhotographsGridFragment extends Fragment  {


    private DataViewModel data;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_collection_grid, container, false);
        initDataModel();
        return root;
    }


    private void initDataModel() {
        data =  new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        data.getPhotographs(requireContext()).observe(getViewLifecycleOwner(),this::loadPhotographs);
    }


    private void loadPhotographs(PhotographManager photographs) {
        data.getFilters(requireContext()).observe(getViewLifecycleOwner(), filters ->
            updateDataSet(photographs, filters)
        );
    }


    private void updateDataSet(PhotographManager photographs, FilterManager filters){
        if(getGrid().getAdapter() == null){
            PhotographsGridAdapter adapter = new PhotographsGridAdapter(getContext(),
                                                                        photographs,
                                                                        filters);
            getGrid().setAdapter(adapter);
        }
        else{
            ((PhotographsGridAdapter)getGrid().getAdapter()).notifyDataSetChanged();
        }
    }


    private GridView getGrid(){
        return root.findViewById(R.id.CollectionGrid);
    }
}