import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink} from "@angular/router";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {CognitoService} from "../../service/cognito-service/cognito.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private formBuilder = inject(FormBuilder)
  cognitoService = inject(CognitoService)

  loginForm = this.formBuilder.group({
    login: ['', [Validators.required, Validators.minLength(4)]],
    password: ['', [Validators.required, Validators.minLength(4)]]
  })

  onSubmit(){
    let username = this.loginForm.value.login
    let password = this.loginForm.value.password
    if (username && password)
      this.cognitoService.login(username, password)
  }
}
