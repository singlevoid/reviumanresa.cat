package com.singlevoid.caterina.ui.main.near;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.photograph.Photograph;
import com.singlevoid.caterina.ui.main.MainActivity;

import java.util.Objects;

public class NearItemFragment extends Fragment {

    private View root;
    private Photograph photograph;
    public void setPhotograph(Photograph photograph){ this.photograph = photograph;}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.item_near_photograph, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(photograph != null){ loadUI(); }
    }

    private void loadUI(){
        ImageView image = root.findViewById(R.id.item_near_photograph_image);
        TextView title = root.findViewById(R.id.item_near_photograph_text_title);
        TextView distance = root.findViewById(R.id.item_near_photograph_distance);

        image.setOnClickListener(this::itemClicked);
        title.setText(photograph.getTitle());
        distance.setText(requireContext().getResources().getString(R.string.distance,
                (int) Math.round(photograph.getDistance())));
        Glide.with(this).load(photograph.HighQualityReference()).into(image);
    }

    private void itemClicked(View view) {
        ((MainActivity) requireActivity()).openPhotographDetail(photograph);
    }

}
