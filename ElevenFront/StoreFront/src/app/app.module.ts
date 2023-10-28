import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TipoListComponent } from './components/tipo/tipolist/tipolist.component';
import { TipodetailsComponent } from './components/tipo/tipodetails/tipodetails.component';

@NgModule({
  declarations: [
    AppComponent,
    TipoListComponent,
    TipodetailsComponent
  ],
  imports: [
    FormsModule,
    RouterModule,
    BrowserModule,
    NgbModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
