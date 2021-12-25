package com.test.project.service;

import com.test.project.domain.Employee;
import com.test.project.dto.EmployeeDTO;
import com.test.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
            dto.setSalary(formatCurrency(emRepo.findAll().get(e).getSalary()));
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

    public Employee save(Employee employee){
        emRepo.save(employee);
        return employee;
    }

    public Employee get(long id){
        return emRepo.findById(id).get();
    }

    private String calculateManager(int e){
        double salary = Double.valueOf(emRepo.findAll().get(e).getSalary());
        double pManager = 10.0/100.0;
        double totalsalary = salary + (salary * pManager);

        return formatCurrency(totalsalary);
    }

    private String calculateSupervisor(int e){
        double salary = Double.valueOf(emRepo.findAll().get(e).getSalary());
        double pManager = 6.0/100.0;
        double totalsalary = salary + (salary * pManager);

        return formatCurrency(totalsalary);
    }

    private String calculateStaff(int e){
        double salary = Double.valueOf(emRepo.findAll().get(e).getSalary());
        double pManager = 3.0/100.0;
        double totalsalary = salary + (salary * pManager);

        return formatCurrency(totalsalary);
    }

    private String formatCurrency(double currency){
        DecimalFormat kurs = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols format = new DecimalFormatSymbols();

        format.setCurrencySymbol("");
        format.setMonetaryDecimalSeparator(',');
        format.setGroupingSeparator('.');

        kurs.setDecimalFormatSymbols(format);

        return kurs.format(currency).replaceAll(",00","");
    }

}
