import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {ConfirmPassword} from "../../utility/confirm-password";
import {CognitoService} from "../../service/cognito-service/cognito.service";

@Component({
  selector: 'app-register',
  standalone: true,
    imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  private formBuilder = inject(FormBuilder)
  private cognitoService = inject(CognitoService)

  registerForm = this.formBuilder.group({
    login: ['', [Validators.required, Validators.minLength(4)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', Validators.required]
  }, {validators: ConfirmPassword})

  onSubmit(){
    let username = this.registerForm.value.login
    let password = this.registerForm.value.password
    let email = this.registerForm.value.email
    if (username && password && email)
      this.cognitoService.register(username, email, password)
  }
}
