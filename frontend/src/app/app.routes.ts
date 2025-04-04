import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent
    },
    {
        path: 'product',
        loadChildren: () => import('./product/product-routing.module').then(m => m.ProductRoutingModule),
    },
    {
        path: 'uom',
        loadChildren: () => import('./uom/uom-routing.module').then(m => m.UomRoutingModule),
    }
];
