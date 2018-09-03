import { VehicleService } from './services/vehicle.service';
import { InvoiceService } from './services/invoice.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http'
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { VehiclesComponent } from './components/vehicles/vehicles.component';
import { InvoicesComponent } from './components/invoices/invoices.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HistoryComponent } from './components/history/history.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { MenuComponent } from './components/menu/menu.component';
import { CheckinComponent } from './components/checkin/checkin.component';
import { CheckoutComponent } from './components/checkout/checkout.component';

const appRoutes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'checkin',
    component: CheckinComponent
  },
  {
    path: 'invoices',
    component: InvoicesComponent
  },
  {
    path: 'history',
    component: HistoryComponent
  },
  {
    path: 'vehicles',
    component: VehiclesComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    VehiclesComponent,
    InvoicesComponent,
    HeaderComponent,
    FooterComponent,
    HistoryComponent,
    PageNotFoundComponent,
    MenuComponent,
    CheckinComponent,
    CheckoutComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpModule,
    RouterModule.forRoot(
      appRoutes
    )
  ],
  providers: [
    InvoiceService,
    VehicleService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
