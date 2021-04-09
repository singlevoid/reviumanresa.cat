
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


import { RangedDate } from "./ranged-date"


export class ObjectDate{


  constructor(private year: RangedDate, private month: RangedDate, private day: RangedDate){}


  public get isDated(): boolean{
    return (this.getYear().isDated() || this.getMonth().isDated() || this.getDay().isDated())
  }


  public getYear(): RangedDate{
    return this.year
  }


  public getMonth(): RangedDate{
    return this.month
  }


  public getDay(): RangedDate{
    return this.day
  }

  public toJSON(): object{

    var data = {}

    if ( this.getYear().toJSON() )  { data["year"] =  this.getYear().toJSON() }
    if ( this.getMonth().toJSON() ) { data["month"] = this.getMonth().toJSON() }
    if ( this.getDay().toJSON() )   { data["day"] = this.getDay().toJSON() }

    if (Object.keys(data).length === 0) { return null }

    return data;
  }

}
