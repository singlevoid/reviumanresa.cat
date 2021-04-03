import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-date-form',
  templateUrl: './date-form.component.html',
  styleUrls: ['./date-form.component.scss']
})
export class DateFormComponent implements OnInit {

  public isExact: boolean = true;
  private form: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void { this.createForm() }

  public getForm(): FormGroup{ return this.form }

  private createForm(): void{
    this.form = this.formBuilder.group({
      start:          [null, Validators.required],
      end:            [null, Validators.required]});
   }

  public setForm(options: {start?: number, end?: number}): void{
    if(options?.start)    {this.getStartControl().setValue(options.start)}
    if(options?.end)      {this.getEndControl().setValue(options.end)}
  }

  public getStartControl(): AbstractControl{
    return this.form.controls['start']
  }

  public toJSON(): object{
    return {start: this.getStartValue(),
            end: this.getEndValue()}
  }

  public getEndControl(): AbstractControl{
    return this.form.controls['end']
  }

  public getStartValue(): number {
    return this.form.controls['start'].value
  }

  public getEndValue(): number{
    return this.form.controls['end'].value
  }

  public clicked(): void{
    this.isExact = !this.isExact
  }

}
