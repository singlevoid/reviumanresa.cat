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
  selector: 'app-photograph-card',
  templateUrl: './photograph-card.component.html',
  styleUrls: ['./photograph-card.component.scss']
})
export class PhotographCardComponent implements OnInit {

  @Input() private photograph: Photograph;
  private backgroundImage: string = "assets/card-back-horizontal.jpg";

  constructor(private storage: AngularFireStorage,
              private data: DataService) { }

  ngOnInit(): void {
  }


  public getPhotograph(): Photograph{
    return this.photograph
  }


  public getId(): string{
    return this.getPhotograph()?.getId()
  }


  public getImgUrl(): string{
    return this.getPhotograph()?.getLowQuality()
  }


  public getDate(): string{
    var date = this.getPhotograph().getDate().getYear().getHumanDate()
    date = date.split('[').join('')
    date = date.split(']').join('')
    return date;
  }


  public getTitle(): string{
    return this.getPhotograph().getTitle()
  }


  public getAuthor(): string{
    return this.data.getAuthorByID(this.getPhotograph().getAuthor())?.getName()
  }


  public getBackgroundImage(): string{
    return this.backgroundImage
  }


  public setBackgroundImage(event?: any): void{
    var ratio = event.target.width / event.target.height
    if(ratio >= 1){
      this.backgroundImage = "assets/card-back-horizontal.jpg";
    }
    else{
      this.backgroundImage = "assets/card-back-vertical.jpg";
    }
  }
}
