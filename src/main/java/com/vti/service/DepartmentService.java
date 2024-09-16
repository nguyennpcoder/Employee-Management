package com.vti.service;

import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.DepartmentCreateForm;
import com.vti.form.DepartmentFilterForm;
import com.vti.form.DepartmentUpdateForm;
import com.vti.repository.IDepartmentRepository;
import com.vti.specification.DepartmentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private IDepartmentRepository repository;

    @Override
    public Page<Department> findAll(Pageable pageable, DepartmentFilterForm form){
        Specification<Department> spec = DepartmentSpecification.buildSpec(form);
        return repository.findAll(spec, pageable);
    }

    @Override
    public ResponseEntity<Department> findById(int id){
        Department department = repository.findById(id).orElse(new Department());
        //fdf
        return new ResponseEntity<>(department, HttpStatus.BAD_REQUEST);
    }

    @Override
    @Transactional // đồng bộ thêm thêm tất, fail là bỏ tất
    public void create(DepartmentCreateForm form){
        Department department = mapper.map(form, Department.class);
        List<Account> accounts = department.getAccounts();
        if (accounts != null) {
            for (Account account: accounts){
                account.setDepartment(department);
            }
        }
        repository.save(department);
    }

    @Override
    public void update(DepartmentUpdateForm form){
        Department department = mapper.map(form, Department.class);
        repository.save(department);
    }

    @Override
    public void deleteById(int id){
        repository.deleteById(id);
    }

    @Override
    public void deleteAllById(List<Integer> ids) {
        repository.deleteAllById(ids);
    }
}
