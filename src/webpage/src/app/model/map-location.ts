
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

export class MapLocation{


  private lat: number = null;
  private lng: number = null;


  constructor(options?: {lat?: number, lng?: number}){
    this.set(options)
  }

  public set(options?: {lat?: number, lng?: number}): void{
    this.lat = options?.lat ? options.lat : this.lat;
    this.lng = options?.lng ? options.lng : this.lng;
  }

  public isGeolocalized(): boolean{
    return !!(this.getLatitude() && this.getLongitude());
  }


  public getLatitude(): number{
    return this.lat
  }


  public getLongitude(): number{
    return this.lng
  }

  public toJSON(): object{
    return {lat: this.getLatitude(),
            lng: this.getLongitude()}
  }

}
