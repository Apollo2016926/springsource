package com.gexx.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {
    SqlSession sqlSession = null;

    @Before
    public void init() throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
        System.out.println("-------------------------init--------------------------");

    }

    @After
    public void destory() {
        try {

        } finally {

            sqlSession.close();
        }

        System.out.println("-------------------------destory--------------------------");
    }

    @Test
    public void testMybatis() throws IOException {
        {

            Employee employee = sqlSession.selectOne("com.gexx.mybatis.EmployeeMapper.selectEmployee", 1);
            System.out.println(employee);


        }
    }
}
