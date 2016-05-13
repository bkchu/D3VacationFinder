package Frontend.controllers;

import devacation.Algorithm;
import devacation.Client;
import devacation.Result;
import devacation.UserCriteria;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("query")
public class MainController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model) {
		if (!model.containsAttribute("query")) {
			model.addAttribute("query", new PlacesQuery());
			// Category enums for the checkboxes
			model.addAttribute("section_poi", PlacesQuery.sectionPOI());
			model.addAttribute("section_fun", PlacesQuery.sectionFun());
			model.addAttribute("section_social", PlacesQuery.sectionSocial());
			model.addAttribute("section_entertainment", PlacesQuery.sectionEntertainment());
			model.addAttribute("section_misc", PlacesQuery.sectionMisc());
		}

		return "index";
	}

	@RequestMapping("/map")
	public String map() {
		return "map";
	}

	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String querySubmit(@ModelAttribute("query") PlacesQuery query, Model model, SessionStatus status) {
		return "redirect:/";
	}

	@RequestMapping(value="/submit", method= RequestMethod.POST)
	public String querySubmit3(@ModelAttribute("query") PlacesQuery query, Model model, SessionStatus status) {
		status.setComplete();

		// Filter and rank the results
		UserCriteria criteria = new UserCriteria(query);
		Result[] places = Client.search("", criteria);
		places = Algorithm.filter(places, criteria);
		places = Algorithm.rank(places, criteria);

		model.addAttribute("places", places);
		for (int i = 0; i < places.length; i++){
			System.out.println(places[i].toString());
		}

		return "result";
	}
}
