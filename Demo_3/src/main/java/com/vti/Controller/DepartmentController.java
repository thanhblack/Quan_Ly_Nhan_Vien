package com.vti.Controller;

import com.vti.DTO.AccountDTO;
import com.vti.DTO.DepartmentDTO;
import com.vti.Entity.Department;
import com.vti.Form.CreatingDepartmentForm;
import com.vti.Form.UpdatingDepartmentForm;
import com.vti.Service.IDepartmentService;
import com.vti.filter.DepartmentFilterForm;
import com.vti.validate.IdExists;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/departments")
@Validated
@CrossOrigin("*")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private ModelMapper modelMapper;

//    get all dung page
//    @GetMapping
//    public Page<DepartmentDTO> getAllDepartments(Pageable pageable, DepartmentFilterForm form) {
//        Page<Department> page = departmentService.getAllDepartment(pageable, form);
//        List<DepartmentDTO> departmentDTOS = modelMapper.map(page.getContent(), new TypeToken<List<DepartmentDTO>>() {
//        }.getType());
//        return new PageImpl<>(departmentDTOS, pageable, page.getTotalElements());
//    }

    //    get all dung list
    @GetMapping
    public ResponseEntity<?> getAllDepartments(DepartmentFilterForm form) {
        List<DepartmentDTO> ls = modelMapper.map(departmentService.getAllDepartment(form), new TypeToken<List<DepartmentDTO>>() {
        }.getType());
        return new ResponseEntity<>(ls, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody @Valid CreatingDepartmentForm form) {
        departmentService.createDepartment(form);
        return new ResponseEntity<>("Tao department thanh cong",HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable(name = "id") @IdExists int id, @RequestBody UpdatingDepartmentForm form) {
        form.setId(id);
        departmentService.updateDepartment(form);
        return new ResponseEntity<>("update department thanh cong",HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?>  deleteAccount(@PathVariable(name = "id") @IdExists int id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>("delete department thanh cong",HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable(name = "id") @IdExists int id) {
        return new ResponseEntity<>(modelMapper.map(departmentService.getDepartmentById(id), DepartmentDTO.class), HttpStatus.OK);
    }
}
