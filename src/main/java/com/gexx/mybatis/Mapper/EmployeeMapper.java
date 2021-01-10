package com.gexx.mybatis.Mapper;


import com.gexx.mybatis.entity.Employee;
import org.apache.ibatis.annotations.Select;

public interface EmployeeMapper {

    @Select("select * from gexx_employee where id = #{id}")
    public Employee getEmployById(Integer id);

}
