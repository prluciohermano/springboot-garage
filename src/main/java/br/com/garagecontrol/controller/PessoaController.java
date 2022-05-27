package br.com.garagecontrol.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.garagecontrol.entity.Pessoa;
import br.com.garagecontrol.entity.Telefone;
import br.com.garagecontrol.repository.PessoaRepository;
import br.com.garagecontrol.repository.TelefoneRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;

	/* ***Direciona para a página Index*** */
	@GetMapping(path = "/meuprojeto")
	public String index() {
		return "index";
	}
	
	/* ***Direciona para de cadastro*** */
	@GetMapping("/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}
	
	@GetMapping("/arearestrita")
	public ModelAndView arearestrita() {
		ModelAndView modelAndView = new ModelAndView("cadastro/arearestrita");
		return modelAndView;
	}
	
	/* ***Captura os dados na tela e Salva uma pessoa no banco*** */
	@PostMapping("/salvarpessoa")
	public ModelAndView salvarPessoa(@Valid Pessoa pessoa, BindingResult bindingResult) {
		
		pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getId()));
		
		if (bindingResult.hasErrors()) { // Validação de Erros
			ModelAndView modelAndView = new ModelAndView("/cadastro/listapessoas");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			modelAndView.addObject("pessoas", pessoasIt);
			modelAndView.addObject("pessoaobj", pessoa);
			System.out.println(pessoa);
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); //Erros vem das anotações na Entity
				System.out.println(msg);
			}
			
			modelAndView.addObject("msg", msg);
			System.out.println("pessoa-> " + pessoa);
			System.out.println("mensagem-> " + msg);
			return modelAndView;
		}
		
		pessoaRepository.save(pessoa);

		ModelAndView andView = new ModelAndView("cadastro/listapessoas");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj", new Pessoa());
			
		return andView;
	}
	
	@PostMapping("**/editarpessoa/salvarpessoa")
	public ModelAndView atualizaPessoa(Pessoa pessoa) {
		pessoaRepository.save(pessoa);

		ModelAndView andView = new ModelAndView("cadastro/listapessoas");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj", new Pessoa());
			
		return andView;
	}
		
	/* ***Mostra os dados na tela de uma pessoa vindo do banco*** */
	@RequestMapping(method=RequestMethod.GET, value="/listapessoas")
	public ModelAndView pessoas() {
		ModelAndView andView = new ModelAndView("cadastro/listapessoas");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj", new Pessoa());
		return andView;	
	}
	
	/* ***Edita os dados na tela de uma pessoa e salva no banco*** */
	@GetMapping(value="/editarpessoa/{idpessoa}")
	public ModelAndView editarPessoa(@PathVariable("idpessoa") Long idpessoa) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", pessoa.get());
		return modelAndView;	
	}
	
	/* *** Exclui os dados na tela de uma pessoa e salva no banco*** */
	@GetMapping(value="/removerpessoa/{idpessoa}")
	public ModelAndView excluirPessoa(@PathVariable("idpessoa") Long idpessoa) {
		
		pessoaRepository.deleteById(idpessoa);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/listapessoas");
		modelAndView.addObject("pessoas", pessoaRepository.findAll());
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;	
	}
	
	/* *** Pesquisa o cliente por parte do nome *** */
	@SuppressWarnings("static-access")
	@PostMapping("/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa,
			@RequestParam("pesqsexo") String pesqsexo) {
		
		List<Pessoa> pessoas = new ArrayList<>();
		
		if (pesqsexo != null && !pesqsexo.isEmpty()) {
			pessoas = pessoaRepository.findPessoaByNameSexo(nomepesquisa, pesqsexo);
		} else {
			pessoas = pessoaRepository.findPessoaByName(nomepesquisa);
		}
		
		ModelAndView modelAndView = new ModelAndView("cadastro/listapessoas");
		modelAndView.addObject("pessoas", pessoas);
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
		
			//List<String> msg = new ArrayList<>();
			//msg.add("O nome informado não existe");
			//andView.addObject("msg", msg);
			//return andView;
	}
	
	/* *** Puxa o cliente e leva para a tela de telefones *** */
	@GetMapping(value="/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));
		return modelAndView;	
	}
	
	/* *** Adiciona os telefones no cliente, testa e refaz a tela com os dados *** */
	@PostMapping("/addfonepessoa/{pessoaid}")
	public ModelAndView addFonePessoa(Telefone telefone,
							@PathVariable("pessoaid") Long pessoaid) {
		
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		
		if (telefone != null && telefone.getNumero().isEmpty() || telefone.getTipo().isEmpty()) {
			
			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("pessoaobj", pessoa);
			modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
			List<String> msg = new ArrayList<>();
			
			if(telefone.getNumero().isEmpty() ) {
				msg.add("O Número do telefone dever ser informado");
			} if (telefone.getTipo().isEmpty()) {
				msg.add("O Tipo do telefone dever ser informado");
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
			
		telefone.setPessoa(pessoa);
		
		telefoneRepository.save(telefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
		return modelAndView;
	}
	
	/* *** Exclui os dados na tela de uma pessoa e salva no banco*** */
	@GetMapping(value="/removertelefone/{idtelefone}")
	public ModelAndView excluirTelefone(@PathVariable("idtelefone") Long idtelefone) {
		
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
		
		telefoneRepository.deleteById(idtelefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
		
		return modelAndView;	
	}
}