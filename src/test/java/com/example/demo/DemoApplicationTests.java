package com.example.demo;

import com.example.mapper.*;

import com.example.service.BookService;
import com.example.service.CourseService;
import com.example.service.ExpertService;
import com.example.service.ProjectService;

import com.example.utils.ExcelUtil;
import com.example.utils.Keyword;
import com.example.utils.SortUtil;
import com.example.utils.TFIDFAnalyzerUtil;


import com.example.vo.BookVo;
import com.example.vo.CourseVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
@Autowired
UserMapper userMapper;
@Autowired
BookMapper bookMapper;
@Autowired
OldGroupMapper oldGroupMapper;
@Autowired
SchoolMapper schoolMapper;
@Autowired
 ProjectService projectService;
@Autowired
 ExpertService expertService;
@Autowired
 CourseService courseService;
 @Autowired
 ProjectMapper projectMapper;
 @Autowired
 BookService bookService;
 @Autowired
 ExpertMapper expertMapper;
 @Autowired
 CourseMapper courseMapper;



 public DemoApplicationTests() throws Exception {

 }

 @Test
 public  void Test() throws Exception {


  try {

   //System.out.println(expertMapper.selectByarea("马克思", 3, "中央"));
  //projectService.groupBook(3);
    //projectService.UngroupBook();
   //给所有项目分配两个中央专家和地方专家
   //System.out.println(expertMapper.selectByarea(1,"计算机"));
   System.out.println(projectMapper.selectNoExpert("课程"));

  }
  catch (Exception e){
   System.out.println("异常" + e);
  }

 }
//


	}


