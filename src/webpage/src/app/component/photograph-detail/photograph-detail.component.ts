
/** Copyright [2020] [Joan Albert Espinosa Muns]
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/storage';
import { ActivatedRoute } from '@angular/router';
import { DataService } from 'src/app/service/data.service';
import { Author } from 'src/app/model/author';
import { Photograph } from 'src/app/model/photograph';
import { Tag } from 'src/app/model/tag';
import { Source } from 'src/app/model/source';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-photograph-detail',
  templateUrl: './photograph-detail.component.html',
  styleUrls: ['./photograph-detail.component.scss']
})
export class PhotographDetailComponent implements OnInit {


  private id: string;
  private author: Author;
  private source: Source;
  private photograph: Photograph;


  constructor(
    public data: DataService,
    private route: ActivatedRoute,
    private storage: AngularFireStorage,
    private auth: AuthService) {}


  ngOnInit(): void{
    this.updateParams()
  }

  public ID(): string   {return this.id}

  public isUserAdmin(): boolean{
    return this.auth.userIsRegistered()
  }

  private async load(){
    this.photograph = await this.data.getPhotographById(this.id)
    await this.getAuthor()
    await this.getSource()
  }


  public getPhotograph(): Photograph{
    return this.photograph
  }


  public getSourceName(): string{
    return this.source.getName()
  }

  private updateParams(): void{
    this.route.paramMap.subscribe(
      params => {
        this.id = params.get("id");
        this.load()
      })
  }


  public tags(): Tag[]{
    return this.data.getTagsByIds(this.photograph.getTags())
  }


  public title(): string{
    return this.photograph.getTitle()
  }


  public isDated(): boolean{
    return this.photograph.isDated
  }


   public yearDate(): string{
    return this.getPhotograph().getDate().getYear().getHumanDate()
   }


  private getHighQuality(): string{
    return this.photograph.getHighQuality(this.storage)
  }


  private getLowQuality(): string{
    return this.photograph.getLowQuality(this.storage)
  }


   public imgSource()              {
     return this.getHighQuality() ? this.getHighQuality() : this.getLowQuality()
  }


  public getLicense(): string{
    return this.getPhotograph().getLicense()
  }


  public getAuthorId(): string{
    return this.author?.getId();
  }


  private async getAuthor(): Promise<void>{
    this.author = await this.data.getAuthorById(this.photograph.getAuthor())
  }

  private async getSource(): Promise<void>{
    this.source = await this.data.getSourceById(this.photograph.getSource())
  }


  public getAuthorName(): string{
    return this.author?.getName()
  }

}
