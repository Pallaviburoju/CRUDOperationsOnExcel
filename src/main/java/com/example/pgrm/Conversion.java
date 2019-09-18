package com.example.pgrm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codoid.products.exception.FilloException;
import com.example.service.ConvertService;

@RestController
public class Conversion {
	@Autowired
	private ConvertService convertService;
	
	@GetMapping("/")
	public String test() {
		return "Hello";
	}
	
	@GetMapping("/get")
	public List<Map<String, String>> retrieve() throws Exception{
		return convertService.retrieve();
	}
	
	@PutMapping("/add/{name}/{dept}/{cmpny}")
	public void add(@PathVariable String name, @PathVariable String dept, @PathVariable String cmpny) throws Exception {
		convertService.add(name, dept, cmpny);
	}
	
	@PostMapping("/update/{empName}/{dept}/{cmpny}")
	public int updateData(@PathVariable String empName, @PathVariable String dept, @PathVariable String cmpny) throws FilloException {
		return convertService.updateData(empName, dept, cmpny);
	}
	
	@DeleteMapping("/delete/{cellValue}")
	public void delete(@PathVariable String cellValue) throws Exception {
		convertService.delete(cellValue);
	}
}
