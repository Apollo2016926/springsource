package com.gexx.mybatis;

import com.gexx.mybatis.Mapper.EmployeeMapper;
import com.gexx.mybatis.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MybatisTest {
    SqlSession sqlSession = null;

    @Before
    public void init() throws Exception {
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

//            Employee employee = sqlSession.selectOne("com.gexx.mybatis.EmployeeMapper.selectEmployee", 1);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.getEmployById(1);
        System.out.println(employee);
    }

    @Test
    public void testAdd() throws IOException {

        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setEmail("6566@22");
        employee.setGender("1");
        employee.setLastName("是是是");
        mapper.addEmployee(employee);
        sqlSession.commit();
        System.out.println(employee);
    }

    @Test
    public void testUpdate() throws IOException {

        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setId(8);
        employee.setEmail("666666");
        mapper.updateEmployee(employee);
        sqlSession.commit();
        System.out.println(employee);
    }

    @Test
    public void testdelete() throws IOException {

        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        mapper.delete(9);
//        sqlSession.commit();
    }

    @Test
    public void bio() throws Exception {

        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(5555);


        while (true) {
            Socket accept = serverSocket.accept();
            System.out.println("发现客户端");

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    getString(accept);
                }
            });
        }

    }


    public void getString(Socket socket) {
        try {
            System.out.println(Thread.currentThread().getName());
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            while (true) {
                System.out.println("111111");
                inputStream.read(bytes);
                System.out.println(new String(bytes));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
