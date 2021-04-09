
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


export class RangedDate{


  private start: number = null;
  private end: number = null;


  constructor(options?: {start?: number, end?: number}){
    this.set(options)
  }


  public set(options?: {start?: number, end?: number}): void{
    this.start = options?.start;
    this.end = options?.end;
  }

  public getStart(): number{
    return this.start;
  }

  public getEnd(): number{
    return this.end;
  }

  public isDated(): boolean {
      return !!(this.start || this.end);
  }


  public isRanged(): boolean{
      return !!(this.start && this.end);
  }


  public getHumanDate(): string{
    if(!this.isDated()){
      return ""
    }


    if(this.isRanged()){
      return `[ ${this.start} - ${this.end} ]`
    }
    else{
      return `${this.start}`
    }
  }

  public toJSON(): object{

    var data = {}

    if ( this.getStart() ) { data["start"] = this.getStart(); }
    if (this.getEnd() )    { data["end"] = this.getEnd() }


    if (Object.keys(data).length === 0) { return null }

    return data;
  }

}
