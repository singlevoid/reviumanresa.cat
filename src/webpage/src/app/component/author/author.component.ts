import { Component, OnInit } from '@angular/core';
import { Author } from 'src/app/model/author';
import { DataService } from 'src/app/service/data.service';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.scss']
})
export class AuthorComponent implements OnInit {

  constructor(private data: DataService) { }

  ngOnInit(): void {
  }

  public Authors(): Author[]{ return this.data.getAuthors() }

}
