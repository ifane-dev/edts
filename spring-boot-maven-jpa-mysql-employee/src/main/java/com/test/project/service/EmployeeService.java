package com.test.project.service;

import com.test.project.domain.Employee;
import com.test.project.dto.EmployeeDTO;
import com.test.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository emRepo;

    public List<EmployeeDTO> listAll(){

        List<EmployeeDTO> listEm = new ArrayList<>();
        for(int e = 0; e < emRepo.findAll().size(); e++){
            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(emRepo.findAll().get(e).getId());
            dto.setName(emRepo.findAll().get(e).getName());
            dto.setSalary(emRepo.findAll().get(e).getSalary());
            dto.setGrade(emRepo.findAll().get(e).getGrade());

            if(emRepo.findAll().get(e).getGrade().equals("1")){
                dto.setTotalBonus(calculateManager(e));
            } else if(emRepo.findAll().get(e).getGrade().equals("2")){
                dto.setTotalBonus(calculateSupervisor(e));
            } else if(emRepo.findAll().get(e).getGrade().equals("3")){
                dto.setTotalBonus(calculateStaff(e));
            }

            listEm.add(dto);
        }

        return listEm;
    }

    public void save(Employee employee){
        emRepo.save(employee);
    }

    public Employee get(long id){
        return emRepo.findById(id).get();
    }

    private double calculateManager(int e){
        double salary = Double.valueOf(emRepo.findAll().get(e).getSalary());
        double pManager = 10.0/100.0;
        double totalsalary = salary + (salary * pManager);

        return totalsalary;
    }

    private double calculateSupervisor(int e){
        double salary = Double.valueOf(emRepo.findAll().get(e).getSalary());
        double pManager = 6.0/100.0;
        double totalsalary = salary + (salary * pManager);

        return totalsalary;
    }

    private double calculateStaff(int e){
        double salary = Double.valueOf(emRepo.findAll().get(e).getSalary());
        double pManager = 3.0/100.0;
        double totalsalary = salary + (salary * pManager);

        return totalsalary;
    }

}
