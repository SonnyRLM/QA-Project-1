package com.qa.quizProject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.quizProject.persitance.domain.QuizDescription;
import com.qa.quizProject.persitance.repository.QuizRepository;
import com.qa.quizProject.rest.dto.DescriptionDTO;
import com.qa.quizProject.utils.ProjectUtils;

@Service
public class DescriptionService {
	
	private QuizRepository repo;
	private ModelMapper mapper;

	
	@Autowired
	public DescriptionService(QuizRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private DescriptionDTO mapToDTO(QuizDescription desc) {
		return this.mapper.map(desc, DescriptionDTO.class);
	}
	
	
	// create
	public DescriptionDTO create(QuizDescription desc) {
		return this.mapToDTO(this.repo.save(desc));
	}
	
	// read all
	public List<DescriptionDTO> getAllDesc(){
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// read by ID
	public DescriptionDTO getDescById(Long id) {
		//return this.repo.getOne(id);
		return this.mapToDTO(this.repo.findById(id).orElseThrow());
	}
	
	
	public DescriptionDTO updateQuizDesc(Long id, DescriptionDTO desc) {
		QuizDescription toUpdate = this.repo.findById(id).orElseThrow();
		toUpdate.setQuizDescription(desc.getQuizDescription());
		toUpdate.setQuizName(desc.getQuizName());
		ProjectUtils.mergeNotNull(desc, toUpdate);
		return this.mapToDTO(toUpdate);
		
		
//		QuizDescription descChange = this.repo.getOne(id);
//		descChange.setQuizDescription(desc.getQuizDescription());
//		descChange.setQuizName(desc.getQuizName());
//		return this.repo.save(descChange);
	}
	
	public boolean removeQuizDesc(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
	
	public DescriptionDTO findByName(String quizName) {
		return this.mapToDTO(this.repo.findQuizDescriptionByQuizName(quizName));
	}
	
}