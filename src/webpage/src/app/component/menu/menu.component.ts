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

import { AfterViewInit, HostListener } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit, AfterViewInit {

  public isCollapsed: boolean = true;
  private isMenuClicked: boolean = false;


  @HostListener('window:resize', [])
  onWindowResize(): void {
    this.showMenu()

}

  constructor(private auth: AuthService) { }
  ngAfterViewInit(): void {
    this.isUserAdmin()
  }

  ngOnInit(): void {
  }


  public isUserAdmin(): boolean{
    return this.auth.userIsRegistered()
  }

  public menuClicked(){
    this.isMenuClicked = !this.isMenuClicked
  }


  public showMenu(): boolean{
    if(window.innerWidth > 800){
      return true
    }
    else{
      return this.isMenuClicked
    }
  }

}

