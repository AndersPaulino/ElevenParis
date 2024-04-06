import { Tipo } from './tipo';

describe('Tipo', () => {
  it('should create an instance', () => {
    const tipo = new Tipo();
    expect(tipo).toBeTruthy();
  });

  it('should have default values for properties', () => {
    const tipo = new Tipo();
    expect(tipo.id).toBeUndefined();
    expect(tipo.ativo).toBeUndefined();
    expect(tipo.registro).toBeUndefined();
    expect(tipo.atualizar).toBeUndefined();
    expect(tipo.nameTipo).toBeUndefined();
    expect(tipo.filtro).toBe('');
  });

  it('should update properties using setter methods', () => {
    const tipo = new Tipo();

    tipo.id = 1;
    tipo.ativo = true;
    tipo.registro = '2023-12-01';
    tipo.atualizar = '2023-12-01';
    tipo.nameTipo = 'Tipo1';
    tipo.filtro = 'Filtro1';

    expect(tipo.id).toBe(1);
    expect(tipo.ativo).toBe(true);
    expect(tipo.registro).toBe('2023-12-01');
    expect(tipo.atualizar).toBe('2023-12-01');
    expect(tipo.nameTipo).toBe('Tipo1');
    expect(tipo.filtro).toBe('Filtro1');
  });
});
