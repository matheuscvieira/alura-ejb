package br.com.caelum.livraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.livraria.modelo.Autor;

@Stateless   //javax.ejb
//@TransactionManagement(TransactionManagementType.BEAN) // BMT - Bean Managed Transaction - devemos usar explicitamente o begin() e commit()
@TransactionManagement(TransactionManagementType.CONTAINER)  //opcional, pois já é o default para SessionBean - // CMT - Container Managed Transaction
public class AutorDao {

	@PersistenceContext
	private EntityManager manager;
	
	@PostConstruct   //Método tambem chamado de callback
	void aposCriacao(){
		System.out.println("AutorDao foi criado");
	}

	// Transação aberta ao iniciar o método	
	//@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) // Garante que o metodo executado fora de uma transação, se uma estiver ativa, será suspensa enquanto o método não terminar, se não tiver ativa, uma nova não será criada
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)  // Tanto faz! Se houver uma transação aberta ao executar um método, a mesma será utilizada, caso não exista uma transação aberta, o método será executado normalmente, sem abrir uma nova transação
	//@TransactionAttribute(TransactionAttributeType.NEVER) // Jamais deve haver uma transação nessa execução, caso exista uma exceção é lançada
	//@TransactionAttribute(TransactionAttributeType.MANDATORY)  //Container verifica se ja existe uma transação aberta, caso contrário, não cria uma nova e lança uma exceção
	//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // Sempre deve usar uma transação nova. Se uma estiver ativa, então é suspensa, uma nova é criada para processar o método, e ao final,
	// a anterior é retomada, caso nao exista uma ativa, uma nova será criada para processar o método	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)  //opcional, pois já é default para SessionBean - ao chamar o método e já houver uma transação aberta, 
	// o container a utiliza para executar o método, entretanto, caso nenhuma tarnsação esteja aberta, o container irá criar uma nova transação para processar o método
	public void salva(Autor autor) {
		
		System.out.println("Salvando Autor " + autor.getNome());
		
//		try {
//			Thread.sleep(20000);  //20s
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		};
		
		//Caused by: java.lang.IllegalStateException: A JTA EntityManager cannot use getTransaction()
		// JTA Java Transaction API
		//manager.getTransaction().begin();
		manager.persist(autor);
		//manager.getTransaction().commit();
		
		System.out.println("Salvou Autor " + autor.getNome());
	}
	
	public List<Autor> todosAutores() {
		return (List<Autor>) manager.createQuery("select a from Autor a", Autor.class).getResultList();
	}

	public Autor buscaPelaId(Integer autorId) {
		Autor autor = this.manager.find(Autor.class, autorId);
		return autor;
	}
	
}
