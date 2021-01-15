package br.com.ricardoianni.inovacaoapp.infrasctructure.web.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import br.com.ricardoianni.inovacaoapp.domain.email.TipoEmail;
import br.com.ricardoianni.inovacaoapp.domain.email.TipoEmailRepository;
import br.com.ricardoianni.inovacaoapp.domain.endereco.TipoEndereco;
import br.com.ricardoianni.inovacaoapp.domain.endereco.TipoEnderecoRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Conta;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.ContaRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.TipoConta;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.TipoContaRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.TipoRecorrencia;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.TipoRecorrenciaRepository;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Cliente;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.ClienteRepository;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Fornecedor;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.FornecedorRepository;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.Funcionario;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.FuncionarioRepository;
import br.com.ricardoianni.inovacaoapp.domain.produto.Produto;
import br.com.ricardoianni.inovacaoapp.domain.produto.ProdutoRepository;
import br.com.ricardoianni.inovacaoapp.domain.produto.UnidadeMedida;
import br.com.ricardoianni.inovacaoapp.domain.produto.UnidadeMedidaRepository;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.domain.servico.Material;
import br.com.ricardoianni.inovacaoapp.domain.servico.MaterialRepository;
import br.com.ricardoianni.inovacaoapp.domain.servico.PacoteServico;
import br.com.ricardoianni.inovacaoapp.domain.servico.PacoteServicoRepository;
import br.com.ricardoianni.inovacaoapp.domain.servico.Servico;
import br.com.ricardoianni.inovacaoapp.domain.servico.ServicoRepository;
import br.com.ricardoianni.inovacaoapp.domain.telefone.TipoTelefone;
import br.com.ricardoianni.inovacaoapp.domain.telefone.TipoTelefoneRepository;

public class ControllerHelper {
	
	public static void setEditMode(Model model, boolean isEdit) {
		model.addAttribute("editMode", isEdit);
	}
	
	public static void setAddMode(Model model, boolean isAdd) {
		model.addAttribute("addMode", isAdd);
	}
	
	public static void setSaveOK(Model model, boolean isSaved) {
		model.addAttribute("saveOK", isSaved);
	}
	
	public static void setHasMovement(Model model, boolean hasMovement) {
		model.addAttribute("hasMovement", hasMovement);
	}

	public static void addTipoEmailToRequest(TipoEmailRepository repository, Model model) {
		
		List<TipoEmail> tiposEmail = repository.findAll();
		model.addAttribute("tiposEmail", tiposEmail);
	}

	public static void addTipoTelefoneToRequest(TipoTelefoneRepository repository, Model model) {
		
		List<TipoTelefone> tiposTelefone = repository.findAll();
		model.addAttribute("tiposTelefone", tiposTelefone);
	}
	
	public static void addTipoEnderecoToRequest(TipoEnderecoRepository repository, Model model) {
		
		List<TipoEndereco> tiposEndereco = repository.findAll();
		model.addAttribute("tiposEndereco", tiposEndereco);
	}
	
	public static void addTipoContaToRequest(TipoContaRepository repository, Model model) {
		
		List<TipoConta> tiposConta = repository.findAll();
		model.addAttribute("tiposConta", tiposConta);
	}
	
	public static void addTipoRecorrenciaToRequest(TipoRecorrenciaRepository repository, Model model) {
		
		List<TipoRecorrencia> tiposRecorrencia = repository.findAll();
		model.addAttribute("tiposRecorrencia", tiposRecorrencia);
	}
	
	public static void addUnidadeMedidaToRequest(UnidadeMedidaRepository repository, Model model) {
		
		List<UnidadeMedida> unidadesMedida = repository.findAll();
		model.addAttribute("unidadesMedida", unidadesMedida);
	}
	
	public static void addProdutoToRequest(ProdutoRepository repository, Model model, Salao salao) {
		
		List<Produto> produtos = repository.findBySalaoProdutoOrderByDescricao(salao);
		model.addAttribute("produtos", produtos);
		
	}
	
	public static void addMaterialToRequest(MaterialRepository repository, Model model, Salao salao, Servico servico) {
		
		List<Material> materiais = repository.findBySalaoMaterialAndServicoMaterialOrderByIdMaterial(salao, servico);
		model.addAttribute("materiais", materiais);
		
	}
	
	public static void addServicoToRequest(ServicoRepository repository, Model model, Salao salao) {
		
		List<Servico> servicos = repository.findBySalaoServicoOrderByDescricao(salao);
		model.addAttribute("servicos", servicos);
		
	}
	
	public static void addPacoteServicoToRequest(PacoteServicoRepository repository, Model model, Salao salao) {
		
		List<PacoteServico> pctServicos = repository.findFirstBySalaoPacoteServicoOrderByIdPacoteDesc(salao);
		model.addAttribute("pctservicos", pctServicos);
		
	}
	
	public static void addClienteToRequest(ClienteRepository repository, Model model, Salao salao) {
		
		List<Cliente> clientes = repository.findBySalaoClienteOrderByNome(salao);
		model.addAttribute("clientes", clientes);
		
	}
	
	public static void addFuncionarioToRequest(FuncionarioRepository repository, Model model, Salao salao) {
		
		List<Funcionario> funcionarios = repository.findBySalaoFuncionarioOrderByNome(salao);
		model.addAttribute("funcionarios", funcionarios);
		
	}
	
	public static void addFornecedorToRequest(FornecedorRepository repository, Model model, Salao salao) {
		
		List<Fornecedor> fornecedores = repository.findBySalaoFornecedorOrderByNome(salao);
		model.addAttribute("fornecedores", fornecedores);
		
	}
	
	public static void addContaToRequest(ContaRepository repository, Model model, Salao salao) {
		
		List<Conta> contas = repository.findBySalaoContaOrderByDescricao(salao);
		model.addAttribute("contas", contas);
		
	}

	public static Optional<String> getPreviousPageByRequest(HttpServletRequest request)
	{
	   return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
	}
	
	public static void addMesesToRequest(Model model) {
		Object[][] meses = new Object[12][3];
		String mes = new String();
		
		
		for (int i = 0; i < 12; i++) {
			mes = Month.of(i+1).getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
			mes = mes.substring(0, 1).toUpperCase() + mes.substring(1);
			
			meses[i][0] = i + 1;
			meses[i][1] = mes;
			meses[i][2] = LocalDate.now().getMonth().equals(Month.of(i+1));
		}
		
		model.addAttribute("meses", meses);
	}
	
	public static void addAnosToRequest(Model model, int periodo) {
		
		Object[][] anos = new Object[periodo+1][2];
		
		for (int i = 0; i <= periodo; i++) {
			anos[i][0] = LocalDate.now().getYear() + 1 - i;
			anos[i][1] = anos[i][0].equals(LocalDate.now().getYear());
		}
		
		model.addAttribute("anos", anos);
	}
	
}
