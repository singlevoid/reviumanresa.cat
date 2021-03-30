package com.singlevoid.caterina.ui.photograph;

import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.DataViewModel;
import com.singlevoid.caterina.data.author.Author;
import com.singlevoid.caterina.data.photograph.Photograph;

import java.util.ArrayList;
import java.util.Objects;

public class PhotographDetailActivity extends AppCompatActivity implements RequestListener<Bitmap>{

    private Bitmap bitmap;
    private Photograph photograph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photograph_image);
        setToolbar();
        photograph = getParcelablePhotograph();
        loadUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photograph_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)           { back(); return true; }
        else if (item.getItemId() == R.id.action_download)  { saveImageToGallery(); }
        else if (item.getItemId() == R.id.action_share)     { photograph.SharePhotograph(this); }
        else if (item.getItemId() == R.id.action_web)       { photograph.openInBrowser(this); }
        return super.onOptionsItemSelected(item);
    }

    private void back() {
        finish();
        overridePendingTransition(R.anim.slide_up_long, R.anim.slide_down_long);
    }

    private Photograph getParcelablePhotograph(){
        return getIntent().getParcelableExtra("com.singlevoid.caterina.detail.PHOTOGRAPH");
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.DetailToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void loadUI(){


        TextView title = findViewById(R.id.DetailTitleText);
        TextView author = findViewById(R.id.DetailAuthorText);
        TextView source = findViewById(R.id.DetailSourceText);
        TextView date = findViewById(R.id.DetailDateText);
        TextView description = findViewById(R.id.DetailDescriptionText);
        GridView gridView = findViewById(R.id.DetailTagView);

        title.setText(photograph.getTitle());
        if (!TextUtils.isEmpty(photograph.getSource())) {  source.setText(photograph.getSource()); }
        //if (!Objects.isNull(photograph.getDate().toString())) {date.setText(photograph.getDate
        // ().toString());}
        if (!TextUtils.isEmpty(photograph.getDescription())) {  description.setText(photograph.getDescription()); }

        DataViewModel dataViewModel =  new ViewModelProvider(this).get(DataViewModel.class);
        dataViewModel.getTags(this).observe(this, tagManager -> {
            TagAdapter adapter = new TagAdapter(this, tagManager.getTags(photograph.getTags()));
            gridView.setAdapter(adapter);
        });

        dataViewModel.getAuthors(this).observe(this, authorManager -> {
            if((photograph.getAuthor() != null) && authorManager.getAuthorById(photograph.getAuthor()) != null){
                author.setText(authorManager.getAuthorById(photograph.getAuthor()).getName());
            }
        });

        loadImage();
    }

    private void loadImage(){
        PhotoView image = findViewById(R.id.DetailImage);
        if (photograph.getBitmap() == null){
            StorageReference data =
                    FirebaseStorage.getInstance().getReference("high/" +photograph.getId());
            Glide.with(this).asBitmap().load(data).addListener(this).into(image);
        }
        else{
            Bitmap data = photograph.getBitmap();
            Glide.with(this).asBitmap().load(data).addListener(this).into(image);
        }
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
        View progress = findViewById(R.id.DetailProgress);
        View data = findViewById(R.id.activity_photograph_detail_container);
        data.setVisibility(View.VISIBLE);
        progress.setVisibility(View.INVISIBLE);
        bitmap = resource;
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_up_long, R.anim.slide_down_long);
    }
}
