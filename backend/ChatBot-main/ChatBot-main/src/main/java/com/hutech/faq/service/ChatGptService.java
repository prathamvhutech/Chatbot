package com.hutech.faq.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hutech.faq.entity.Questions;
import com.hutech.faq.entity.dto.AnswerDto;
import com.hutech.faq.exceptionhandler.ResourceNotFoundException;
import com.hutech.faq.service.repository.ChatRepository;

@Service
public class ChatGptService {

	@Autowired
	private ChatRepository chatRepository;

	public AnswerDto atGPT(Questions question) throws Exception {
		AnswerDto answerDto = new AnswerDto();

		Questions questions = chatRepository.findByQuestion(question.getQuestion());
		if (questions != null) {
			answerDto.setAnswer(questions.getAnswer());
			return answerDto;
		} else {
			Questions abc = new Questions();
			abc.setQuestion(question.getQuestion());
			question.setQuestion(question.getQuestion());

			String url = "https://api.openai.com/v1/completions";
			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Authorization", "Bearer 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");

			JSONObject data = new JSONObject();
			data.put("model", "text-davinci-003");
			data.put("prompt", question.getQuestion());
			data.put("max_tokens", 4000);
			data.put("temperature", 1.0);

			con.setDoOutput(true);
			con.getOutputStream().write(data.toString().getBytes());

			String output =null;
			output=new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
					.reduce((a, b) -> a + b).orElseThrow();
			String answer = new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text");
			// answer = answer.replaceAll("(\\r\\n|\\n|\\r|\\d+\\.\\s)", "");

			List<Questions> list = chatRepository.findAll();
			  if(!list.isEmpty())
			  {
				  if(list.size()==9) {
				  Questions ques=list.get(list.size()-9);
				  chatRepository.delete(ques);
				  }
			  }

			abc.setAnswer(answer);
			chatRepository.save(abc);
			answerDto.setAnswer(answer);
			return answerDto;
		}

	}

	public List<Questions> getAll() {
		return chatRepository.findAll();
	}

	public Questions getById(Integer id)throws ResourceNotFoundException {
		
		return chatRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(" data not exit's in the Db", 404));
	}

}