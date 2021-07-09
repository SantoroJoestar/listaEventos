package com.santoro.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.santoro.models.Convidado;
import com.santoro.models.Eventos;
import com.santoro.repository.ConvidadoRepository;
import com.santoro.repository.EventoRepository;

@Controller
public class EventoController{

	@Autowired
	private EventoRepository er;
	
	@Autowired
	private ConvidadoRepository cr;
	
	//Create event
	@RequestMapping(value = "/cadastrarEvento", method=RequestMethod.GET)
	public String formulario() {
		return "evento/formEvento";
	}

	@RequestMapping(value = "/cadastrarEvento", method=RequestMethod.POST)
	public String formulario(@Valid Eventos eventos, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os Campos! ");
			return "redirect:/cadastrarEvento";
		}
		
		er.save(eventos);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso! ");
		return "redirect:/cadastrarEvento";
	}

	//Página principal
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Eventos> eventos = er.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}

	
	//Reda event
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("id") long id) {
		Eventos eventos = er.findById(id);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("eventos", eventos);
		System.out.println("eventos" + eventos);
		
		Iterable<Convidado> convidado = cr.findByEvento(eventos);
		mv.addObject("convidado", convidado);
		
		return mv;
		}
	//Delete event
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long id) {
		Eventos eventos = er.findById(id);
		er.delete(eventos);
		return "redirect:/eventos";
	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		Eventos eventos = convidado.getEvento();
		long idLong = eventos.getId();
		String id = "" + idLong;
		return "redirect:/" + id;	
	}
	
	
	// Formulario edição evento
    @RequestMapping(value = "/editarEvento", method = RequestMethod.GET)
    public ModelAndView updateEvent(long id) {
         ModelAndView mv = new ModelAndView("evento/editarEvento");
         Eventos eventos = er.findById(id); 
        mv.addObject("eventos", eventos);
        return mv;
    }

    // Update event
    @RequestMapping(value = "/editarEvento", method = RequestMethod.POST)
    public String editarEvento(@Valid Eventos eventos, BindingResult result, RedirectAttributes attributes) {
    	er.save(eventos);
		//attributes.addFlashAttribute("mensagem", "Evento alterado com sucesso!");
		return "redirect:/eventos";	
}
        
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("id") long id, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
			if(result.hasErrors()) {
				attributes.addFlashAttribute("mensagem", "Verifique os Campos!");
				return "redirect:/{id}";
			}
			Eventos evento = er.findById(id);
			convidado.setEvento(evento);
			cr.save(convidado);
			attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
			return "redirect:/{id}";
		}
		
	
}