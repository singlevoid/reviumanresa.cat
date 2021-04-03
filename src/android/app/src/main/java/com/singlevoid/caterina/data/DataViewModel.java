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

package com.singlevoid.caterina.data;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.singlevoid.caterina.data.author.AuthorManager;
import com.singlevoid.caterina.data.filters.FilterManager;
import com.singlevoid.caterina.data.photograph.PhotographManager;
import com.singlevoid.caterina.data.source.SourceManager;
import com.singlevoid.caterina.data.tag.TagManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DataViewModel extends ViewModel {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          VARIABLES                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private final static String DATASET = "dataset-1-1";
    private final static String PHOTOGRAPHS_TAG = "photograph";
    private final static String TAGS_TAG = "tag";
    private final static String AUTHOR_TAG = "author";
    private final static String SOURCES_TAG = "source";

    private static final List<String> SUPPORTED_LANGUAGES = new ArrayList<String>() {{ add("es"); add("en"); add("ca"); }};

    private MutableLiveData<PhotographManager> photographs;
    private MutableLiveData<TagManager> tags;
    private MutableLiveData<AuthorManager> authors;
    private MutableLiveData<FilterManager> filters;
    private MutableLiveData<SourceManager> sources;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                             SETTERS AND GETTERS                                            //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public MutableLiveData<PhotographManager> getPhotographs(Context context){
        downloadPhotographsIfAreNull(context);
        return photographs;
    }


    public MutableLiveData<FilterManager> getFilters(){
        createFiltersIfAreNull();
        return filters;
    }


    public MutableLiveData<TagManager> getTags(Context context){
        downloadTagsIfAreNull(context);
        return tags;
    }


    public MutableLiveData<AuthorManager> getAuthors(Context context){
        downloadAuthorIfAreNull(context);
        return authors;
    }


    public MutableLiveData<SourceManager> getSources(Context context){
        downloadSourcesIfAreNull(context);
        return sources;
    }


    @NotNull
    private String getDeviceLanguage(@NotNull Context context) {
        return context.getResources().getConfiguration().getLocales().get(0).getLanguage();
    }


    @NotNull
    private String getLocale(Context context){
        if (SUPPORTED_LANGUAGES.contains(getDeviceLanguage(context))){
            return getDeviceLanguage(context);
        }
        return "en";
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                   METHODS                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void updateFilters(FilterManager filterManager) {
        filters.postValue(filterManager);
    }

    public void updatePhotographs(PhotographManager manager) {
        photographs.postValue(manager);
    }


    private void downloadPhotographs(Context context){
        getDocuments(PHOTOGRAPHS_TAG, getLocale(context))
                .addOnSuccessListener(query ->
                    photographs.postValue(photographs.getValue().parseQuery(query))
                );
    }


    private void downloadTags(Context context){
        getDocuments(TAGS_TAG, getLocale(context))
                .addOnSuccessListener(query ->
                   tags.postValue(tags.getValue().parseQuery(query))
                );
    }


    private void downloadAuthors(Context context){
        getDocuments(AUTHOR_TAG, getLocale(context))
                .addOnSuccessListener(query ->
                    authors.postValue(authors.getValue().parseQuery(query))
                );
    }


    private void downloadSources(Context context){
        getDocuments(SOURCES_TAG, getLocale(context))
                .addOnSuccessListener(query -> 
                    sources.postValue(sources.getValue().parseQuery(query))
                );
    }


    @NotNull
    private Task<QuerySnapshot> getDocuments(String collection, String lang){
        authenticateToFirebaseAsAnonymous();
        return FirebaseFirestore.getInstance()
                .collection(DATASET)
                .document(collection)
                .collection(lang)
                .get();
    }


    private void authenticateToFirebaseAsAnonymous(){
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            FirebaseAuth.getInstance().signInAnonymously();
        }
    }


    private void createFiltersIfAreNull(){
        if (filters == null){
            filters = new MutableLiveData<>();
            filters.setValue(new FilterManager());
        }
    }


    private void downloadAuthorIfAreNull(Context context){
        if ( authors == null){
            authors = new MutableLiveData<>();
            authors.setValue(new AuthorManager());
            downloadAuthors(context);
        }
    }


    private void downloadPhotographsIfAreNull(Context context){
        if (photographs == null){
            photographs = new MutableLiveData<>();
            photographs.setValue(new PhotographManager());
            downloadPhotographs(context);
        }
    }


    private void downloadTagsIfAreNull(Context context){
        if (tags == null){
            tags = new MutableLiveData<>();
            tags.setValue(new TagManager());
            downloadTags(context);
        }
    }


    private void downloadSourcesIfAreNull(Context context){
        if (sources == null){
            sources = new MutableLiveData<>();
            sources.setValue(new SourceManager());
            downloadSources(context);
        }
    }
}
