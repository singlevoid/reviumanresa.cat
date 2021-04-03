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

import { Component, Input, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/storage';
import { Photograph } from 'src/app/model/photograph';
import { DataService } from 'src/app/service/data.service';

@Component({
  selector: 'app-photograph-masonry',
  templateUrl: './photograph-masonry.component.html',
  styleUrls: ['./photograph-masonry.component.scss']
})
export class PhotographMasonryComponent implements OnInit {

  @Input() private photographs: Photograph[];
  private span = 20;
  public masonryOptions= {gutter: 0,
                          columnWidth: 0,
                          percentPosition: true}


  constructor(private storage: AngularFireStorage, private data: DataService) { }


  ngOnInit(): void {
  }


  public morePhotographToShow(): boolean{
    return this.span < this.photographs.length
  }


  public getPhotographs(): Photograph[]{
    var photographs = this.photographs;
    if( photographs.length < this.span){
      return this.loadedPhotographs(photographs);
    }
    else{
      return this.loadedPhotographs(photographs.slice(0, this.span))
    }
  }


  public loadedPhotographs(photographs: Photograph[]): Photograph[]{
    var loaded = []
    for(const photo of photographs){
      if(photo.getLowQuality(this.storage)) {loaded.push(photo)}
    }
    return loaded;
  }


  public increaseSpan(){
    this.span += 20;
  }


  public getAuthor(photo: Photograph): string{
    return this.data.getAuthorByID(photo.getAuthor())?.getName()
  }


  public getDate(photo: Photograph): string{
    var date = photo.getDate().getYear().getHumanDate()
    date = date.split('[').join('')
    date = date.split(']').join('')
    return date;
  }


  public getBackgroundImage(event?: any){
    if(event){
      if( event.target.height / event.target.width  > 1){
        event.target.src = "assets/card-back.png"
      }
      console.log(event.target.height)

      return "assets/card-back.png"
    }
    return "assets/card-back.jpg"
  }

}
