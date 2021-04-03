import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
@Component({
  selector: 'app-tag-form',
  templateUrl: './tag-form.component.html',
  styleUrls: ['./tag-form.component.scss']
})
export class TagFormComponent implements OnInit {


  private form: FormGroup;


  constructor(
    private formBuilder: FormBuilder
    ) { }


  ngOnInit(): void {
    this.createForm()
  }


  public getForm(): FormGroup{
    return this.form;
  }


  private createForm(): void{
    this.form = this.formBuilder.group({
      name:         [null, Validators.required],
      description:  [null, Validators.required]});
   }


  public setForm(options: {name?: string, description?: string}): void{
    if(options.name) {this.form.controls['name'].setValue(options.name)}
    if(options.description) {this.form.controls['description'].setValue(options.description)}
  }


  public formToJSON(): {name: string, description: string}{
    return  {name: this.form.controls['name'].value,
             description: this.form.controls['description'].value}
  }

}
