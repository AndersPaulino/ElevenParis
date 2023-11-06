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
import { IndexComponent } from './layout/index/index.component';
import { FooterComponent } from './layout/footer/footer.component';
import { HeaderComponent } from './layout/header/header.component';
import { LoginComponent } from './components/sistema/login/login.component';
import { CadastroComponent } from './components/sistema/cadastro/cadastro.component';
import { EstoquelistComponent } from './components/estoque/estoquelist/estoquelist.component';
import { EstoquedetailsComponent } from './components/estoque/estoquedetails/estoquedetails.component';
import { MovimentacaodetailsComponent } from './components/movimentacao/movimentacaodetails/movimentacaodetails.component';
import { MovimentacaolistComponent } from './components/movimentacao/movimentacaolist/movimentacaolist.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CadastroComponent,
    IndexComponent,
    FooterComponent,
    HeaderComponent,
    EstoquelistComponent,
    EstoquedetailsComponent,
    TipoListComponent,
    TipodetailsComponent,
    ProdutolistComponent,
    ProdutodetailsComponent,
    MovimentacaodetailsComponent,
    MovimentacaolistComponent
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
