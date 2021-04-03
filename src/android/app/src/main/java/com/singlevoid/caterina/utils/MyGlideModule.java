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

package com.singlevoid.caterina.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

@GlideModule
public final class MyGlideModule extends AppGlideModule {


    @Override
    public void registerComponents(@NotNull Context context, @NotNull Glide glide, @NotNull Registry registry) {
        registry.append(StorageReference.class, InputStream.class,
                new FirebaseImageLoader.Factory());
    }
}