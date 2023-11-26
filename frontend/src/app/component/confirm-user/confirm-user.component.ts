import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {CognitoService} from "../../service/cognito-service/cognito.service";

@Component({
  selector: 'app-confirm-user',
  standalone: true,
    imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './confirm-user.component.html',
  styleUrl: './confirm-user.component.css'
})
export class ConfirmUserComponent {
  private cognitoService = inject(CognitoService)
  private formBuilder = inject(FormBuilder)

  confirmForm = this.formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(4)]],
    code: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]]
  })

  onSubmit() {
    let username = this.confirmForm.value.username
    let code = this.confirmForm.value.code
    if(username && code)
      this.cognitoService.confirmUser(username, code)
  }
}
