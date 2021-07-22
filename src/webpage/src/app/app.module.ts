

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AngularFireModule } from '@angular/fire';
import { AngularFirestoreModule } from "@angular/fire/firestore";
import { AngularFireStorageModule } from '@angular/fire/storage';
import { AngularFireAuthModule } from '@angular/fire/auth';

import { MatTabsModule } from '@angular/material/tabs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AgmCoreModule } from '@agm/core';
import {AgmMarkerClustererModule } from '@agm/markerclusterer';

import { NgxMasonryModule } from 'ngx-masonry';
import { NgxImageCompressService } from 'ngx-image-compress';

import { environment } from '@environment';

import { AppComponent } from './app.component';
import { HomeComponent } from './component/home/home.component';
import { Routes, RouterModule } from '@angular/router';
import { FooterComponent } from './component/footer/footer.component';
import { MenuComponent } from './component/menu/menu.component';
import { PhotographComponent } from './component/photograph/photograph.component';
import { PhotographDetailComponent } from './component/photograph-detail/photograph-detail.component';
import { MapComponent } from './component/map/map.component';
import { AuthorComponent } from './component/author/author.component';
import { AuthorDetailComponent } from './component/author-detail/author-detail.component';
import { AdminTagComponent } from './component/admin-tag/admin-tag.component';
import { PhotographMasonryComponent } from './component/photograph-masonry/photograph-masonry.component';
import { PhotographCardComponent } from '@photograph';
import { UploadImageComponent } from './component/upload-image/upload-image.component';
import { TagFormComponent } from './component/admin-tag/tag-form.component';
import { TagsComponent } from './component/tags/tags.component';
import { TagCardComponent } from './component/tag-card/tag-card.component';
import { TagDetailComponent } from './component/tag-detail/tag-detail.component';
import { AuthorAdminComponent } from './component/author-admin/author-admin.component';
import { PhotographAdminComponent } from './component/photograph-admin/photograph-admin.component';
import { AuthorCardComponent } from './component/author-card/author-card.component';
import { BasicFormComponent } from './component/basic-form/basic-form.component';
import { DateFormComponent } from './component/date-form/date-form.component';
import { LoginComponent } from './component/login/login.component';
import { AdminDashboardComponent } from './component/admin-dashboard/admin-dashboard.component';
import { LoadingComponent } from './component/loading/loading.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'photo', component: PhotographComponent},
  {path: 'photo/:id', component: PhotographDetailComponent},
  {path: 'collection', component: TagsComponent},
  {path: 'collection/:id', component: TagDetailComponent},
  {path: 'map', component: MapComponent},
  {path: 'admin/collection', component: AdminTagComponent},
  {path: 'admin/collection/:id', component: AdminTagComponent},
  {path: 'admin/author', component: AuthorAdminComponent},
  {path: 'admin/author/:id', component: AuthorAdminComponent},
  {path: 'admin/photo', component: PhotographAdminComponent},
  {path: 'admin/photo/:id', component: PhotographAdminComponent},
  {path: 'author', component: AuthorComponent},
  {path: 'author/:id', component: AuthorDetailComponent},
  {path: 'login', component: LoginComponent},
  {path: 'admin', component: AdminDashboardComponent}
  ]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooterComponent,
    MenuComponent,
    PhotographComponent,
    PhotographDetailComponent,
    MapComponent,
    AuthorComponent,
    AuthorDetailComponent,
    AdminTagComponent,
    PhotographMasonryComponent,
    PhotographCardComponent,
    UploadImageComponent,
    TagFormComponent,
    TagsComponent,
    TagCardComponent,
    TagDetailComponent,
    AuthorAdminComponent,
    PhotographAdminComponent,
    AuthorCardComponent,
    BasicFormComponent,
    DateFormComponent,
    LoginComponent,
    AdminDashboardComponent,
    LoadingComponent,
   ],
  imports: [
    BrowserAnimationsModule,
    MatTabsModule,
    MatProgressSpinnerModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    NgxMasonryModule,
    AngularFirestoreModule,
    AngularFireStorageModule,
    AngularFireAuthModule,
    AgmMarkerClustererModule,
    AgmCoreModule.forRoot( { apiKey: 'AIzaSyB5vrjR9jHMun90QsnLwhESKBmbdoV11rk' } ),
    RouterModule.forRoot(routes),
    NgbModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
  ],
  providers: [NgxImageCompressService],
  bootstrap: [AppComponent]
})
export class AppModule {}
