import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Photograph } from 'src/app/model/photograph';
import { Tag } from 'src/app/model/tag';
import { AuthService } from 'src/app/service/auth.service';
import { DataService } from 'src/app/service/data.service';

@Component({
  selector: 'app-tag-detail',
  templateUrl: './tag-detail.component.html',
  styleUrls: ['./tag-detail.component.scss']
})
export class TagDetailComponent implements OnInit {

  private id: string;
  private tag: Tag;

  constructor(private route: ActivatedRoute,
              private data: DataService,
              private auth: AuthService) { }

  ngOnInit(): void {
    this.updateParams()
  }

  public ID(): string  {return this.id}

  public isUserAdmin(): boolean{
    return this.auth.userIsRegistered()
  }

  private updateParams(): void{
    this.route.paramMap.subscribe(
      params => {
        this.id = params.get("id");
        this.load()
      })}


  private async load(): Promise<void>{
    this.tag =  await this.data.getTag(this.id)
  }


  public getTagName(): string{
    return this.tag?.getName()
  }


  public getTagDescription(): string{
    return this.tag?.getDescription()
  }


  public getTagPhotographs(): Photograph[]{
    return this.data.getPhotographsByTag(this.id)
  }


  public getNumberOfPhotographs(): number{
    return this.getTagPhotographs()?.length
  }

}
