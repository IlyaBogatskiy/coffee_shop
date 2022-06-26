import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./component/home/home.component";
import {OrderComponent} from "./component/order/order.component";
import {CoffeeVarietyComponent} from "./component/coffee-variety/coffee-variety.component";

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'order', component: OrderComponent},
  {path: 'coffee-variety', component: CoffeeVarietyComponent},
  {path: '', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
