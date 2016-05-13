package Frontend.controllers;

import Frontend.services.BarService;
import Frontend.services.BarService.BarRequest;
import Frontend.services.BarService.BarResponse;
import Frontend.services.FooService;
import Frontend.services.Greeting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TestController {
	@Autowired
	private FooService fooService;

	private BarService barService = new BarService();

	@RequestMapping("/test")
	public String testRequestParam(@RequestParam(value = "param1", required = false) String value1, @RequestParam(value = "param2") String value2, Model model) {
		model.addAttribute("data1", "We have some data passed to our view!");

		String result = fooService.foo(value1, value2);
		model.addAttribute("serviceResult", result);
		return "test";
	}

	@RequestMapping("/test/{value1}/{value2}")
	public String testPathVariable(@PathVariable String value1, @PathVariable String value2, HttpServletRequest request, HttpServletResponse response, Model model) {
		String result = fooService.foo(value1, value2);
		model.addAttribute("serviceResult", result);
		return "examples/test2";
	}

	@ResponseBody
	@RequestMapping(value = "/testJson", method = RequestMethod.POST)
	public BarResponse testJson(@RequestBody BarRequest request) {
		return barService.bar(request);
	}

	@ResponseBody
	@RequestMapping(value = "/testJson2", method = RequestMethod.POST)
	public BarResponse testJson() {
		return new BarResponse(Lists.newArrayList("one", "two", "three"), Maps.newHashMap());
	}
	
	@RequestMapping(value="/greeting", method=RequestMethod.GET)
	public String greetingForm(Model model) {
		model.addAttribute("greeting", new Greeting());
		return "index";
	}
	
	@RequestMapping(value="/greeting", method = RequestMethod.POST)
	public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
		model.addAttribute("greeting", greeting);
		return "result";
	}
	
}