import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ShopListComponent } from './shop-list/shop-list.component';
import { ShopFormComponent } from './shop-form/shop-form.component';
import { NavbarComponent } from './navbar/navbar.component';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';
import { VisitListComponent } from './visit-list/visit-list.component';
import { VisitFormComponent } from './visit-form/visit-form.component';
import { ClusterListComponent } from './cluster-list/cluster-list.component';

@NgModule({
  declarations: [
    AppComponent,
    ShopListComponent,
    ShopFormComponent,
    NavbarComponent,
    WelcomePageComponent,
    VisitListComponent,
    VisitFormComponent,
    ClusterListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
