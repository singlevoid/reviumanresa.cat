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


import { AngularFirestore, DocumentData, QuerySnapshot } from "@angular/fire/firestore";
import { Locale } from "src/environments/locale";
import { LocalStore } from "../model/local-storage";


export abstract class BaseManager{


  protected abstract data: any;
  protected dataset: string = "dataset-1-1"
  protected local: LocalStore;
  protected document: string;
  protected abstract store: AngularFirestore;
  protected abstract locale: Locale;


  constructor(){}


  protected abstract parseDataObject(object: any): Object


  public async load(): Promise<void>{
    if(this.local.dataIsValid())   {this.loadFromLocal()}
    else                           {await this.loadFromServer()}
  }


  private async loadFromServer(): Promise<void>{
    await this.downloadData()
    this.local.saveData(this.data);
  }


  private loadFromLocal(): void{
    console.log(`Loading ${this.document} (${this.locale.getLocale()}) from local storage`)
    this.local.getData().forEach((item: Object) => this.data.push(this.parseDataObject(item)))
  }


  protected async downloadData(fastLoading = true, locale:string = this.locale.getLocale()): Promise<any[]>{
    console.log(`Downloading ${this.document} (${locale}) of ${this.dataset}`)
    var data: any[] = []
    try{
      await this.store
        .collection(this.dataset)
        .doc(this.document)
        .collection(locale)
        .get().toPromise()
        .then( query => data = this.parseQuerySnapshot(query, fastLoading) )
        return data;
    }
    catch(e){
      console.log(e);
      return data;
    }
  }


  protected async downloadDataById(id: string, locale:string = this.locale.getLocale()): Promise<any>{
    var data: any[] = []
    try{
      await this.store
      .collection(this.dataset)
      .doc(this.document)
      .collection(locale)
      .doc(id)
      .get().toPromise()
      .then(query => {data = this.parseDataSnapshot(query)})
      return data;
    }
    catch (e){
      console.log(e);
      return data;
    }
  }


  private parseQuerySnapshot(query: QuerySnapshot<DocumentData>, fastLoading: boolean): any[] {
    const data: any[] = [];
    console.log(`Total ${this.document} downloaded: ${query.docs?.length}`);
    for(const item of query.docs){
      data.push(this.parseDataSnapshot(item));
      if(fastLoading){
        this.data = data;
      }
    }
    return data;
  }


  private parseDataSnapshot(item: any): any {
    const data = item.data();
    data['id'] = item.id;
    return this.parseDataObject(data)
  }


  protected async uploadDataById(id:string, data: any, locale:string = this.locale.getLocale()): Promise<any>{
    try{
      await this.store
        .collection(this.dataset)
        .doc(this.document)
        .collection(locale)
        .doc(id)
        .set(data)
    console.log('Upload completed')
    }catch(e){
      console.log(e)
    }
  }


  protected async CreateNewDocument(data: any, locale:string = this.locale.getLocale()): Promise<any>{
    try{
     var query = await this.store
              .collection(this.dataset)
              .doc(this.document)
              .collection(locale)
              .add(data)
    return query.id
    }catch(e){
      console.log(e)
    }
  }


  public async updateServerData(): Promise<void>{
    await this.downloadData()
  }
}

