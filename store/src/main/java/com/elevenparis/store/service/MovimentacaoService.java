package com.elevenparis.store.service;

import com.elevenparis.store.dto.MovimentacaoDTO;
import com.elevenparis.store.dto.ProdutoDTO;
import com.elevenparis.store.entity.Movimentacao;
import com.elevenparis.store.entity.Produto;
import com.elevenparis.store.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimentacaoService {
    private final MovimentacaoRepository movimentacaoRepository;

    @Autowired
    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository){this.movimentacaoRepository = movimentacaoRepository;}

    @Transactional(readOnly = true)
    public List<MovimentacaoDTO> findByAtivo(boolean ativo){
        List<Movimentacao> movimentacaos = movimentacaoRepository.findByAtivo(ativo);
        return movimentacaos.stream()
                .map(MovimentacaoDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoDTO> findByDiaRegistro(LocalDate registro){
        List<Movimentacao> movimentacaos = movimentacaoRepository.findByDiaRegistro(registro);

        return movimentacaos.stream()
                .map(MovimentacaoDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoDTO> findByDiaAtualizar(LocalDate atualizar){
        List<Movimentacao> movimentacaos = movimentacaoRepository.findByDiaAtualizar(atualizar);

        return movimentacaos.stream()
                .map(MovimentacaoDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<MovimentacaoDTO> findById(Long id) {
        return movimentacaoRepository.findById(id).map(movimentacao -> {
            MovimentacaoDTO movimentacaoDTO = new MovimentacaoDTO(movimentacao);
            // Você pode configurar aqui a carga do produto associado, se necessário.
            // movimentacaoDTO.setProduto(new ProdutoDTO(movimentacao.getProduto()));
            return movimentacaoDTO;
        });
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoDTO> findAll() {
        List<Movimentacao> movimentacoes = movimentacaoRepository.findAll();
        return movimentacoes.stream().map(MovimentacaoDTO::new).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(Movimentacao movimentacao) {
        BigDecimal valorVenda = movimentacao.getValorVenda();
        BigDecimal valorCompra = movimentacao.getValorCompra();
        BigDecimal diferenca = valorVenda.subtract(valorCompra);
        movimentacao.setValorTotal(diferenca);

        movimentacao.setTotalProduto(movimentacao.getEntrada() - movimentacao.getSaida());
        movimentacaoRepository.save(movimentacao);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizar(Long id, Movimentacao movimentacao) {
        Optional<Movimentacao> movimentacaoExistenteOptional = movimentacaoRepository.findById(id);

        if (movimentacaoExistenteOptional.isPresent()) {
            Movimentacao movimentacaoExistente = movimentacaoExistenteOptional.get();
            if (movimentacao.getEntrada() != movimentacao.getEntrada()){
                movimentacaoExistente.setEntrada(movimentacaoExistente.getEntrada() + movimentacao.getEntrada());
            }
            if (movimentacao.getEntrada() == movimentacao.getEntrada()){
                movimentacaoExistente.setEntrada(movimentacaoExistente.getEntrada());
            }
            if(movimentacao.getSaida() != movimentacao.getSaida()){
                movimentacaoExistente.setSaida(movimentacao.getSaida());
            }
            if(movimentacao.getSaida() == movimentacao.getSaida()){
                movimentacaoExistente.setSaida(movimentacao.getSaida());
            }
            if(movimentacao.getValorCompra() != null){
                movimentacaoExistente.setValorCompra(movimentacao.getValorCompra());
            }
            if(movimentacao.getValorVenda() != null){
                movimentacaoExistente.setValorVenda(movimentacao.getValorVenda());
            }
            BigDecimal valorVenda = movimentacao.getValorVenda();
            BigDecimal valorCompra = movimentacao.getValorCompra();
            BigDecimal diferenca = valorVenda.subtract(valorCompra);
            movimentacaoExistente.setValorTotal(diferenca);

            movimentacaoExistente.setTotalProduto(movimentacao.getEntrada() - movimentacao.getSaida());

            movimentacaoRepository.save(movimentacaoExistente);
        } else {
            throw new IllegalArgumentException("ID Inválido!");
        }
    }



    public void validarMovimentacao(final Movimentacao movimentacao){
        int entrada = movimentacao.getEntrada();
        int saida = movimentacao.getSaida();

        if (entrada == 0){
            throw new IllegalArgumentException("Nome De Produto Não Preenchido");
        }
        if (entrada < saida){
            throw new IllegalArgumentException("Erro! Saida maior do que os produtos em estoque!");
        }

    }
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void deletar(Long id){
        Optional<Movimentacao> movimentacaoExistenteOptional = movimentacaoRepository.findById(id);

        if (movimentacaoExistenteOptional.isPresent()){
            Movimentacao movimentacaoExistente = movimentacaoExistenteOptional.get();
            movimentacaoExistente.setAtivo(false);
        } else {
            throw new IllegalArgumentException("ID Invalido");
        }
    }

}
