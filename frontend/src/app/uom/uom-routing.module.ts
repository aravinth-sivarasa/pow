import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UomViewComponent } from './view/uom-view.component';

const routes: Routes = [
  {
    path: '',
    component: UomViewComponent
  },
  {
    path: ':code',
    component: UomViewComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UomRoutingModule { }
