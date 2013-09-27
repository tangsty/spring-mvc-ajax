package com.codetutr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codetutr.domain.Person;
import com.codetutr.service.PersonService;

@Controller
@RequestMapping("api")
public class PersonController {
	
	PersonService personService;
	
	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@RequestMapping("list")
	public String userList(Model model) {
		List<Person> people = personService.getPeople();
		model.addAttribute("people", people);
		return "users";
	}

	@RequestMapping("person/random")
	@ResponseBody
	public Person randomPerson() {
		return personService.getRandom();
	}
	
	@RequestMapping("person/{id}")
	@ResponseBody
	public Person getById(@PathVariable Long id) {
		return personService.getById(id);
	}

	@RequestMapping("person/remove/{id}")
	@ResponseBody
	public String removeById(@PathVariable Long id) {
		Person removed = personService.removeById(id);
		return "Removed person: " + removed.toString();
	}

	/* same as above method, but is mapped to
	 * /api/person?id= rather than /api/person/{id}
	 */
	@RequestMapping(value="person", params="id")
	@ResponseBody
	public Person getByIdFromParam(@RequestParam("id") Long id) {
		return personService.getById(id);
	}

	/**
	 * Saves new person. Spring automatically binds the name
	 * and age parameters in the request to the person argument
	 * @param value value submitted from browser
	 * @return String indicating success or failure of save
	 */
	@RequestMapping(value="person", params="value", method=RequestMethod.POST)
	@ResponseBody
	public String savePerson(@RequestParam("value") String value) {
		return "Updated: " + value;
	}

	/**
	 * Updated person. Spring automatically binds the name
	 * and age parameters in the request to the person argument
	 * @param id person Id
	 * @param column updated column index
	 * @param newValue new column value
	 * @return String indicating success or failure of save
	 */
	@RequestMapping(value="person/update", params={"row_id", "column", "value"})
	@ResponseBody
	public String updatePerson(@RequestParam("row_id") Long id, @RequestParam("column") Integer column, @RequestParam("value") String newValue) {
		Person person = personService.getById(id);
		String colName = getColumn(column);
		if (colName.startsWith("FIX:"))
			return "无法修改为 \"" + newValue + "\"";
		else {
			if (column == 2)
				person.setName(newValue);
			else
				person.setAge(Integer.parseInt(newValue));
		}
		return newValue;
	}

	public static String getColumn(int col) {
		switch (col) {
			case 0:
				return "FIX:order";
			case 1:
				return "FIX:id";
			case 2:
				return "name";
			case 3:
				return "age";
			default:
				return "FIX:unknown";
		}
	}
}
