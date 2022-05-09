package com.example.service.impl;

import com.example.utils.ExcelUtil;
import com.example.utils.Result;
import com.example.vo.BookVo;
import com.example.mapper.BookMapper;
import com.example.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Lombok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjh
 * @since 2021-10-15
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, BookVo> implements BookService {
          @Autowired
          BookMapper bookMapper;
          @Autowired
          BookService bookService;

    @Override
    public boolean saveOrUpdateBatch(Collection<BookVo> entityList) {
        return super.saveOrUpdateBatch(entityList);
    }


    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        List<Object[]> list = ExcelUtil.importExcel(fileName,file);

        for(int i = 0;i<list.size();i++){
            Object[] tmp_object = list.get(i);
            if(tmp_object[0]==null)
            {
                throw new Exception("第"+(i+1)+"行，名字不能为空");
            }
            if(tmp_object[1]==null)
            {
                throw new Exception("第"+(i+1)+"行，申报单位不能为空");
            }
            if(tmp_object[2]==null)
            {
                throw new Exception("第"+(i+1)+"行，作者至少有一个");
            }
            if(tmp_object[5]==null)
            {
                throw new Exception("第"+(i+1)+"省份不能为空");
            }

            if(tmp_object[6]==null)
            {
                throw new Exception("第"+(i+1)+"申报种类 不能为空");
            }

            if(tmp_object[7]==null)
            {
                throw new Exception("第"+(i+1)+"册数不能为空");
            }

            if(tmp_object[8]==null)
            {
                throw new Exception("第"+(i+1)+"语言不能为空");
            }
            if(tmp_object[9]==null)
            {
                throw new Exception("第"+(i+1)+"面向本科生/硕士生，不能为空");
            }
            if(tmp_object[10]==null)
            {
                throw new Exception("第"+(i+1)+"专业不能为空");
            }
            if(tmp_object[11]==null)
            {
                throw new Exception("第"+(i+1)+"出版社不能为空");
            }
            if(tmp_object[12]==null)
            {
                throw new Exception("第"+(i+1)+"指明教材形式");
            }

            BookVo bookVo = new BookVo();
            String name = tmp_object[0]+"";
            String unit = tmp_object[1]+"";
            String author_major1 = tmp_object[2]+"";
            String authorUnit = tmp_object[3]+"";
            String author = tmp_object[4]+"";
            String province = tmp_object[5]+"";
            String type = tmp_object[6]+"";
            String copies = tmp_object[7]+"";
            String language = tmp_object[8]+"";
            String student = tmp_object[9]+"";
            String major = tmp_object[10]+"";
            String publisher = tmp_object[11]+"";
            String version = tmp_object[12]+"";


          bookVo.setName(name);
          bookVo.setUnit(unit);
          bookVo.setAuthor_major1(author_major1);
          bookVo.setAuthorUnit(authorUnit);
          bookVo.setAuthor(author);
          bookVo.setProvince(province);
          bookVo.setType(type);
          bookVo.setCopies(copies);
          bookVo.setLanguage(language);
          bookVo.setStudent(student);
          bookVo.setMajor(major);
          bookVo.setPublisher(publisher);
          bookVo.setVersion(version);


          try{
              BookVo tmpVo =bookMapper.selectByName(name,author_major1,unit);
              if(tmpVo!=null){
                  bookMapper.deleteById(tmpVo);
              }

              bookMapper.insert(bookVo);
          }catch (Exception e){
              throw new Exception("导入失败，联系管理员，错误原因："+e);
          }






        }




         return false;
    }

    @Override
    public List<BookVo> fuzzySeach(String name) {
        try {
            name +="%";
            return     bookMapper.fuzzySearch(name);
        }
        catch (Exception e){
            System.out.println("课程模糊查询发生错误"+e);
            return null;
        }
    }

    @Override
    public void export(HttpServletResponse response,String fileName) throws Exception {

        List<List<Object>> list;
        try {
            List<BookVo> bookList = bookService.list();//查出数据库数据
            list    = ExcelUtil.voToList(bookList);
        } catch ( Exception e) {
            throw new Exception("解析教材时，发生错误"+e);
        }

        List<String> cellName = new ArrayList<>();
        cellName.add("*教材名称");
        cellName.add("*申报单位(必填) ");
        cellName.add("*第一作者(必填)");
        cellName.add("第一作者所在单位");
        cellName.add("主要作者");
        cellName.add("*省份编码");
        cellName.add("*申报类型");
        cellName.add("*册数");
        cellName.add("*语言");
        cellName.add("*面向对象");
        cellName.add("*专业名称");
        cellName.add("*出版社");
        cellName.add("*载体形式");
        cellName.add("领域");
        cellName.add("系统推荐领域");
        cellName.add("组号");
        try{   ExcelUtil.uploadExcelAboutUser(response,fileName
                ,cellName,list);
        }
        catch (Exception e){
            throw  new Exception("导出发生异常"+e);
        }



    }
}
