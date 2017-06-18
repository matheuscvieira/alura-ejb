package br.com.caelum.livraria.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)  //opcional, pois já é o default para SessionBean - // CMT - Container Managed Transaction
public class AutorService {

	@Inject
	AutorDao dao;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void adiciona(Autor autor){
		//mais regras de negócio aqui
		dao.salva(autor);
		//Mudar a TX do método salva para REQUIRES_NEW para isolar sua execução deste método
		//throw new RuntimeException("[ERRO] Erro lançado para testar o rollback da transação.");
	}

	//required
	public List<Autor> todosAutores() {
		return dao.todosAutores();
	}
	
}
