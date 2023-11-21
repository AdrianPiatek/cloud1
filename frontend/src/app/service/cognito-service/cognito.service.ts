import {computed, inject, Injectable, signal, WritableSignal} from '@angular/core';
import {
  AuthenticationDetails,
  CognitoUser,
  CognitoUserAttribute,
  CognitoUserPool,
  CognitoUserSession
} from 'amazon-cognito-identity-js'
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class CognitoService {

  router = inject(Router)

  private userPool = new CognitoUserPool({
    UserPoolId: 'us-east-1_M4G0Q1jeR',
    ClientId: '5dpsadu1gdsineldc2g2v5ddq6'
  })

  private cognitoUserSession: WritableSignal<CognitoUserSession | undefined> = signal(undefined)
  isLoggedIn = computed(() => {
    let session = this.cognitoUserSession()
    return session ? session.isValid() : false
  })
  login(username: string, password: string) {

    let authDetails = new AuthenticationDetails({
      Username: username,
      Password: password
    })

    let cognitoUser = new CognitoUser({
      Username: username,
      Pool: this.userPool
    })


    cognitoUser.authenticateUser(authDetails, {
      onSuccess: session => {
        this.cognitoUserSession.set(session)
        this.router.navigate(['']).then()
      },
      onFailure: err => {
        console.log(err)
      }
    })
  }

  register(username: string, email: string, password: string){
    let attr = new CognitoUserAttribute({Name: 'email', Value: email})
    this.userPool.signUp(username, password, [attr], [], (err) => {
      if(err) {
        console.log(err)
        return
      }
      this.router.navigate(['confirm']).then()
    })
  }

  confirmUser(username: string, code: string){
    let cognitoUser = new CognitoUser({
      Username: username,
      Pool: this.userPool
    })

    cognitoUser.confirmRegistration(code, false, (err) => {
      if(err) {
        console.log(err)
        return
      }
      this.router.navigate(['login']).then()
    })
  }

  logout(){
    if(this.isLoggedIn())
      this.getCurrUser()?.signOut(() => this.cognitoUserSession.set(undefined))
  }

  getCurrUser(){
    return this.userPool.getCurrentUser()
  }
}
