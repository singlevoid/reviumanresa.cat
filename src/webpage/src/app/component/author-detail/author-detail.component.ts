import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataService } from '../../service/data.service';
import { Author } from '../../model/author';

@Component({
  selector: 'app-author-detail',
  templateUrl: './author-detail.component.html',
  styleUrls: ['./author-detail.component.scss']
})
export class AuthorDetailComponent implements OnInit {

  private id: string;
  private author: Author;
  private image: string = "assets/avatar_placeholder.png"


  constructor(private data: DataService, private route: ActivatedRoute,) { }

  ngOnInit(): void {
    this.updateParams()
  }

  private updateParams(): void{
    this.route.paramMap.subscribe(
      params => {
        this.id = params.get("id");
        this.load()
      })}


  public getAuthor(): Author{
    return this.author
  }

  public getAuthorImage(): string{
    return this.image
  }


  private async load(){
    this.author = await this.data.getAuthorById(this.id)
  }


  public getAuthorPhotographs(){
    var photographs = this.data.getPhotographs()
    var authorPhotographs = []
    for (const photo of photographs){
      if (photo.getAuthor() == this.getAuthor()?.getId()){
        authorPhotographs.push(photo)
      }
    }
    console.log(authorPhotographs)
    return authorPhotographs;
  }

}
