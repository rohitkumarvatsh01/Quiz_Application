package com.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quizapp.model.Question;
import com.quizapp.model.QuestionWrapper;
import com.quizapp.model.Quiz;
import com.quizapp.model.Response;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.QuizRepository;

@Service
public class QuizService {
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private QuestionRepository questionRepository;

	public ResponseEntity<String> creatQuiz(String category, int numQ) {
		List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);
		Quiz quiz = new Quiz();
		quiz.setQuestions(questions);
		quizRepository.save(quiz);
		return new ResponseEntity<>("Success", HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
		Optional<Quiz> quiz = quizRepository.findById(id);
		List<Question> questionFromDB = quiz.get().getQuestions();
		List<QuestionWrapper> questionsForUser = new ArrayList<>();
		for (Question q : questionFromDB) {
			QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
			questionsForUser.add(qw);
		}
		return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> response) {
		Quiz quiz = quizRepository.findById(id).get();
		List<Question> questions = quiz.getQuestions();
		int right = 0;
		for (int i = 0; i < response.size(); i++) {
			Response userResponse = response.get(i);
			if (userResponse.getResponse().equals(questions.get(i).getRightAnswer())) {
				right++;
			}
		}
		return new ResponseEntity<>(right, HttpStatus.OK);
	}
}
