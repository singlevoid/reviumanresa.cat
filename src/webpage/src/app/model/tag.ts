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

import { AngularFireStorage } from "@angular/fire/storage";
import { MapLocation } from "./map-location";


export class Tag{


  private id: string = "";
  private name: string = "";
  private description: string = "";
  private location: MapLocation = new MapLocation();

  private lowQualityImage: string;
  private highQualityImage: string;
  private isDownloading: boolean = false;
  public selected: boolean;


  constructor(options?: {id?: string, name?: string, description?: string, location?: {lat?: number, lng?: number}}){

    if(options){
      this.setData(options)
    }
  }


  public setData(options: {id?: string, name?: string, description?: string, location?: {lat?: number, lng?: number}}): void{
    if(options.id)          {this.id = options.id}
    if(options.name)        {this.name = options.name}
    if(options.description) {this.description = options.description}
    if(options.location)    {this.location = new MapLocation(options.location)}
  }


  public getId(): string{
    return this.id
  }


  public getName(): string{
    return this.name
  }


  public getDescription(): string{
    return this.description;
  }


  public getLocation(): MapLocation{
    return this.location
  }


  public JSON(): any{
    return {name: this.name,
            description: this.description,
            location: {lat: this.location.getLatitude(), lng: this.location.getLongitude() }}
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


  public async downloadLowQualityImage(storage: AngularFireStorage): Promise<void>{
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


  public async downloadHighQualityImage(storage: AngularFireStorage): Promise<void>{
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

}

