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
import { Locale } from "src/environments/locale";


@Injectable({providedIn: 'root'})
export class LocalStore{


  private keyData: string;
  private keyTime: string;
  private time: number


  constructor(key: string, time: number, locale: Locale){
    this.keyData = `${key}_${locale.getLocale()}`
    this.keyTime = `${key}_${locale.getLocale()}_ts`
    this.time = time;
  }


  /**
  * Return the length of stored array.
  *
  * @return {number} Returns the lenght of the stored array.
  */
  public length(): number {
    return this.getData()?.length;
  }


  /**
  * Return a boolean of the emptiness of the stored data.
  *
  * Returns:
  *      (true)  If the stored data is empty.
  *      (false) If the the stored data is not empty
  *
  * @return {booelan} Return a boolean of the emptiness of the stored data.
  */
  public isEmpty(): boolean {
    return this.length() == 0;
  }


  /**
  * Return a boolean of the validity of the stored data.
  *
  * Returns:
  *      (true)  If the data stored is still valid to use.
  *      (false) If the data stored is no longer valid to use.
  *
  * @return {boolean} Return a boolean of the validity of the stored data.
  */
  public dataIsValid(): boolean {
    return (this.currentTime - parseInt(localStorage.getItem(this.keyTime)) < this.time) && !this.isEmpty()
  }


  /**
  * Returns the stored data on the client's local storage.
  *
  * @return {any} Returns the stored data on the client's local storage.
  */
    public getData(): any {
      return JSON.parse(localStorage.getItem(this.keyData))
  }


  /**
  * Save the given data to the client's local storage.
  *
  * @param {any}  data  Data to be saved.
  */
  public saveData(data: any): void {
    console.log("Saving data to local Storage")
    console.log(data)
    localStorage.setItem(this.keyData, JSON.stringify(data))
    localStorage.setItem(this.keyTime, this.currentTime.toString())
  }

  /**
  * Returns the current UTC timestamp in seconds.
  *
  * @return {number} Returns the current UTC timestamp in seconds.
  */
  public get currentTime(): number {
    return (Date.now() / 1000 | 0)
  }
}
