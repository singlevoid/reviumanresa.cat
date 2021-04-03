import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService } from '../../service/data.service';
import { UploadImageComponent } from '../upload-image/upload-image.component';
import { TagFormComponent } from './tag-form.component';
import { AngularFireStorage } from '@angular/fire/storage';
import { Tag } from 'src/app/model/tag';

@Component({
  selector: 'app-admin-tag',
  templateUrl: './admin-tag.component.html',
  styleUrls: ['./admin-tag.component.scss']
})
export class AdminTagComponent implements OnInit {

  @ViewChild(UploadImageComponent) uploadImage: UploadImageComponent;
  @ViewChild('englishForm') englishForm: TagFormComponent;
  @ViewChild('spanishForm') spanishForm: TagFormComponent;
  @ViewChild('catalanForm') catalanForm: TagFormComponent;

  id: string;
  tabs: any = [];

  private en: Tag;
  private es: Tag;
  private ca: Tag;

  public lowImage: string;

  constructor(public data: DataService,
              private route: ActivatedRoute,
              private storage: AngularFireStorage,
              private router: Router) {

  }

  ngOnInit(): void {
    this.updateParams()
  }

  private updateParams(): void{
    this.route.paramMap.subscribe(
      params => {
        this.id = params.get("id");
        if(this.id) {this.load()}
      }
    )
  }


  public getTagLowQuality(){
    if(this.en){
      return this.en?.getLowQuality(this.storage)
    }
  }

  public getTagHighQuality(){
    if(this.en){
      return this.en?.getHighQuality(this.storage)
    }
  }


  private async load(){
    if(this.id){
      this.en = await this.data.getTagById(this.id, "en")
      this.es = await this.data.getTagById(this.id, "es")
      this.ca = await this.data.getTagById(this.id, "ca")
      if(this.en){
        await this.getTagLowQuality()
        this.englishForm.setForm({name: this.en.getName(),
                                   description: this.en.getDescription()})
      }
      if(this.es){
        this.spanishForm.setForm({name: this.es.getName(),
                                  description: this.es.getDescription()})
      }
      if(this.ca){
        this.catalanForm.setForm({name: this.ca.getName(),
                                  description: this.ca.getDescription()})
      }

    }
  }


  public async upload(): Promise<void> {
    if(this.id)  {this.updateTag()}
    else         {this.createNewTag()}
  }


  public updateTag(): void{
    var newEnTag = new Tag(this.englishForm.formToJSON())
    this.data.uploadTagById(this.id, newEnTag.JSON(), "en")

    var newEsTag = new Tag(this.spanishForm.formToJSON())
    this.data.uploadTagById(this.id, newEsTag.JSON(), "es")

    var newCaTag = new Tag(this.catalanForm.formToJSON())
    this.data.uploadTagById(this.id, newCaTag.JSON(), "ca")

    this.uploadImageToServer()
  }


  public async createNewTag(): Promise<void>{
    console.log("Creating new tag")
    var newEnTag = new Tag(this.englishForm.formToJSON())
    var id = await this.data.createNewTag(newEnTag.JSON(), "en")
    console.log(id)

    var newEsTag = new Tag(this.spanishForm.formToJSON())
    this.data.uploadTagById(id, newEsTag.JSON(), "es")

    var newCaTag = new Tag(this.catalanForm.formToJSON())
    this.data.uploadTagById(id, newCaTag.JSON(), "ca")

    this.router.navigate(['admin', 'tag', id]);
  }


  public async uploadImageToServer(): Promise<void>{
    this.data.uploadImageFile(this.id,"high", await this.uploadImage.getHighQuality())
    this.data.uploadImageFile(this.id,"low", await this.uploadImage.getLowQuality())
  }

}

