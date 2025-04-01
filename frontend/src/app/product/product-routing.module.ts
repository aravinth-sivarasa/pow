import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProductViewComponent } from './view/product-view.component';

const routes: Routes = [
  {
    path: '',
    component: ProductViewComponent
  },
  {
    path: ':code',
    component: ProductViewComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [RouterModule]
})
export class ProductRoutingModule { }
