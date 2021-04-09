
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
import { AngularFireStorage } from "@angular/fire/storage";
import { MapLocation } from "./map-location";
import { ObjectDate } from "./object-date";
import { RangedDate } from "./ranged-date";

@Injectable({providedIn: 'root'})
export class Photograph {

  private id: string = null;
  private title: string = null;
  private author: string = null;
  private description: string = null;
  private location: MapLocation;
  private date: ObjectDate;
  private tags: string[] = [];
  private license: string = null;
  private source: string = null;
  private lowQualityImage: string = null;
  private highQualityImage: string = null;

  private isDownloading: boolean = false;

  constructor(data?: any){
    this.setData(data)
  }

  public async setData(data: any){
    this.id = data?.id ? data.id : this.id;
    this.title = data?.title ? data.title : this.author;
    this.author = data?.author ? data.author : this.author;
    this.description = data?.description ? data.description : this.description;
    this.tags = data?.tags ? data.tags : this.tags;
    this.license = data?.license ? data.license : this.license;
    this.source = data?.source ? data.source : this.source;

    this.location = new MapLocation(data?.location)

    let year = new RangedDate(data?.date?.year)
    let month = new RangedDate(data?.date?.month)
    let day = new RangedDate(data?.date?.day)
    this.date = new ObjectDate(year, month, day)
  }


  public getId(): string{
    return this.id
  }


  public getSource(): string{
    return this.source
  }

  public getAuthor(): string{
    return this.author
  }


  public getTitle(): string{
    return this.title
  }


  public getDescription(): string{
    return this.description
  }


  public getTags(): string[]{
    return this.tags
  }


  public getDate(): ObjectDate{
    return this.date
  }


  public isGeolocalized(): boolean{
    return this.location.isGeolocalized()
  }


  public getLocation(): MapLocation{
    return this.location
  }


  public getLicense(): string{
    return this.license
  }


  public get isDated(): boolean{
      return (this.date.isDated)
  }


  public getLowQuality(storage?: AngularFireStorage){
    if(this.lowQualityImage){
      return this.lowQualityImage
    }
    else{
      this.downloadLowQualityImage(storage)
    }
  }


  public getHighQuality(storage: AngularFireStorage){
    if(this.highQualityImage){
      return this.highQualityImage
    }
    else{
      this.downloadHighQualityImage(storage)
    }
  }


  private async downloadLowQualityImage(storage: AngularFireStorage): Promise<void>{
    if(!this.isDownloading){
      this.isDownloading = true;
      storage.ref(`low/${this.id}`)
      .getDownloadURL()
      .toPromise().then(
        (url: string) => {
          this.isDownloading = false;
          this.lowQualityImage = url
        })
    }
  }


  private async downloadHighQualityImage(storage: AngularFireStorage): Promise<void>{
    if(!this.isDownloading){
      this.isDownloading = true;
      storage.ref(`high/${this.id}`)
      .getDownloadURL()
      .toPromise().then(
        (url: string) => {
          this.isDownloading = false;
          this.highQualityImage = url
        })
      }
    }


  public JSON(): object{

    var data = {}
    if ( this.getTitle() )              { data["title"] = this.getTitle() }
    if ( this.getDescription() )        { data["description"] = this.getDescription() }
    if ( this.getDate().toJSON() )      { data["date"] = this.getDate().toJSON() }
    if ( this.getLocation().toJSON() )  { data["location"] = this.getLocation().toJSON() }
    if ( this.getLicense() )            { data["license"] = this.getLicense() }
    if ( this.getTags() )               { data["tags"] = this.getTags() }
    if ( this.getSource() )             { data["source"] = this.getSource() }
    if ( this.getAuthor() )             { data["author"] = this.getAuthor() }

    return data;

  }


}
