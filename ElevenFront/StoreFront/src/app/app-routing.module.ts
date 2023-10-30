import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TipoListComponent } from './components/tipo/tipolist/tipolist.component';
import { ProdutolistComponent } from './components/produto/produtolist/produtolist.component';
import { MenuComponent } from './components/menu/menu.component';
import { IndexComponent } from './layout/index/index.component';
import { LoginComponent } from './components/sistema/login/login.component';
import { CadastroComponent } from './components/sistema/cadastro/cadastro.component';

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: 'full' },
  { path:"login", component: LoginComponent},
  { path: "cadastro", component: CadastroComponent},
  {
    path: "admin", component: IndexComponent, children: [
      {path: "menu", component: MenuComponent},
      { path: "tipo", component: TipoListComponent },
      { path: "produto", component: ProdutolistComponent }
    ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
