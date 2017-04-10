package cn.edu.ncut.istc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.ncut.istc.model.UserObj;
import cn.edu.ncut.istc.service.TestService;

@RestController
@RequestMapping("/test")
public class TestRestController {
	@Autowired
	private TestService testService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<UserObj>> getUsersGet() {
		List<UserObj> users = testService.getUserList();
		return new ResponseEntity<List<UserObj>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/userpost", method = RequestMethod.POST)
	public ResponseEntity<List<UserObj>> getUsersPost() {
		List<UserObj> users = testService.getUserList();
		return new ResponseEntity<List<UserObj>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserObj> getUser(@PathVariable("id") long id) {
		System.out.println("Fetching User with id " + id);
		UserObj user = testService.getUserById(id);
		if (user == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<UserObj>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserObj>(user, HttpStatus.OK);
	}

}
