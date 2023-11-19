import { Routes } from '@angular/router';
import {LoginComponent} from "./component/login/login.component";
import {RegisterComponent} from "./component/register/register.component";
import {HomeComponent} from "./component/home/home.component";
import {GameComponent} from "./component/game/game.component";

export const routes: Routes = [
  {component: HomeComponent, path: ''},
  {component: LoginComponent, path:'login'},
  {component: RegisterComponent, path: 'register'},
  {component: GameComponent, path: 'game'}
];
