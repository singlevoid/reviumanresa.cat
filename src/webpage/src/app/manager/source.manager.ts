import { Injectable } from "@angular/core";
import { AngularFirestore } from "@angular/fire/firestore";
import { Source } from "src/app/model/source";
import { Locale } from "src/environments/locale";
import { LocalStore } from "../model/local-storage";
import { BaseManager } from "./base.manager";


@Injectable({providedIn: 'root'})
export class SourceManager extends BaseManager{


  protected data: Source[] = [];
  protected document: string = "source"
  protected local: LocalStore;

  constructor(protected store: AngularFirestore, protected locale: Locale){
    super()
    this.local = new LocalStore(this.document, 24 * 60 * 60, locale);
  }


  public get() : Source[]{
    return this.data;
  }


  public getSourceById(id: string): Source{
    for(const source of this.get()){
      if(source.getId() == id) {return source}
    }
  }


  protected parseDataObject(data: any): Source {
    return new Source(data)
  }


}
