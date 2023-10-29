import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TipoListComponent } from './components/tipo/tipolist/tipolist.component';
import { ProdutolistComponent } from './components/produto/produtolist/produtolist.component';

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: 'full' },
  { path: "tipo", component: TipoListComponent },
  { path: "produto", component: ProdutolistComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
