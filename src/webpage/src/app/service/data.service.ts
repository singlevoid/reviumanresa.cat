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

import { Injectable } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/storage';
import firebase from 'firebase';
import { Author } from '../model/author';
import { Photograph } from '../model/photograph';
import { Tag } from '../model/tag';
import { AuthorManager } from '../manager/author.manager';
import { PhotographManager } from '../manager/photograph.manager';
import { TagManager } from '../manager/tag.manager';
import { SourceManager } from '../manager/source.manager';
import { Source } from '../model/source';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class DataService {


  private loaded: boolean = false;


  constructor(
    private auth: AuthService,
    private storage: AngularFireStorage,
    private photographManager: PhotographManager,
    private tagManager: TagManager,
    private authorManager: AuthorManager,
    private sourceManager: SourceManager,
    ){}


  public getPhotographs(): Photograph[]{
    this.loadData()
    return this.photographManager.getPhotographs()
  }


  public getSources(): Source[]{
    this.loadData()
    return this.sourceManager.get()
  }

  public getTags(): Tag[]{
    this.loadData()
    return this.tagManager.getTags()
  }


  public getTagsByIds(ids: string[]): Tag[]{
    this.loadData()
    return this.tagManager.getTagsByIds(ids)
  }


  public async getTagById(id:string, locale:string): Promise<Tag>{
    await this.loadData()
    return await this.tagManager.downloadTagById(id, locale)
  }


  public async downloadPhotographById(id:string, locale:string): Promise<Photograph>{
    await this.loadData()
    return await this.photographManager.downloadById(id, locale)
  }


  public async getPhotographById(id: string): Promise<Photograph>{
    await this.loadData()
    return this.photographManager.getPhotographById(id)
  }


  public getAuthors(): Author[]{
    this.loadData()
    return this.authorManager.getAuthors()
  }


  public async getTag(id: string): Promise<Tag>{
    await this.loadData()
    return this.tagManager.getTag(id)
  }


  public async getAuthorById(id: string): Promise<Author>{
    await this.loadData()
    return this.authorManager.getAuthorById(id)
  }


  public async getSourceById(id: string): Promise<Source>{
    await this.loadData()
    return this.sourceManager.getSourceById(id)
  }


  public getAuthorByID(id: string){
    return this.authorManager.getAuthorById(id)
  }

  public isLoaded(): boolean{
    return this.loaded
  }


  public uploadTagById(id: string, data: any, locale: string): void{
    this.tagManager.uploadTagById(id, data, locale)
  }

  public async uploadPhotographById(id: string, data: any, locale: string): Promise<void>{
    await this.photographManager.uploadPhotographById(id, data, locale)
  }

  public async createNewPhotograph(data: any, locale: string): Promise<string>{
    return await this.photographManager.createNewPhotograph(data, locale)
  }

  public async createNewTag(data: any, locale: string): Promise<string>{
    return await this.tagManager.createNewTag(data, locale)
  }


  private async loadData(): Promise<void>{
    if(!this.isLoaded()){
      this.loaded = true;
      await this.photographManager.load()
      await this.tagManager.load()
      await this.authorManager.load()
      await this.sourceManager.load()
    }
  }

  public async uploadImageFile(id: string, quality: string, file: Blob): Promise<void>{

    if(!file) {return}

    var metadata = {
      cacheControl: 'max-age=31536000',
    }
    this.storage
      .ref(`${quality}/${id}`)
      .put(file, metadata)
      .then(console.log)
  }


  public getPhotographsByTag(tagId: string){
    this.loadData()
    return this.photographManager.getPhotographsByTag(tagId)
  }

  public getPhotographsByAuthor(authorID: string){
    this.loadData()
    return this.photographManager.getPhotographsByAuthor(authorID)
  }

}
