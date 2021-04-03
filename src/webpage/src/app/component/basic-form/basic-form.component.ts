import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-basic-form',
  templateUrl: './basic-form.component.html',
  styleUrls: ['./basic-form.component.scss']
})
export class BasicFormComponent implements OnInit {


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
    return  {       name: this.getName(),
             description: this.getDescription()
            }
  }

  public getName(): string{
    return this.form.controls['name'].value
  }

  public getDescription(): string{
    return this.form.controls['description'].value
  }

}
