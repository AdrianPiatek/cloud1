import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {ConfirmPassword} from "../../utility/confirm-password";

@Component({
  selector: 'app-register',
  standalone: true,
    imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  private formBuilder = inject(FormBuilder)

  registerForm = this.formBuilder.group({
    login: ['', [Validators.required, Validators.minLength(4)]],
    password: ['', [Validators.required, Validators.minLength(4)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(4)]]
  }, {validators: ConfirmPassword})

  onSubmit(){
    console.log(this.registerForm.value)
  }
}
