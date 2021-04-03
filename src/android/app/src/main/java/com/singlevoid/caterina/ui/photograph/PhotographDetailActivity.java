////////////////////////////////////////////////////////////////////////////////////////////////////
//                                      LICENSE                                                   //
////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                                //
// Copyright [2020] [Joan Albert Espinosa Muns]                                                   //
//                                                                                                //
// Licensed under the Apache License, Version 2.0 (the "License")                                 //
// you may not use this file except in compliance with the License.                               //
// You may obtain a copy of the License at                                                        //
//                                                                                                //
// http://www.apache.org/licenses/LICENSE-2.0                                                     //
//                                                                                                //
// Unless required by applicable law or agreed to in writing, software                            //
// distributed under the License is distributed on an "AS IS" BASIS,                              //
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.                       //
// See the License for the specific language governing permissions and                            //
// limitations under the License.                                                                 //
//                                                                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////

package com.singlevoid.caterina.ui.photograph;

import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.author.AuthorManager;
import com.singlevoid.caterina.data.photograph.Photograph;
import com.singlevoid.caterina.data.source.SourceManager;
import com.singlevoid.caterina.data.tag.TagManager;

import org.jetbrains.annotations.NotNull;

public class PhotographDetailActivity extends AppCompatActivity implements RequestListener<Bitmap>{


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private Bitmap bitmap;
    private Photograph photograph;
    private DataViewModel viewModel;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    CONSTRUCTORS AND OVERRIDES                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photograph_image);
        init();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          INIT                                              //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void init() {
        getParcelablePhotograph();
        initViewModel();
        setTitle();
        setDescription();
        setDate();
        loadImage();
        setListeners();
    }


    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(DataViewModel.class);
    }


    private void getParcelablePhotograph() {
        photograph = getIntent().getParcelableExtra("com.singlevoid.caterina.detail.PHOTOGRAPH");
    }


    private void setListeners(){
        viewModel.getAuthors(this).observe(this, this::loadAuthor);
        viewModel.getSources(this).observe(this, this::loadSource);
        viewModel.getTags(this).observe(this, this::loadTags);

        getBackButton().setOnClickListener((v) -> back());
        getDownloadButton().setOnClickListener((v) -> saveImageToGallery());
        getShareButton().setOnClickListener((v) -> photograph.SharePhotograph(this));
        getBrowserButton().setOnClickListener((v) -> photograph.openInBrowser(this));
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void back() {
        overridePendingTransition(R.anim.slide_up_long, R.anim.slide_down_long);
        finish();
    }


    private void scrollToTop() {
        getScrollView().smoothScrollTo(0, 0);
    }


    private void loadAuthor(AuthorManager manager){
        if(photographAuthorIsNotNull(manager)){
            getAuthorTextView().setText(getAuthorName(manager));
        }
    }


    private void loadSource(SourceManager manager){
        if(photographSourceIsNotNull(manager)){
            getSourceTextView().setText(getSourceName(manager));
        }
    }


    private void loadTags(@NotNull TagManager manager){
        getGridView().setAdapter(new TagAdapter(this, manager.getTags(photograph.getTags())));
        scrollToTop();
    }


    private boolean photographSourceIsNotNull(SourceManager manager){
        return (photograph.getSource() != null) && manager.getSourceById(photograph.getSource()) != null;
    }


    private boolean photographAuthorIsNotNull(AuthorManager manager){
        return (photograph.getAuthor() != null) && manager.getAuthorById(photograph.getAuthor()) != null;
    }


    private String getAuthorName(@NotNull AuthorManager manager){
        return manager.getAuthorById(photograph.getAuthor()).getName();
    }


    private String getSourceName(@NotNull SourceManager manager){
        return manager.getSourceById(photograph.getSource()).getName();
    }


    private void setTitle(){
        if(photograph.getTitle() != null){
            getTitleTextView().setText(photograph.getTitle());
        }
    }


    private void setDate(){
        if(photograph.getHumanDate() != null && !photograph.getHumanDate().equals("")){
            getDateTextView().setText(photograph.getHumanDate());
        }
    }


    private void setDescription(){
        getDescriptionTextView().setText(photograph.getDescription());
    }


    private void loadImage(){
        Glide.with(this)
                .asBitmap()
                .load(photograph.highQualityReference())
                .addListener(this)
                .into(getImageView());
        scrollToTop();
    }


    private void saveImageToGallery(){
        String imagePath = MediaStore.Images.Media.insertImage(getContentResolver(),
                                                               bitmap,
                                                               photograph.getTitle(),
                                                               photograph.getDescription());
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(imagePath), "image/*");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_up_long, R.anim.slide_down_long);
    }


    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
        return false;
    }


    @Override
    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
        getProgressContainer().setVisibility(View.GONE);
        getImageContainer().setVisibility(View.VISIBLE);
        bitmap = resource;
        scrollToTop();
        return false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_up_long, R.anim.slide_down_long);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          UI VIEWS                                          //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private TextView getTitleTextView(){
        return findViewById(R.id.detail_photograph_title);
    }


    private TextView getAuthorTextView(){
        return findViewById(R.id.detail_photograph_author);
    }


    private TextView getDescriptionTextView(){
        return findViewById(R.id.detail_photograph_description);
    }


    private TextView getDateTextView(){
        return findViewById(R.id.detail_photograph_date);
    }


    private TextView getSourceTextView(){
        return findViewById(R.id.detail_photograph_source);
    }


    private GridView getGridView() {
        return findViewById(R.id.detail_photograph_tag_container);
    }


    private PhotoView getImageView(){
        return findViewById(R.id.detail_photograph_image);
    }


    private ImageView getBackButton(){
        return findViewById(R.id.detail_photograph_action_bar_home);
    }


    private ImageView getShareButton(){
        return findViewById(R.id.detail_photograph_action_bar_share);
    }


    private ImageView getDownloadButton(){
        return findViewById(R.id.detail_photograph_action_bar_download);
    }


    private ImageView getBrowserButton(){
        return findViewById(R.id.detail_photograph_action_bar_browser);
    }


    private View getProgressContainer(){
        return findViewById(R.id.detail_progress_container);
    }


    private View getImageContainer(){
        return findViewById(R.id.activity_photograph_detail_container);
    }


    private ScrollView getScrollView(){
        return findViewById(R.id.activity_photograph_detail_container);
    }
}
