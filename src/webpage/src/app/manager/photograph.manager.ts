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
import { Photograph } from "../model/photograph";
import { BaseManager } from "./base.manager";


@Injectable({providedIn: 'root'})
export class PhotographManager extends BaseManager{


  protected data: Photograph[] = [];
  protected document: string = "photograph"


  constructor(protected store: AngularFirestore, protected locale: Locale){
    super()
    this.local = new LocalStore(this.document, 24 * 60 * 60, locale);
  }


  public get(): Photograph[] { return this.data }


  public async downloadById(id: string, locale:string): Promise<Photograph>{
    return await this.downloadDataById(id, locale)
  }

  public getPhotographs(): Photograph[]{
    return this.data
  }


  protected parseDataObject(data: any): Photograph {
    return new Photograph(data)
  }


  public async getAllLocales(){
    return {es: await this.downloadData(false, "es"),
            en: await this.downloadData(false, "en"),
            ca: await this.downloadData(false, "ca")}
  }


  public getPhotographById(id:string): Photograph{
    for(const photo of this.getPhotographs()){
      if(photo.getId() == id){
        return photo;
      }
    }
  }


  public getPhotographsByAuthor(authorID: string): Photograph[]{
    var photographs = this.getPhotographs()
    var authorPhotographs = []
    for (const photo of photographs){
      if(photo.getAuthor() == authorID){ authorPhotographs.push(photo) }
    }
    return authorPhotographs;
  }

  public getPhotographsByTag(tagId: string){
    var photographs = this.getPhotographs()
    var tagPhotographs = []
    for (const photo of photographs){
      for(const tag of photo.getTags()){
        if(tag == tagId){
          tagPhotographs.push(photo)
        }
      }
    }
    return tagPhotographs;
  }

  public async uploadPhotographById(id: string, data: any, locale: string): Promise<void>{
    console.log(`Uploading photograph ${id} of ${locale} with data:`)
    console.log(data)
    await this.uploadDataById(id, data, locale)
  }

  public async createNewPhotograph(data: any, locale: string): Promise<string>{
    return await this.CreateNewDocument(data, locale)
  }

}
