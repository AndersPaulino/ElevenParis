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
import { ProdutodetailsComponent } from './components/produto/produtodetails/produtodetails.component';
import { ProdutolistComponent } from './components/produto/produtolist/produtolist.component';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    TipoListComponent,
    TipodetailsComponent,
    ProdutodetailsComponent,
    ProdutolistComponent
  ],
  imports: [
    FormsModule,
    RouterModule,
    BrowserModule,
    NgbModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
