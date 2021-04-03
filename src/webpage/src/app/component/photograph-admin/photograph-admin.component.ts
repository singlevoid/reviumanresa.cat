import { TOUCH_BUFFER_MS } from '@angular/cdk/a11y';
import { typeWithParameters } from '@angular/compiler/src/render3/util';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/storage';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { timer } from 'rxjs';
import { Author } from 'src/app/model/author';
import { MapLocation } from 'src/app/model/map-location';
import { Photograph } from 'src/app/model/photograph';
import { Source } from 'src/app/model/source';
import { Tag } from 'src/app/model/tag';
import { DataService } from 'src/app/service/data.service';
import { environment } from 'src/environments/environment';
import { TagFormComponent } from '../admin-tag/tag-form.component';
import { BasicFormComponent } from '../basic-form/basic-form.component';
import { DateFormComponent } from '../date-form/date-form.component';
import { UploadImageComponent } from '../upload-image/upload-image.component';

@Component({
  selector: 'app-photograph-admin',
  templateUrl: './photograph-admin.component.html',
  styleUrls: ['./photograph-admin.component.scss']
})
export class PhotographAdminComponent implements OnInit {

  @ViewChild(UploadImageComponent) uploadImage: UploadImageComponent;
  @ViewChild('englishForm') englishForm: BasicFormComponent;
  @ViewChild('spanishForm') spanishForm: BasicFormComponent;
  @ViewChild('catalanForm') catalanForm: BasicFormComponent;
  @ViewChild('yearForm') yearForm: DateFormComponent;
  @ViewChild('monthForm') monthForm: DateFormComponent;
  @ViewChild('dayForm') dayForm: DateFormComponent;

  private id: string;
  private tags: Tag[]
  private sources: Source[]
  private authors: Author[]
  private marker: {lat: number, lng: number};
  private globalForm: FormGroup;
  private en: Photograph;
  private es: Photograph;
  private ca: Photograph;
  public uploading: boolean = false;

  public map = {lat: 41.7242344,
                lng: 1.8235865,
                zoom: 15.81,
                styles: environment.mapStyle,
  }

  constructor(public data: DataService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private storage: AngularFireStorage,
              private router: Router) { }

  public Tags(): Tag[]                            {return this.tags}
  public Sources(): Source[]                      {return this.sources}
  public Authors(): Author[]                      {return this.authors}
  public Marker(): {lat: number, lng: number}     {return this.marker}
  public GlobalForm():  FormGroup                 {return this.globalForm}

  ngOnInit(): void {
    this.route.paramMap.subscribe( params => this.getParams(params) )
  }

  private async load(): Promise<void>{
    this.createGlobalForm()
    this.loadTags()
    this.loadSources()
    this.loadAuthors()

    if(!this.id) return

    await this.downloadPhotographs()
    this.loadForm("es")
    this.loadForm("en")
    this.loadForm("ca")
    this.loadGlobalForm()
    this.LoadGlobalTags()

  }


  private async downloadPhotographs(): Promise<void>{
    this.en = await this.data.downloadPhotographById(this.id, "en")
    this.es = await this.data.downloadPhotographById(this.id, "es")
    this.ca = await this.data.downloadPhotographById(this.id, "ca")

  }


  private createGlobalForm(): void{
    this.globalForm = this.formBuilder.group({
      author: ["", Validators.required],
      source: ["", Validators.required],
      latitude: [null, Validators.required],
      longitude: [null, Validators.required],
    })
  }


  private getParams(params: Params): void{
    this.id = params.get("id")
    this.load()
  }


  public getTagLowQuality(): string{
    if(this.en){ return this.en?.getLowQuality(this.storage) }
  }

  public getTagHighQuality(): string{
    if(this.en){ return this.en?.getHighQuality(this.storage) }
  }


  private loadForm(locale: string): void{
    this.getForm(locale).setForm({
      name:         this.getPhotograph(locale).getTitle(),
      description:  this.getPhotograph(locale).getDescription(),
    })
  }

  public getForm(locale: string): BasicFormComponent{
    if(locale == "es") { return this.spanishForm }
    if(locale == "ca") { return this.catalanForm }
    if(locale == "en") { return this.englishForm }
  }

  public getPhotograph(locale:string): Photograph{
    if(locale == "es") { return this.es }
    if(locale == "ca") { return this.ca }
    if(locale == "en") { return this.en }
  }

  private LoadGlobalTags(): void{
    for (const tag of this.en.getTags()){
      for(const myTag of this.tags){
        if(myTag.getId() == tag){
          myTag.selected = true
        }
      }
    }
  }

  private loadGlobalForm(): void{
    this.GlobalForm().controls['author'].setValue(this.en.getAuthor())
    this.GlobalForm().controls['source'].setValue(this.en.getSource())
    if(this.en.isGeolocalized()){
      this.setMarker(this.en.getLocation().getLatitude(), this.en.getLocation().getLongitude())
    }
    if(this.en.getDate().getYear().isRanged()) {this.yearForm.clicked()}
    this.yearForm.setForm({start: this.en.getDate().getYear().getStart(),
                           end: this.en.getDate().getYear().getEnd()})

    if(this.en.getDate().getMonth().isRanged()) {this.monthForm.clicked()}
    this.monthForm.setForm({start: this.en.getDate().getMonth().getStart(),
                           end: this.en.getDate().getMonth().getEnd()})

    if(this.en.getDate().getDay().isRanged()) {this.dayForm.clicked()}
    this.dayForm.setForm({start: this.en.getDate().getDay().getStart(),
                            end: this.en.getDate().getDay().getEnd()})
  }

  public createPhotograph(locale: string): object{

    return new Photograph(
      {
        title: this.getForm(locale).getName(),
        description: this.getForm(locale).getDescription(),
        author: this.getFormAuthor(),
        source: this.getFormSource(),
        tags: this.getSelectedTags(),
        location: new MapLocation({ lat: this.getFormLatitude(),
                                    lng: this.getFormLongitude()
                                  }),
        date: { year: this.yearForm.toJSON(),
                month: this.monthForm.toJSON(),
                day: this.dayForm.toJSON()}

      }).JSON()
  }


  private loadTags(): void          { this.tags = this.data.getTags() }
  private loadSources(): void       { this.sources = this.data.getSources() }
  private loadAuthors(): void       { this.authors = this.data.getAuthors() }

  private getFormAuthor(): String     { return this.GlobalForm().controls['author'].value }
  private getFormSource(): String     { return this.GlobalForm().controls['source'].value }
  private getFormLatitude(): number   { return this.GlobalForm().controls['latitude'].value }
  private getFormLongitude(): number   { return this.GlobalForm().controls['longitude'].value }

  private getSelectedTags(): String[]{
    var tags = []
    for(const tag of this.tags){
      if(tag.selected){
        tags.push(tag.getId())
      }
    }
    return tags;

  }

  public upload(): void{
    if(this.id) { this.updatePhotographData ()}
    else        { this.createNewPhotograph()}
  }

  private async updatePhotograph(locale: string, id?: string): Promise<void>{
    if(!id) {id = this.id}
    var photo = this.createPhotograph(locale)
    await this.data.uploadPhotographById(id, photo, locale)
  }


  public async updatePhotographData(){
    this.uploading = true;
    await this.updatePhotograph("es")
    await this.updatePhotograph("ca")
    await this.updatePhotograph("en")

    await this.uploadImageToServer()
    this.uploading = false;
  }


  private async createNewPhotograph(){
    this.uploading = true;

    var photo = this.createPhotograph("en")
    var id = await this.data.createNewPhotograph(photo, "en")
    await this.updatePhotograph("es", id)
    await this.updatePhotograph("ca", id)
    await this.uploadImageToServer(id)

    this.uploading = false;
  }


  public mapClick(event: any): void{
    this.setMarker(event.coords.lat, event.coords.lng)
  }


  public setMarker(lat: number, lng: number): void{
    this.marker = {lat: lat, lng: lng}
    this.GlobalForm().controls['latitude'].setValue(lat)
    this.GlobalForm().controls['longitude'].setValue(lng)
  }


  public addTag(tag: Tag): void{
    tag.selected = !tag.selected
  }

  public async uploadImageToServer(id?: string): Promise<void>{
    if(!id){ id=this.id }

    var high = await this.uploadImage.getHighQuality()
    if(high){
      this.data.uploadImageFile(id,"high", high)
    }

    var low =await this.uploadImage.getHighQuality()
    if(low){
      this.data.uploadImageFile(id,"low", await this.uploadImage.getLowQuality())
    }
  }

}
