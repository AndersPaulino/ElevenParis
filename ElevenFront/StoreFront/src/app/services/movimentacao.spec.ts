import { TestBed } from "@angular/core/testing";
import { MovimentacaoService } from "./movimentacao.service";

describe('movimentacaoService', ()=>{
    let service: MovimentacaoService;

    beforeEach(()=>{
        TestBed.configureTestingModule({});
        service = TestBed.inject(MovimentacaoService);
    });

    it('should be created', () =>{
        expect(service).toBeTruthy();
    })
})