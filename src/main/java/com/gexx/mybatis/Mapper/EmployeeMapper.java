package com.gexx.mybatis.Mapper;


import com.gexx.mybatis.entity.Employee;

public interface EmployeeMapper {

    //    @Select("select * from gexx_employee where id = #{id}")
    public Employee getEmployById(Integer id);

    public void addEmployee(Employee employee);

    public void updateEmployee(Employee employee);

    public void delete(Integer id);

}
