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


import { Injectable } from "@angular/core";
import { AngularFirestore } from "@angular/fire/firestore";
import { Locale } from "src/environments/locale";
import { LocalStore } from "../model/local-storage";
import { Tag } from "../model/tag";
import { BaseManager } from "./base.manager";


@Injectable({providedIn: 'root'})
export class TagManager extends BaseManager{


  protected data: Tag[] = [];
  protected document: string = "tag"
  protected local: LocalStore;


  constructor(protected store: AngularFirestore, protected locale: Locale){
    super()
    this.local = new LocalStore(this.document, 24 * 60 * 60, locale);
  }


  public get(): Tag[]{
    return this.data;
  }

  protected parseDataObject(data: any): Tag {
    return new Tag(data)
  }


  public getTags(): Tag[]{
    return this.data
  }


  public async getTagsRaw(){
    return {en: await this.downloadData(false, "en"),
            es: await this.downloadData(false, "es"),
            ca: await this.downloadData(false, "ca")}
  }


  public async downloadTagById(id: string, locale:string): Promise<Tag>{
    return await this.downloadDataById(id, locale)
  }


  public getTag(id: string): Tag{
    for (const tag of this.getTags()){
      if (tag.getId() == id){
        return tag
      }
    }
  }


  public getTagsByIds(ids: string[]): Tag[]{
    const tagList: Tag[] = []
    for(const tag of this.getTags()){
      if(ids.includes(tag.getId())){
        tagList.push(tag)
      }
    }
    return tagList;
  }


  public uploadTagById(id: string, data: any, locale: string): void{
    console.log(`Uploading tag ${id} of ${locale} with data:`)
    console.log(data)
    this.uploadDataById(id, data, locale)
  }


  public async createNewTag(data: any, locale: string){
    return await this.CreateNewDocument(data, locale)
  }
}
