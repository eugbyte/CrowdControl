import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShopListComponent } from './shop-list/shop-list.component';
import { ShopFormComponent } from './shop-form/shop-form.component';
import { AppComponent } from './app.component';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';


const routes: Routes = [
  { path: 'shops',
    children: [
      { path: 'view', component: ShopListComponent },
      { path: 'create', component: ShopFormComponent}
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
