import { Component, Input, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/storage';
import { Author } from 'src/app/model/author';
import { Photograph } from 'src/app/model/photograph';
import { DataService } from 'src/app/service/data.service';

@Component({
  selector: 'app-author-card',
  templateUrl: './author-card.component.html',
  styleUrls: ['./author-card.component.scss']
})
export class AuthorCardComponent implements OnInit {

  @Input() private author: Author;
  private image: string = "assets/avatar_placeholder.png"
  private thumbnailImage: string;

  constructor(private data: DataService, private storage: AngularFireStorage) { }

  ngOnInit(): void {}

  public Id(): String     { return this.author.getId() }
  public Name(): String   { return this.author.getName() }
  public AuthorImage(): String { return this.image }


  private getPhotographs(): Photograph[]{
    return this.data.getPhotographsByAuthor(this.author.getId())
  }

  public getNumberOfPhotographs(): Number {
    return this.getPhotographs()?.length
  }

  public ThumbnailImage(): string{
    this.LoadThumbnailImage()
    return this.thumbnailImage
  }

  public LoadThumbnailImage(): void {
    var photographs = this.getPhotographs()
    this.thumbnailImage = photographs[0].getLowQuality(this.storage)
  }

}
