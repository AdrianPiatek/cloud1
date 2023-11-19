import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

export const ConfirmPassword: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const password = control.get('password')
  const confirmPassword = control.get('confirmPassword')

  return password && confirmPassword && password.value === confirmPassword.value ? null : {passwordNotMatch: true};
}
