package com.elevenparis.store.service;

import com.elevenparis.store.auditing.Audit;
import com.elevenparis.store.dto.ProdutoDTO;
import com.elevenparis.store.entity.Produto;
import com.elevenparis.store.repository.AuditRepository;
import com.elevenparis.store.repository.ProdutoRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    private AuditRepository auditRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, AuditRepository auditRepository){
        this.produtoRepository = produtoRepository;
        this.auditRepository = auditRepository;
    }

    @Transactional(readOnly = true)
    public Optional<ProdutoDTO> findById(Long id){ return produtoRepository.findById(id).map(ProdutoDTO::new);}

    @Transactional(readOnly = true)
    public List<ProdutoDTO> findAll(){
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream().map(ProdutoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProdutoDTO findByNome(String nome){
        Produto produto = produtoRepository.findByNomeProduto(nome);
        return new ProdutoDTO(produto);
    }

    @Transactional(readOnly = true)
    public  List<ProdutoDTO> findByAtivo(boolean ativo){
        List<Produto> produtos = produtoRepository.findByAtivo(ativo);
        return produtos.stream()
                .map(ProdutoDTO::new)
                .toList();
    }
    @Transactional(readOnly = true)
    public List<ProdutoDTO> findByDiaRegistro(LocalDate registro){
        List<Produto> produtos = produtoRepository.findByDiaRegistro(registro);

        return produtos.stream()
                .map(ProdutoDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProdutoDTO> findByDiaAtualizar(LocalDate atualizar){
        List<Produto> produtos = produtoRepository.findByDiaAtualizar(atualizar);

        return produtos.stream()
                .map(ProdutoDTO::new)
                .toList();
    }

    public void validarProduto(final Produto produto){
        String nome = produto.getNome();

        if (nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("Nome De Produto Não Preenchido");
        }
        if (!nome.matches("[a-zA-Z0-9 ]+")){
            throw new IllegalArgumentException("Nome De Produto Invalido");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(Produto produto){
        validarProduto(produto);

        Audit audit = new Audit();
        audit.setOperation("CREATE_PRODUTO");
        audit.setCreatedBy(audit.getCreatedBy());
        audit.setCreateDate(audit.getCreateDate());
        auditRepository.save(audit);

        produtoRepository.save(produto);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizar(Long id, Produto produto) {
        validarProduto(produto);
        Optional<Produto> produtoExistenteOptional = produtoRepository.findById(id);

        if (produtoExistenteOptional.isPresent()) {
            Produto produtoExistente = produtoExistenteOptional.get();

            if (produto.getNome() != null) {
                produtoExistente.setNome(produto.getNome());
            }

            if (produto.getTipo() != null) {
                produtoExistente.setTipo(produto.getTipo());
            }
            if (produto.getDescricao() != null) {
                produtoExistente.setDescricao(produto.getDescricao());
            }
            produtoExistente.setAtivo(produto.isAtivo());

            Audit audit = new Audit();
            audit.setOperation("INSERT_PRODUTO");
            audit.setCreatedBy(audit.getCreatedBy());
            audit.setCreateDate(audit.getCreateDate());
            auditRepository.save(audit);

            produtoRepository.save(produtoExistente);
        } else {
            throw new IllegalArgumentException("ID Inválido!");
        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void deletar(Long id){
        Optional<Produto> produtoExistenteOptional = produtoRepository.findById(id);

        if (produtoExistenteOptional.isPresent()){
            Produto produtoExistente = produtoExistenteOptional.get();

            Audit audit = new Audit();
            audit.setOperation("DELETE_PRODUTO");
            audit.setCreatedBy(audit.getCreatedBy());
            audit.setCreateDate(audit.getCreateDate());
            auditRepository.save(audit);

            produtoExistente.setAtivo(false);
        } else {
            throw new IllegalArgumentException("ID Invalido");
        }
    }

}
