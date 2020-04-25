import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShopListComponent } from './shop-list/shop-list.component';
import { ShopFormComponent } from './shop-form/shop-form.component';
import { AppComponent } from './app.component';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';
import { VisitFormComponent } from './visit-form/visit-form.component';
import { VisitListComponent } from './visit-list/visit-list.component';
import { ClusterListComponent } from './cluster-list/cluster-list.component';


const routes: Routes = [
  { path: 'shops',
    children: [
      { path: 'view', component: ShopListComponent },
      { path: 'create/:id', component: ShopFormComponent},
      { path: 'create', component: ShopFormComponent}
    ]
  },
  { path: 'visits', 
    children: [
      { path: 'shop/:id', component: VisitListComponent },
      { path: 'shop/cluster/:id', component: ClusterListComponent },
      { path: 'create/:id', component: VisitFormComponent },
      { path: 'create', component: VisitFormComponent }  
    ] 
  },
  { path: 'home', component: WelcomePageComponent }, 
  { path: '', redirectTo:"/home", pathMatch:'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
