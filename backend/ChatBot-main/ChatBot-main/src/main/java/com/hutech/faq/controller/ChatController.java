
package com.hutech.faq.controller;

import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hutech.faq.entity.Questions;
import com.hutech.faq.entity.dto.AnswerDto;
import com.hutech.faq.exceptionhandler.ResourceNotFoundException;
import com.hutech.faq.service.ChatGptService;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1")
public class ChatController {

	@Autowired
	private ChatGptService chatGptService;
	
	@PostMapping("/chatHpt")
	public ResponseEntity<AnswerDto> answer(@RequestBody Questions question) throws Exception{
		return new ResponseEntity<AnswerDto>(chatGptService.atGPT(question),HttpStatus.OK);
	}
	
	@GetMapping("chat/getAll")
	public ResponseEntity<List<Questions>> getAll(){
		return new ResponseEntity<List<Questions>>(chatGptService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("chat/get/{id}")
	public ResponseEntity<Questions>getByid(@PathVariable Integer id)throws ResourceNotFoundException{
		return new ResponseEntity<Questions>(chatGptService.getById(id),HttpStatus.OK);
	}
	
	@GetMapping("/hai")
	public String hai() {
		return "hai";
	}
	
}