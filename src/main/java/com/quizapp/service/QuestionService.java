package com.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizapp.question.Question;
import com.quizapp.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	public List<Question> getAllQuestion() {
		return questionRepository.findAll();
	}

	public List<Question> getQuestionByCategory(String category) {
		return questionRepository.findByCategory(category);
	}

	public String addQuestion(Question question) {
		questionRepository.save(question);
		return "Success";
	}

}
