import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {


  private form: FormGroup;

  constructor(private auth: AuthService,
              private formBuilder: FormBuilder,) { }

  ngOnInit(): void {
    this.createForm()
  }

  public getForm(): FormGroup{return this.form;}


  public createForm(): void{
    this.form = this.formBuilder.group({
      email: null,
      password: null
    })
  }

  private getPassword(): string{
    return this.form.controls['password'].value
  }

  private getEmail(): string{
    return this.form.controls['email'].value
  }

  public async signIn(): Promise<void>{
    this.auth.signInWithEmailAndPassword(this.getEmail(), this.getPassword())
  }

}
