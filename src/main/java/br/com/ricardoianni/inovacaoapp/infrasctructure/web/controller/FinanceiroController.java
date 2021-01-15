package br.com.ricardoianni.inovacaoapp.infrasctructure.web.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ricardoianni.inovacaoapp.application.service.FinanceiroService;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Conta;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.ContaRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.FluxoCaixa;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.FluxoCaixaFilter;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Pagamento;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.PagamentoRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Recebimento;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.RecebimentoRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.Saldo;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.SaldoRepository;
import br.com.ricardoianni.inovacaoapp.domain.financeiro.TipoRecorrenciaRepository;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.ClienteRepository;
import br.com.ricardoianni.inovacaoapp.domain.pessoa.FornecedorRepository;
import br.com.ricardoianni.inovacaoapp.domain.salao.Salao;
import br.com.ricardoianni.inovacaoapp.utils.NavigatorUtils;
import br.com.ricardoianni.inovacaoapp.utils.SecurityUtils;

@Controller
@RequestMapping(path = "/admin/financeiro")
public class FinanceiroController {
	
	
	  @Autowired 
	  private FinanceiroService financeiroService;
	  
	  @Autowired 
	  private ClienteRepository clienteRepository;
	  
	  @Autowired 
	  private FornecedorRepository fornecedorRepository;
	  
	  @Autowired
	  private ContaRepository contaRepository;
	  
	  @Autowired
	  private PagamentoRepository pagamentoRepository;
	  
	  @Autowired
	  private RecebimentoRepository recebimentoRepository;
	  
	  @Autowired
	  private SaldoRepository saldoRepository;
	  
	  @Autowired
	  private TipoRecorrenciaRepository tipoRecorrenciaRepository;
	  
	  @GetMapping("/pagamento") 
	  public String pagamento(@ModelAttribute("fluxoCaixaFilter") FluxoCaixaFilter filter,
			  				  Model model) { 
		  Salao salao = SecurityUtils.loggedSalao();
		  
		  List<Pagamento> pagamentos;

		  Integer numMes;
		  Integer numAno;
		  LocalDate dataInicial;
		  LocalDate dataFinal;
		  Integer contaId;
		  
		  try {
			  contaId = filter.getIdConta();
			  numMes = filter.getNumeroMes();
			  numAno = filter.getAno();
			  
			  dataInicial = LocalDate.of(filter.getAno(), filter.getNumeroMes(), 1);
			  dataFinal = LocalDate.of(filter.getAno(), filter.getNumeroMes(), Month.of(filter.getNumeroMes()).length(Year.isLeap(filter.getAno())));  	
		  } catch (NullPointerException e) {
			  contaId = 0;
			  numMes = LocalDate.now().getMonthValue();
			  numAno = LocalDate.now().getYear();
			  
			  filter = new FluxoCaixaFilter();
			  
			  filter.setNumeroMes(numMes);
			  filter.setAno(numAno);
			  
			  dataInicial = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
			  dataFinal = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), Month.of(LocalDate.now().getMonthValue()).length(Year.isLeap(LocalDate.now().getYear())));
		  }
		  
		  if (contaId == 0) {
			  pagamentos = pagamentoRepository.findBySalaoPagamentoAndVencimentoBetweenOrderByVencimento(salao, dataInicial, dataFinal);
		  } else {
			  Conta conta = contaRepository.findBySalaoContaAndIdConta(salao, contaId);
			  
			  pagamentos = pagamentoRepository.findBySalaoPagamentoAndContaPagamentoAndVencimentoBetweenOrderByVencimento(salao, conta, dataInicial, dataFinal);
		  }
		  
		  model.addAttribute("pagamentos", pagamentos);
		  model.addAttribute("filter", filter);

		  ControllerHelper.addContaToRequest(contaRepository, model, salao);
		  ControllerHelper.addMesesToRequest(model);
		  ControllerHelper.addAnosToRequest(model, 5);
		  
		  return "pagamento"; 
	  }
	  
	  @GetMapping("/pagamento/new") 
	  public String newPagamento(Model model,
				 				 HttpServletRequest request) { 
		  Salao salao = SecurityUtils.loggedSalao();
		  Pagamento pagamento = new Pagamento();
		  
		  pagamento.setVencimento(LocalDate.now());
		  
		  ControllerHelper.setEditMode(model, false);
		  
		  model.addAttribute("pagamento", pagamento);
		  
		  ControllerHelper.addFornecedorToRequest(fornecedorRepository, model, salao);
		  ControllerHelper.addContaToRequest(contaRepository, model, salao);
		  ControllerHelper.addTipoRecorrenciaToRequest(tipoRecorrenciaRepository, model);
		  
		  NavigatorUtils.setUrlToBack(request, "incpagamento");
		  
		  return "incpagamento"; 
	  }
	  
	  @PostMapping("/pagamento/save") 
	  public String savePagamento(@ModelAttribute("pagamento") Pagamento pagamento, 
			  					  Model model) { 
		  Salao salao = SecurityUtils.loggedSalao();
	  
		  String url = NavigatorUtils.getUrlToBack();
		  
		  pagamento.setSalaoPagamento(salao);
		  
		  if (pagamento.getValor() == null) {
			  pagamento.setValor(new BigDecimal(0));
		  }
		  
		  financeiroService.savePagamento(pagamento);
			
		  if (url.contains("home")) {
			  return pagamento(new FluxoCaixaFilter(), model);
		  } else if (url.contains("fornecedor") || url.contains("conta")) {
			  return fluxoCaixa(new FluxoCaixaFilter(), model);
		  } else {
			  return url;
		  }
	  }
	  
	  @PostMapping("/pagamento/edit") 
	  public String editPagamento(@RequestParam("idPgtoEdit") Integer idPagamento,
			  					  Model model,
			  					  HttpServletRequest request) { 
		  Salao salao = SecurityUtils.loggedSalao();
		  
		  ControllerHelper.setEditMode(model, true);
		  
		  Pagamento pagamento = pagamentoRepository.findBySalaoPagamentoAndIdPagamento(salao, idPagamento);
		  
		  model.addAttribute("pagamento", pagamento);
		  
		  ControllerHelper.addFornecedorToRequest(fornecedorRepository, model, salao);
		  ControllerHelper.addContaToRequest(contaRepository, model, salao);
		  ControllerHelper.addTipoRecorrenciaToRequest(tipoRecorrenciaRepository, model);
		  
		  NavigatorUtils.setUrlToBack(request, "incpagamento");
		  
		  return "incpagamento"; 
	  }
		
	  @PostMapping(path = "/pagamento/delete")
	  public String deletePagamento(@RequestParam("idPgtoDelete") Integer idPagamento,
								   	Model model,
								   	HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		NavigatorUtils.setUrlToBack(request, "incpagamento");
		String url = NavigatorUtils.getUrlToBack();
		
		Pagamento pagamento = pagamentoRepository.findBySalaoPagamentoAndIdPagamento(salao, idPagamento);
		
		financeiroService.deletePagamento(pagamento);
		
		return url;
		
	  }
	  
	  @GetMapping("/recebimento") 
	  public String recebimento(@ModelAttribute("fluxoCaixaFilter") FluxoCaixaFilter filter,
				  				Model model) { 
		  Salao salao = SecurityUtils.loggedSalao();
		  
		  List<Recebimento> recebimentos;

		  Integer numMes;
		  Integer numAno;
		  LocalDate dataInicial;
		  LocalDate dataFinal;
		  Integer contaId;
		  
		  try {
			  contaId = filter.getIdConta();
			  numMes = filter.getNumeroMes();
			  numAno = filter.getAno();
			  
			  dataInicial = LocalDate.of(filter.getAno(), filter.getNumeroMes(), 1);
			  dataFinal = LocalDate.of(filter.getAno(), filter.getNumeroMes(), Month.of(filter.getNumeroMes()).length(Year.isLeap(filter.getAno())));  	
		  } catch (NullPointerException e) {
			  contaId = 0;
			  numMes = LocalDate.now().getMonthValue();
			  numAno = LocalDate.now().getYear();
			  
			  filter = new FluxoCaixaFilter();
			  
			  filter.setNumeroMes(numMes);
			  filter.setAno(numAno);
			  
			  dataInicial = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
			  dataFinal = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), Month.of(LocalDate.now().getMonthValue()).length(Year.isLeap(LocalDate.now().getYear())));
		  }
		  
		  if (contaId == 0) {
			  recebimentos = recebimentoRepository.findBySalaoAndMes(salao, dataInicial, dataFinal);
		  } else {
			  Conta conta = contaRepository.findBySalaoContaAndIdConta(salao, contaId);
			  
			  recebimentos = recebimentoRepository.findBySalaoAndContaAndMes(salao, conta, dataInicial, dataFinal);
		  }
		  
		  model.addAttribute("recebimentos", recebimentos);
		  model.addAttribute("filter", filter);

		  ControllerHelper.addContaToRequest(contaRepository, model, salao);
		  ControllerHelper.addMesesToRequest(model);
		  ControllerHelper.addAnosToRequest(model, 5);
		  
		  return "recebimento"; 
	  }
	  
	  @GetMapping("/recebimento/new") 
	  public String newRecebimento(Model model,
				 				   HttpServletRequest request) { 
		  Salao salao = SecurityUtils.loggedSalao();
		  Recebimento recebimento = new Recebimento();
		  
		  recebimento.setVencimento(LocalDate.now());
		  
		  ControllerHelper.setEditMode(model, false);
		  
		  model.addAttribute("recebimento", recebimento);
		  
		  ControllerHelper.addClienteToRequest(clienteRepository, model, salao);
		  ControllerHelper.addContaToRequest(contaRepository, model, salao);
		  ControllerHelper.addTipoRecorrenciaToRequest(tipoRecorrenciaRepository, model);
		  
		  NavigatorUtils.setUrlToBack(request, "increcebimento");
		  
		  return "increcebimento"; 
	  }
	  
	  @PostMapping("/recebimento/save") 
	  public String saveRecebimento(@ModelAttribute("recebimento") Recebimento recebimento, 
			  					    Model model) { 
		  Salao salao = SecurityUtils.loggedSalao();
	  
		  String url = NavigatorUtils.getUrlToBack();
		  
		  recebimento.setSalaoRecebimento(salao);
		  
		  if (recebimento.getValor() == null) {
			  recebimento.setValor(new BigDecimal(0));
		  }
		  
		  financeiroService.saveRecebimento(recebimento);
			
		  if (url.contains("home")) {
			  return recebimento(null, model);
		  } else if (url.contains("cliente") || url.contains("conta")) {
			  return fluxoCaixa(new FluxoCaixaFilter(), model);
		  } else {
			  return url;
		  }
	  }
	  
	  @PostMapping("/recebimento/edit") 
	  public String editRecebimento(@RequestParam("idRecebEdit") Integer idRecebimento,
			  					    Model model,
			  					    HttpServletRequest request) { 
		  Salao salao = SecurityUtils.loggedSalao();
		  
		  ControllerHelper.setEditMode(model, true);
		  
		  Recebimento recebimento = recebimentoRepository.findBySalaoRecebimentoAndIdRecebimento(salao, idRecebimento);
		  
		  model.addAttribute("recebimento", recebimento);
		  
		  ControllerHelper.addClienteToRequest(clienteRepository, model, salao);
		  ControllerHelper.addContaToRequest(contaRepository, model, salao);
		  ControllerHelper.addTipoRecorrenciaToRequest(tipoRecorrenciaRepository, model);
		  
		  NavigatorUtils.setUrlToBack(request, "increcebimento");
		  
		  return "increcebimento"; 
	  }
		
	  @PostMapping(path = "/recebimento/delete")
	  public String deleteRecebimento(@RequestParam("idRecebDelete") Integer idRecebimento,
									  Model model,
									  HttpServletRequest request) {
		Salao salao = SecurityUtils.loggedSalao();
		
		NavigatorUtils.setUrlToBack(request, "increcebimento");
		String url = NavigatorUtils.getUrlToBack();
		
		Recebimento recebimento = recebimentoRepository.findBySalaoRecebimentoAndIdRecebimento(salao, idRecebimento);
		
		financeiroService.deleteRecebimento(recebimento);
		
		return url;
		
	  }
	  
	  @GetMapping(path = "/fluxocaixa")
	  public String fluxoCaixa(
			  @ModelAttribute("fluxoCaixaFilter") FluxoCaixaFilter filter,
			  Model model) {
		  Salao salao = SecurityUtils.loggedSalao();
		  
		  Saldo saldo;
		  List<Pagamento> pagamentos;
		  List<Recebimento> recebimentos;

		  Integer numMes;
		  Integer numAno;
		  LocalDate dataInicial;
		  LocalDate dataFinal;
		  Integer contaId;
		  
		  try {
			  contaId = filter.getIdConta();
			  numMes = filter.getNumeroMes();
			  numAno = filter.getAno();
			  
			  dataInicial = LocalDate.of(filter.getAno(), filter.getNumeroMes(), 1);
			  dataFinal = LocalDate.of(filter.getAno(), filter.getNumeroMes(), Month.of(filter.getNumeroMes()).length(Year.isLeap(filter.getAno())));  	
		  } catch (NullPointerException e) {
			  contaId = 0;
			  numMes = LocalDate.now().getMonthValue();
			  numAno = LocalDate.now().getYear();
			  
			  filter = new FluxoCaixaFilter();
			  
			  filter.setNumeroMes(numMes);
			  filter.setAno(numAno);
			  
			  dataInicial = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
			  dataFinal = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), Month.of(LocalDate.now().getMonthValue()).length(Year.isLeap(LocalDate.now().getYear())));
		  }
		  
		  
		  if (contaId == 0) {
			  BigDecimal valorSaldo = new BigDecimal(0);
			  
			  List<Conta> contas = contaRepository.findBySalaoContaOrderByDescricao(salao);
				
			  if (contas.size() > 0) {
				  for (Conta conta : contas) {
					  List<Saldo> saldosConta = saldoRepository.findBySalaoSaldoAndContaSaldoAndNumMesLessThanEqualAndNumAnoLessThanEqualOrderByNumAnoDescNumMesDesc(salao, conta, numMes, numAno);
						
					  if (saldosConta.size() > 0) {
						  valorSaldo = valorSaldo.add(saldosConta.get(0).getSaldo());
					  }
				  }
			  }
				
			  saldo = new Saldo();
			  saldo.setSaldo(valorSaldo);
			  
			  pagamentos = pagamentoRepository.findBySalaoPagamentoAndVencimentoBetweenOrderByVencimento(salao, dataInicial, dataFinal);
			  recebimentos = recebimentoRepository.findBySalaoAndMes(salao, dataInicial, dataFinal);
		  } else {
			  Conta conta = contaRepository.findBySalaoContaAndIdConta(salao, contaId);
			  
			  List<Saldo> saldos = saldoRepository.findBySalaoSaldoAndContaSaldoAndNumMesLessThanEqualAndNumAnoLessThanEqualOrderByNumAnoDescNumMesDesc(salao, conta, numMes, numAno);
			  
			  if (saldos.size() == 0) {				  
				  saldo = new Saldo();
				  saldo.setSaldo(new BigDecimal(0));
			  } else {
				  saldo = saldos.get(0);
			  }

			  
			  pagamentos = pagamentoRepository.findBySalaoPagamentoAndContaPagamentoAndVencimentoBetweenOrderByVencimento(salao, conta, dataInicial, dataFinal);
			  recebimentos = recebimentoRepository.findBySalaoAndContaAndMes(salao, conta, dataInicial, dataFinal);
		  }
			  
		  model.addAttribute("saldo", saldo);
		  
		  List<FluxoCaixa> fluxoCaixa = new ArrayList<FluxoCaixa>();
		  
		  for (Pagamento pagamento : pagamentos) {
			  if (pagamento.getValor() == null) {
				  pagamento.setValor(new BigDecimal(0.00));
			  }
			  pagamento.setValor(pagamento.getValor().negate());
		  }
		  
		  for (Recebimento recebimento : recebimentos) {
			  if (recebimento.getValor() == null) {
				  recebimento.setValor(new BigDecimal(0.00));
			  }
		  }
		  
		  fluxoCaixa.addAll(recebimentos);
		  fluxoCaixa.addAll(pagamentos);
		  
		  Collections.sort(fluxoCaixa);;
		  
		  model.addAttribute("fluxoCaixa", fluxoCaixa);
		  model.addAttribute("filter", filter);

		  ControllerHelper.addContaToRequest(contaRepository, model, salao);
		  ControllerHelper.addMesesToRequest(model);
		  ControllerHelper.addAnosToRequest(model, 5);
			  
		  return "fluxocaixa";
	  }

}