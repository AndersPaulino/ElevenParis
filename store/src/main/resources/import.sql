INSERT INTO tb_estoque(cl_nome, ativo, registro) VALUES('Estoque', true, now());
INSERT INTO tb_tipo(cl_tipo, ativo, registro) VALUES('Tipo', true, now());
INSERT INTO tb_produto(nome, tipo_id, descricao, ativo, registro) VALUES('Tipo', 1, 'alguma coisa', true, now());

