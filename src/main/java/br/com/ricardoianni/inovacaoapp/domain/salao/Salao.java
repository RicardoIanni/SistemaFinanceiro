package br.com.ricardoianni.inovacaoapp.domain.salao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.ricardoianni.inovacaoapp.domain.agendamento.Agendamento;
import br.com.ricardoianni.inovacaoapp.domain.email.EnderecoEmail;
import br.com.ricardoianni.inovacaoapp.domain.endereco.Endereco;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Conta;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Pagamento;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Recebimento;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Fornecedor;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.produto.Produto;
import br.com.ricardoianni.inovacaoapp.domain.produto.ProdutoCompra;
import br.com.ricardoianni.inovacaoapp.domain.produto.ProdutoVenda;
import br.com.ricardoianni.inovacaoapp.domain.servico.Material;
import br.com.ricardoianni.inovacaoapp.domain.servico.PacoteServico;
import br.com.ricardoianni.inovacaoapp.domain.servico.Servico;
import br.com.ricardoianni.inovacaoapp.domain.telefone.Telefone;
import br.com.ricardoianni.inovacaoapp.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Salao extends Usuario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idsalao")
	private Integer idSalao;
	
	@Column(length = 14, nullable = true)
	private String cnpj;
	
	@OneToMany(mappedBy = "salaoEmail")
	private Set<EnderecoEmail> emailSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoEndereco")
	private Set<Endereco> enderecoSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoConta")
	private Set<Conta> contaSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoPagamento")
	private Set<Pagamento> pagamentoSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoRecebimento")
	private Set<Recebimento> recebimentoSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoCliente")
	private Set<Cliente> clienteSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoFornecedor")
	private Set<Fornecedor> fornecedorSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoFuncionario")
	private Set<Funcionario> funcionarioSalao = new HashSet<>(0);
		
	@OneToMany(mappedBy = "salaoProduto")
	private Set<Produto> produtoSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoProdutoCompra")
	private Set<ProdutoCompra> produtoCompraSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoProdutoVenda")
	private Set<ProdutoVenda> produtoVendaSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoMaterial")
	private Set<Material> materialSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoPacoteServico")
	private Set<PacoteServico> pacoteServicoSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoServico")
	private Set<Servico> servicoSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoTelefone")
	private Set<Telefone> telefoneSalao = new HashSet<>(0);
	
	@OneToMany(mappedBy = "salaoAgendamento")
	private Set<Agendamento> agendamentoSalao = new HashSet<>(0);
	
}