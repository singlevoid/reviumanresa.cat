import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import firebase from 'firebase';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private user: firebase.User;

  constructor(private auth: AngularFireAuth) {
    this.auth.onAuthStateChanged(user =>{
      if(user)   {this.user = user}
      else       {this.SignInAnonymously()}
    })
   }


  public userIsRegistered(): boolean{
    return !!(this.user && !this.user.isAnonymous)
  }


  public async signInWithEmailAndPassword(email: string, password: string): Promise<void>{
    try{
      await this.auth.signInWithEmailAndPassword(email, password)
    }
    catch(e){
      console.log(e)
    }
  }

  public async userIsAnonymous(): Promise<Boolean>{
    if(this.user)   { return this.user.isAnonymous }
    else            { return false }
  }

  public async SignInAnonymously(): Promise<firebase.auth.UserCredential>{
    try{
      console.log("Logging Anonymously")
      return await this.auth.signInAnonymously()
    }
    catch (error){
      console.log(error)
    }
  }
}
