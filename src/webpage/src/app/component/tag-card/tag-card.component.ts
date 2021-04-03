import { Component, Input, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/storage';
import { Tag } from 'src/app/model/tag';
import { DataService } from 'src/app/service/data.service';

@Component({
  selector: 'app-tag-card',
  templateUrl: './tag-card.component.html',
  styleUrls: ['./tag-card.component.scss']
})
export class TagCardComponent implements OnInit {

  @Input() private tag: Tag;
  private image: string;

  constructor(private storage: AngularFireStorage,
              private data: DataService) { }

  ngOnInit(): void {
  }

  public getImage(): string{
    return this.tag.getLowQuality(this.storage)
  }


  public getName(): string{
    return this.tag.getName();
  }


  public getDescription(): string{
    return this.tag.getDescription();
  }


  public getId(): string{
    return this.tag.getId();
  }

  public getNumberOfPhotographs(): number{
    return this.data.getPhotographsByTag(this.tag.getId())?.length
  }
}
