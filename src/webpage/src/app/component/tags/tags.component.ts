import { Component, OnInit } from '@angular/core';
import { Tag } from 'src/app/model/tag';
import { DataService } from 'src/app/service/data.service';

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.scss']
})
export class TagsComponent implements OnInit {

  constructor(private data: DataService) { }

  ngOnInit(): void {
  }

  public getTags(): Tag[]{
    return this.data.getTags()
  }
}
