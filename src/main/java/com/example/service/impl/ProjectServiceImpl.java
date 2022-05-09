package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.*;

import com.example.service.BookService;
import com.example.service.CourseService;
import com.example.service.ExpertService;
import com.example.service.ProjectService;

import com.example.utils.ExcelUtil;
import com.example.utils.Keyword;
import com.example.utils.SortUtil;
import com.example.utils.TFIDFAnalyzerUtil;
import com.example.vo.*;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.*;


@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, ProjectVo> implements ProjectService{
    @Autowired
    BookService bookService;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectService projectService;
    @Autowired
    MajorMapper majorMapper;
    @Autowired
    OldGroupMapper oldGroupMapper ;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseService courseService;
    @Autowired
    ExpertMapper expertMapper;
    @Autowired
    ExpertService expertService;

    /**
     * @param size 每组的大小
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly=false,rollbackFor = Exception.class)
    public boolean groupBook(int size) throws Exception {
        boolean flag = true;
        Integer id = null;
        //首先获取project id 的值 如果为空 则默认从1000开始
        try {
            //由于存的是string 所以取出来先看看是不是为null
            //不是就用id去转为int
            String tmp_id = projectMapper.getMaxId();
            if(tmp_id==null){
                id = 1000;
            }
            else{
                id = Integer.parseInt(tmp_id);
            }
        }
        catch (Exception e){
            System.out.println("获取projectid异常" + e);
        }
        id++;
        //首先获取所有未分组书籍的所有领域 然后根据领域进行分组
        //map形式为 area：“领域名”
        List<Map> areaList = bookMapper.getUnGroupArea();
        //逐个取出领域 对该领域进行一下分组
        for(Map tmpMap:areaList){
            //获取该map
            String tmpArea = (String)tmpMap.get("area");

            if(tmpArea.equals("无")){
                continue;
            }

            //获取该领域所有未分组的书籍
            List<BookVo> bookList = bookMapper.selectNoarea(tmpArea);
            //求出要将上边的书籍分为几个组
            int n = bookList.size()/size;
            //假如取余后发现不等于0且和size的绝对值小于等于1
            // 可以多加一组
            if(     bookList.size()%size!=0&&
                    Math.abs(bookList.size()%size-size)<=1){
                n++;
            }

            for(int i = 0;i<n;i++){
                ProjectVo projectVo = new ProjectVo();
                projectVo.setArea(tmpArea);
                String Major = bookMapper.areaGetMajor(tmpArea);
                //根据该领域下书籍最多的专业命名
                projectVo.setMajor(Major);
                projectVo.setId(id+++"");
                projectVo.setType("教材");
                projectVo.setLocal(0);
                projectVo.setCenter(0);
                projectVo.setFlag("0");
                try {
                    projectMapper.insert(projectVo);
                }
                catch (Exception e){
                    flag = false;
                    throw new Exception("给图书分组时，发生项目" +
                            "分组错误："+e);
                }

                int tmp = size;

                //在给图书分组时候
                //由于有可能会多分一组的情况发生，所以j不应该是小于size
                //因为有可能最后一组大小不到size，则应该是小于size
                //和booklist.size的较小值
                if(tmp>bookList.size()){
                    tmp = bookList.size();
                }

                for(int j = 0;j<tmp;j++){
                    BookVo bookVo =  bookList.get(0);
                    bookList.remove(bookVo);
                    //前边id++了 这里要-1
                    bookVo.setFlag(id-1+"");
                    try {
                        bookService.saveOrUpdate(bookVo);
                    }
                    catch (Exception e){
                        flag = false;
                        throw new Exception("给教材分组时，发生教材保存到" +
                                "分组错误："+e);
                    }
                }

            }


        }

        return flag;
    }


    /**
     * @param size 每组的大小
     */
    @Override
    @Transactional(readOnly=false,rollbackFor = Exception.class)
    public boolean groupCourse(int size) throws Exception {
        boolean flag = true;
        Integer id = null;
        //首先获取project id 的值 如果为空 则默认从1000开始
        try {
            //由于存的是string 所以取出来先看看是不是为null
            //不是就用id去转为int
            String tmp_id = projectMapper.getMaxId();
            if(tmp_id==null){
                id = 1000;
            }
            else{
                id = Integer.parseInt(tmp_id);
            }
        }
        catch (Exception e){
            System.out.println("获取projectid异常" + e);
        }
        id++;
        //首先获取所有未分组书籍的所有领域 然后根据领域进行分组
        //map形式为 area：“领域名”
        List<Map> areaList = courseMapper.getUnGroupArea();
        //逐个取出领域 对该领域进行一下分组
        for(Map tmpMap:areaList){
            //获取该map
            String tmpArea = (String)tmpMap.get("area");

            if(tmpArea.equals("无")){
                continue;
            }

            //获取该领域所有未分组的书籍
            List<CourseVo> courseVoList = courseMapper.selectNoarea(tmpArea);
            //求出要将上边的书籍分为几个组
            int n = courseVoList.size()/size;
            //假如取余后发现不等于0且和size的绝对值小于等于1
            // 可以多加一组
            if(     courseVoList.size()%size!=0&&
                    Math.abs(courseVoList.size()%size-size)<=1){
                n++;
            }

            for(int i = 0;i<n;i++){
                ProjectVo projectVo = new ProjectVo();
                projectVo.setArea(tmpArea);
                String Major = courseMapper.areaGetMajor(tmpArea);
                //根据该领域下书籍最多的专业命名
                projectVo.setMajor(Major);
                projectVo.setId(id+++"");
                projectVo.setType("课程");
                projectVo.setLocal(0);
                projectVo.setCenter(0);
                projectVo.setFlag("0");
                try {
                    projectMapper.insert(projectVo);
                }
                catch (Exception e){
                    flag = false;
                    throw new Exception("给课程分组时，发生项目" +
                            "分组错误："+e);
                }

                int tmp = size;

                //在给图书分组时候
                //由于有可能会多分一组的情况发生，所以j不应该是小于size
                //因为有可能最后一组大小不到size，则应该是小于size
                //和booklist.size的较小值
                if(tmp>courseVoList.size()){
                    tmp = courseVoList.size();
                }

                for(int j = 0;j<tmp;j++){
                    CourseVo courseVo =  courseVoList.get(0);
                    courseVoList.remove(courseVo);
                    //前边id++了 这里要-1
                    courseVo.setFlag(""+(id-1));
                    try {
                        courseService.saveOrUpdate(courseVo);
                    }
                    catch (Exception e){
                        flag = false;
                        throw new Exception("给课程分组时，发生教材保存到" +
                                "分组错误："+e);
                    }
                }

            }


        }

        return flag;
    }
    /**撤销所有List分组里
     */
    @Override
    public boolean Ungroup(List<String> Ids) throws Exception {
           for(String id :Ids) {
               try {
                 projectMapper.deleteById(id);
                 expertMapper.deleteAllgroup(id);
                 bookMapper.deleteAllIdgroup(id);
                 courseMapper.deleteGroup(id);
               } catch (Exception e) {

                   throw new Exception("删除"+id+"组发生异常" +
                           e);

               }


           }
        return true;
    }


    /**
     * author:文家华 21125259
     * 根据往年的自动给书籍打领域
     */
    @Override
    public boolean groupAreaBook() throws Exception {
        List<BookVo> list = bookMapper.selectAllNoarea();
        for (BookVo bookVo : list) {
            HashMap<String, Integer> map = new HashMap<>();//代表该领域匹配的次数
            String name = bookVo.getName();
            String area = "无";//要打的领域

            String Major = bookVo.getMajor();//获取填写的类


                int topK = Math.min(4, name.length() / 2);//分割提取三个关键字足够了
                //获取名字中的关键字

                name = SortUtil.cutStrKuohao(name);


            System.out.println(name);
                List<Keyword> keywords = TFIDFAnalyzerUtil.analyze(name, topK);

                for (Keyword keyword : keywords) {
                    System.out.print(keyword.getName() + " " + keyword.getTfidfvalue());
                    //根据关键字进行模糊匹配 选出出现次数最多的两个领域进入map
                    System.out.println(SortUtil.cutStr(keyword.getName()));
                    List<Map> tmp_list = oldGroupMapper.fuzzy_search(SortUtil.cutStr(keyword.getName()), "");
                    System.out.println(tmp_list);
                    if (tmp_list.size() == 0 ) {
                        continue;//假如这个字段没有往年的数据 就跳过
                    } else {
                        int value = (int)(keyword.getTfidfvalue()*10);//权重
                        int end = Math.min(tmp_list.size(), 2);
                        for (int j = 0; j < end; j++) {
                            Map tmp_map = tmp_list.get(j);
                            //获取领域 和出现次数，让他入map
                            String tmp_area = tmp_map.get("area") + "";
                            //查询该领域对应的专业类 如果正好等于书籍的 那么我们应该加入一些权重，认为该领域符合
                            String tmp_Major = tmp_map.get("major")+"";
                            if(tmp_Major.equals(bookVo.getMajor())){
                                value += 100;//加权
                            }
                            //获取该领域出现的次数
                            Integer tmp_count = Integer.parseInt(String.valueOf("" + tmp_map.get("count")));
                            map.put(tmp_area, (map.getOrDefault(tmp_area, 0) + tmp_count*value));
                        }//for end
                    }//else end
                }
                //得到了一个推荐的领域
                if (map.size() > 0) {
                    area = "" + SortUtil.getMaxValue(map);
                } //获取出现次数最多的


       try {
           OldGroupVo oldGroupVo = null;
           name = SortUtil.cutStrKuohao(SortUtil.cutStrKuohao(bookVo.getName()));//括号一般都没用，去掉
           oldGroupVo = oldGroupMapper.selectByName(name);
           if (oldGroupVo != null) {
               area = oldGroupVo.getArea();
           }
       }
       catch (Exception e){

       }

           bookVo.setRarea(area);
                try {
                    bookService.saveOrUpdate(bookVo);
                } catch (Exception e) {
                    throw new Exception("给教材自动分配领域发生了错误：" + e);
                }



        }

        return false;
    }


        /**
         * author
         * 根据往年的自动给课程打领域
         */
        @Override
        public boolean groupAreaCourse () throws Exception {
            List<CourseVo> courseVoList = courseMapper.selectAllNoarea();
            for (CourseVo courseVo : courseVoList) {
                HashMap<String, Integer> map = new HashMap<>();//代表该领域匹配的次数
                String name = courseVo.getName();
                String area = "无";//要打的领域


                int topK = Math.min(4, name.length() / 2);//分割提取三个关键字足够了
                //获取名字中的关键字

                name = SortUtil.cutStrKuohao(name);


                System.out.println(name);
                List<Keyword> keywords = TFIDFAnalyzerUtil.analyze(name, topK);

                for (Keyword keyword : keywords) {
                    //根据关键字进行模糊匹配 选出出现次数最多的两个领域进入map
                    List<Map> tmp_list = oldGroupMapper.fuzzy_search(SortUtil.cutStr(keyword.getName()), "");
                    if (tmp_list.size() == 0) {
                        continue;//假如这个字段没有往年的数据 就跳过
                    }
                    else {
                        int value =(int)(keyword.getTfidfvalue()*10);//权
                        int end = Math.min(tmp_list.size(), 2);
                        for (int j = 0; j < end; j++) {

                            Map tmp_map = tmp_list.get(j);
                            //获取领域 和出现次数，让他入map
                            String tmp_area = tmp_map.get("area") + "";
                            //获取领域对应的专业
                            String tmp_Major = tmp_map.get("major")+"";
                            if(tmp_Major.equals(courseVo.getMajorClass())){
                                value+=500;
                            }
                            //统计领域出现次数
                            Integer tmp_count = Integer.parseInt(String.valueOf("" + tmp_map.get("count")));
                            map.put(tmp_area, (map.getOrDefault(tmp_area, 0) + tmp_count*value));
                        }//for end
                    }//else end
                }
                //得到了一个推荐的领域
                if (map.size() > 0) {
                    area = "" + SortUtil.getMaxValue(map);
                } //获取出现次数最多的


                //假如之前这个项目存在 那肯定认为往年的是对的
                try {
                    OldGroupVo oldGroupVo = null;
                    name = SortUtil.cutStrKuohao(SortUtil.cutStrKuohao(courseVo.getName()));//括号一般都没用，去掉
                    oldGroupVo = oldGroupMapper.selectByName(name);//type写教材、课程 但是感觉不需要
                    if (oldGroupVo != null || !oldGroupVo.equals("无")) {
                        area = oldGroupVo.getArea();
                    }
                }
                catch (Exception e){

                }


                courseVo.setRarea(area);
                try {
                    courseService.saveOrUpdate(courseVo);
                } catch (Exception e) {
                    throw new Exception("给课程自动分配领域发生了错误：" + e);
                }


            }

            return false;
        }



    @Override
    @Transactional(readOnly=false,rollbackFor = Exception.class)
    public boolean allGroupExpert(int center,int local)throws  Exception{
        List<ProjectVo> list;
         try {
             //获取所有没有分配完毕的项目集合
             list = projectMapper.selectNoExpert("");
             List<Integer> test = new ArrayList<>();
             Integer[] t = (Integer[])test.toArray();
         }
         catch (Exception e){

            throw new Exception("获取所有未分配专家项目发送错误"+e);

         }

             try {
                 for(ProjectVo projectVo:list){
                     //假如需要回避
                     if(!projectVo.getAvoid().equals("否")){
                         //先领域 后专业
                         projectService.GroupExpertAvoid(projectVo.getId(),
                                 projectVo.getNeedCenter(),projectVo.getNeedLocal());
                         projectService.MajorGroupExpertAovid(projectVo.getId(),
                                 projectVo.getNeedCenter(),projectVo.getNeedLocal());

                     }
                     //不需要回避
                     else{

                     }
                 }

            }
            catch (Exception e){
                throw new Exception("项目分组专家分配时发生错误"+e);
            }


return false;

    }









    /** 给一个项目按照领域分配专家
     */
    @Override
    @Transactional(readOnly=false,rollbackFor = Exception.class)
    public boolean GroupExpert(String id, int center, int local) throws Exception {
        String type = "中央高校";//默认为中央高校查询
        //若不需要限定中央或者地方 就查询所有的 并且把local设置为0 center设置为Ids.size
        if(center==-1||local==-1){
            type = "";
        }
          ProjectVo projectVo;
          try {
              projectVo = projectMapper.selectById(id);
          } catch (Exception e) {
              throw new Exception("给项目" + id + "组分配专家发生错误，数据库里或许没有此项目组" + e);
          }

          //这里的major指的就是 领域 但是一个项目会有多个领域
          String tmpArea = projectVo.getArea();

          //分割一下 这样出来之后变成了 xx类-xx、xx、 所以等下还要继续以、分割
          List<String> areas = SortUtil.toList1(tmpArea);
          for (String area : areas) {
              //字段小于2其实没什么意义了 可能是一些垃圾字符串 ，xx类应该优先级比较低  先按领域
              if (area.length() < 2) continue;
              //这里是分割成了具体的
              List<String> litAreas = SortUtil.toList2(area);
              // System.out.println("area"+area);
              //首先通过具体的去匹配专家领域
              for (String litArea : litAreas) {
                  if (projectVo.getCenter() == center &&
                          projectVo.getLocal() == local) {
                      projectVo.setFlag("1");
                      try {
                          projectService.saveOrUpdate(projectVo);
                      } catch (Exception e) {
                          throw new Exception("给项目设置 专家后，保存项目时发生错误" + e);
                      }
                      return true;
                  }
                  //字段小于2其实没什么意义了 可能是一些垃圾字符串
                  if (litArea.charAt(litArea.length() - 1) == '类' || litArea.length() < 2) continue;
                  //当前有几个中央专家

                  int CcurNum = projectVo.getCenter();
                  //当前有几个地方专家
                  int LcurNum = projectVo.getLocal();

                  List<ExpertVo> Clist = expertMapper.selectByarea(litArea, center - CcurNum, type);
                  List<ExpertVo> Llist = expertMapper.selectLocalByArea(litArea, local - LcurNum);

                  //又添加了几个中央专家
                  projectVo.setCenter(Clist.size() + CcurNum);
                  //又添加了几个地方专家
                  projectVo.setLocal(Llist.size() + LcurNum);
                  try {
                      projectService.saveOrUpdate(projectVo);
                  } catch (Exception e) {
                      throw new Exception("给项目设置 专家后，保存项目时发生错误" + e);
                  }
                  for (ExpertVo expertVo : Clist) {
                      expertVo.setFlag(id);
                      try {
                          expertService.saveOrUpdate(expertVo);
                      } catch (Exception e) {
                          throw new Exception("给专家设置分组字段发生错误" + e);
                      }

                  }

                  for (ExpertVo expertVo : Llist) {
                      expertVo.setFlag(id);
                      try {
                          expertService.saveOrUpdate(expertVo);
                      } catch (Exception e) {
                          throw new Exception("给专家设置分组字段发生错误" + e);
                      }

                  }

              }

          }//根据领域分配专家


        return true;
    }



    /**
     * 根据专业大类分配专家
     * 这里需要注意的是 专业大类应该按照领域的大类去划分
     * 因为书籍的类是用户自己填写的类
     */
    @Override
    @Transactional(readOnly=false,rollbackFor = Exception.class)
    public boolean MajorGroupExpert(String id, int center, int local) throws Exception {
        String type = "中央高校";//默认为中央高校查询
        //若不需要限定中央或者地方 就查询所有的 并且把local设置为0 center设置为Ids.size
        if(center==-1||local==-1){
            type = "";
        }
           ProjectVo projectVo;
           try {
               projectVo = projectMapper.selectById(id);
           } catch (Exception e) {
               throw new Exception("给项目" + id + "组分配专家发生错误，数据库里或许没有此项目组" + e);
           }


           //tmpArea 领域, 但是一个项目会有多个领域
           String tmpArea = projectVo.getArea();
           // 分割一下 分割成 xx类-xx、xx 这种 还要分割
           List<String> majors = SortUtil.toList1(tmpArea);
           for (String major : majors) {
               //截断后边的 只保留大类
               major = SortUtil.toList3(major);
               if (major == null || major.equals(null)) {
                   continue;
               }
               //有几个中央专家
               int CcurNum = projectVo.getCenter();
               //当前有几个地方专家
               int LcurNum = projectVo.getLocal();

               List<ExpertVo> Clist = expertMapper.selectBymajor(major, center - CcurNum, type);
               List<ExpertVo> Llist = expertMapper.selectLocalBymajor(major, local - LcurNum);

               //又添加了几个中央专家
               projectVo.setCenter(Clist.size() + CcurNum);
               //又添加了几个地方专家
               projectVo.setLocal(Llist.size() + LcurNum);
               try {
                   projectService.saveOrUpdate(projectVo);
               } catch (Exception e) {
                   throw new Exception("给项目设置 专家后，保存项目时发生错误" + e);
               }
               for (ExpertVo expertVo : Clist) {
                   expertVo.setFlag(id);
                   try {
                       expertService.saveOrUpdate(expertVo);
                   } catch (Exception e) {
                       throw new Exception("给专家设置分组字段发生错误" + e);
                   }

               }

               for (ExpertVo expertVo : Llist) {
                   expertVo.setFlag(id);
                   try {
                       expertService.saveOrUpdate(expertVo);
                   } catch (Exception e) {
                       throw new Exception("给专家设置分组字段发生错误" + e);
                   }

               }
           }//for 专业大类结束


        return true;
    }

    /**按照专业大类分配专家 设置回避
     * @param center
     * @param local
     * @return
     * @throws Exception
     */
    @Override
    public boolean MajorGroupExpertAovid(String id, int center, int local) throws Exception {
        String type = "中央高校";//默认为中央高校查询
        //若不需要限定中央或者地方 就查询所有的
        //这里的type代表的是第一个查询的 如果我们不需要限制中央人数
        //则直接把第一个查询者中央的改为查询所有的
        //后边那个就不要查le
        if(center==-1||local==-1){
            type = "";
            center = local;
            local = 0;
        }

            ProjectVo projectVo;
            try {
                projectVo = projectMapper.selectById(id);
            } catch (Exception e) {
                throw new Exception("给项目" + id + "组分配专家发生错误，数据库里或许没有此项目组" + e);
            }


            //tmpArea 领域, 但是一个项目会有多个领域
            String tmpArea = projectVo.getArea();
            // 分割一下 分割成 xx类-xx、xx 这种 还要分割
            List<String> majors = SortUtil.toList1(tmpArea);
            for (String major : majors) {
                //截断后边的 只保留大类
                major = SortUtil.toList3(major);
                if (major == null || major.equals(null)) {
                    continue;
                }
                //有几个中央专家
                int CcurNum = projectVo.getCenter();
                //当前有几个地方专家
                int LcurNum = projectVo.getLocal();

                //当前有几个地方专家
                int expertNum = projectVo.getExpert();
                // 当前有几个专家


                List<ExpertVo> Clist = expertMapper.selectBymajorAvoid(major, center - CcurNum, type,id);
                List<ExpertVo> Llist = expertMapper.selectLocalBymajorAvoid(major, local - LcurNum,id);

                //又添加了几个中央专家
                projectVo.setCenter(Clist.size() + CcurNum);
                //又添加了几个地方专家
                projectVo.setLocal(Llist.size() + LcurNum);
                //总共添加了几个
                projectVo.setExpert(expertNum+Clist.size()+Llist.size());
                try {
                    projectService.saveOrUpdate(projectVo);
                } catch (Exception e) {
                    throw new Exception("给项目设置 专家后，保存项目时发生错误" + e);
                }
                for (ExpertVo expertVo : Clist) {
                    expertVo.setFlag(id);
                    try {
                        expertService.saveOrUpdate(expertVo);
                    } catch (Exception e) {
                        throw new Exception("给专家设置分组字段发生错误" + e);
                    }

                }

                for (ExpertVo expertVo : Llist) {
                    expertVo.setFlag(id);
                    try {
                        expertService.saveOrUpdate(expertVo);
                    } catch (Exception e) {
                        throw new Exception("给专家设置分组字段发生错误" + e);
                    }

                }
            }//for 专业大类结束

        return true;
    }

    @Override
    public List listAll()  throws  Exception{
        List res = new ArrayList();
        List<ProjectVo> list;
        try {
           list  = projectService.list(); //先获取所有的项目id
        }
        catch (Exception e){
            throw new Exception("在获取所有项目的阶段发生错误");
        }
        for(ProjectVo projectVo : list){
            List tmpList = new ArrayList();
            List<ExpertVo> expertVoList;
            List<CourseVo> courseVoList;
            List<BookVo> bookVoList;
            try {
                String id = projectVo.getId(); //获取组号
                expertVoList  = expertMapper.listByFlag(id);
                courseVoList = courseMapper.listByIdAll(id);
                bookVoList = bookMapper.selectAllIdgroup(id);

            }
            catch (Exception e){
                throw new Exception("查询组号为"+projectVo.getId()+"时发生错误"+e);
            }

            tmpList.add(projectVo);
            tmpList.add(expertVoList);
            tmpList.add(courseVoList);
            tmpList.add(bookVoList);
            res.add(tmpList);
        }

        return res;
    }



    /**按照领域分类  只不过这里设置回避
     * @param center
     * @param local
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly=false,rollbackFor = Exception.class)
    public boolean GroupExpertAvoid(String id, int center, int local) throws Exception {
        String type = "中央高校";//默认为中央高校查询
        //若不需要限定中央或者地方 就查询所有的 并且把local设置为0 center设置为Ids.size
        if(center==-1||local==-1){
            type = "";
            center = local;
            local = 0;
        }
         ProjectVo projectVo;
         try {
             projectVo = projectMapper.selectById(id);
         } catch (Exception e) {
             throw new Exception("给项目" + id + "组分配专家发生错误，数据库里或许没有此项目组" + e);
         }

         //这里的major指的就是 领域 但是一个项目会有多个领域
         String tmpArea = projectVo.getArea();

         // 这里的major就是专业类 大类
         String major = projectVo.getMajor();
         //分割一下 这样出来之后变成了 xx类-xx、xx、 所以等下还要继续以、分割
         List<String> areas = SortUtil.toList1(tmpArea);
         for (String area : areas) {
             //字段小于2其实没什么意义了 可能是一些垃圾字符串 ，xx类应该优先级比较低  先按领域
             if (area.length() < 2) continue;
             //这里是分割成了具体的
             List<String> litAreas = SortUtil.toList2(area);

             //首先通过具体的去匹配专家领域
             for (String litArea : litAreas) {

                 //字段小于2其实没什么意义了 可能是一些垃圾字符串
                 if (litArea.charAt(litArea.length() - 1) == '类' || litArea.length() < 2) continue;
                 //当前有几个中央专家

                 int CcurNum = projectVo.getCenter();
                 //当前有几个地方专家
                 int LcurNum = projectVo.getLocal();
                 //当前有几个专家
                 int expert = projectVo.getExpert();
                 List<ExpertVo> Clist = expertMapper.selectByAreaAvoid(litArea, center - CcurNum, type, projectVo.getId());
                 List<ExpertVo> Llist = expertMapper.selectLocalByAreaAvoid(litArea, local - LcurNum, projectVo.getId());

                 //又添加了几个中央专家
                 projectVo.setCenter(Clist.size() + CcurNum);
                 //又添加了几个地方专家
                 projectVo.setLocal(Llist.size() + LcurNum);
                 //一共添加了几个专家
                 projectVo.setExpert(expert+Clist.size()+Llist.size());
                 try {
                     projectService.saveOrUpdate(projectVo);
                 } catch (Exception e) {
                     throw new Exception("给项目设置 专家后，保存项目时发生错误" + e);
                 }
                 for (ExpertVo expertVo : Clist) {
                     expertVo.setFlag(id);
                     try {
                         expertService.saveOrUpdate(expertVo);
                     } catch (Exception e) {
                         throw new Exception("给专家设置分组字段发生错误" + e);
                     }

                 }

                 for (ExpertVo expertVo : Llist) {
                     expertVo.setFlag(id);
                     try {
                         expertService.saveOrUpdate(expertVo);
                     } catch (Exception e) {
                         throw new Exception("给专家设置分组字段发生错误" + e);
                     }

                 }



         }//根据领域分配专家
     }


        return true;
    }

    @Override
    public boolean setAvoid(List<String> Ids) throws Exception {
       for(String id : Ids){
           ProjectVo projectVo;
           try {
               projectVo   = projectMapper.selectById(id);
               if(projectVo == null){
                   throw new Exception("失败，失败原因可能是数据库没有id为"+
                           id+"的分组");
               }
           }
           catch (Exception e){
               throw  new Exception("发生系统错误"+e);
           }
             projectVo.setAvoid("是");
           try {
               projectService.saveOrUpdate(projectVo);
           }
           catch (Exception e){
               throw new Exception("发生系统错误"+e);
           }


       }



        return false;
    }


    @Override
    public boolean resetAvoid(List<String> Ids) throws Exception {
        for(String id : Ids){
            ProjectVo projectVo;
            try {
                projectVo   = projectMapper.selectById(id);
                if(projectVo == null){
                    throw new Exception("失败，失败原因可能是数据库没有id为"+
                            id+"的分组");
                }
            }
            catch (Exception e){
                throw  new Exception("发生系统错误"+e);
            }
            projectVo.setAvoid("否");
            try {
                projectService.saveOrUpdate(projectVo);
            }
            catch (Exception e){
                throw new Exception("发生系统错误"+e);
            }


        }



        return false;
    }

    @Override
    public boolean setExpert(List<String> Ids, int center, int local) throws Exception {
       for(String id :Ids){
           ProjectVo projectVo;
           try {
               projectVo = projectMapper.selectById(id);
               if(projectVo==null){
                   throw new Exception("没有id为"+id+"的项目");
               }
               if(center!=-1){
                   projectVo.setExpert((center+local));
                   projectVo.setCenter(center);
                   projectVo.setLocal(local);
               }
               else{
                   projectVo.setExpert(local);
                   projectVo.setCenter(-1);
                   projectVo.setLocal(-1);
               }
           }
           catch (Exception e){
               throw new Exception("系统内部错误"+e);
           }
       }

        return false;
    }

    /**
     * 导出书籍名:组号
     * @param response http响应
     * @param fileName 下载的文件名
     * @throws Exception
     */
    @Override
    public void exportBook(HttpServletResponse response, String fileName) throws Exception {
        List<PoJoProjectVo> books;
        try {
            books  = bookMapper.export();
     }
     catch (Exception e){
         throw  new Exception("获取书籍分组对应关系发生异常+"+e);
     }
        List<List<Object>> lists = ExcelUtil.voToList(books);
        List<String> cellName = new ArrayList<>();
        cellName.add("标识符(请勿改动)");
        cellName.add("书籍名");
        cellName.add("组号(小于等于0代表未分组)");
        ExcelUtil.uploadExcelAboutUser(response,fileName,
              cellName,lists);

    }

    /**
     * @param response http响应
     * @param fileName 下载的文件名
     * @throws Exception
     */
    @Override
    public void exportCourse(HttpServletResponse response, String fileName) throws Exception {

        List<PoJoProjectVo> courses;
        try {
            courses  = courseMapper.export();
        }
        catch (Exception e){
            throw  new Exception("获取书籍分组对应关系发生异常+"+e);
        }
        List<List<Object>> lists = ExcelUtil.voToList(courses);
        List<String> cellName = new ArrayList<>();
        cellName.add("标识符(请勿改动)");
        cellName.add("课程名");
        cellName.add("组号(小于等于0代表未分组)");
        ExcelUtil.uploadExcelAboutUser(response,fileName,
                cellName,lists);
    }

    @Override
    public void exportExpert(HttpServletResponse response, String fileName) throws Exception {

        List<PoJoProjectVo> experts;
        try {
            experts  = expertMapper.export();
        }
        catch (Exception e){
            throw  new Exception("获取专家分组对应关系发生异常+"+e);
        }
        List<List<Object>> lists = ExcelUtil.voToList(experts);
        List<String> cellName = new ArrayList<>();
        cellName.add("专家手机号");
        cellName.add("专家姓名");
        cellName.add("组号(小于等于0代表未分组)");
        ExcelUtil.uploadExcelAboutUser(response,fileName,
                cellName,lists);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchImportBook(String fileName, MultipartFile file) throws Exception {

        //存储的是每一行,工具类为我们设置好的模板 每行有2列
        List<Object[]> list =   ExcelUtil.importExcel(fileName,file);

        for(Object[] objects:list){

            String id = objects[0]+"";
            String name = objects[1]+"";
            String flag = objects[2]+"";

            ProjectVo projectVo ;
            if(flag.equals("null")){
                //flag默认为0
                flag = "0";
            }
            BookVo bookVo;
            try {
                projectVo = projectMapper.selectById(flag);
                bookVo = bookService.getById(id);
            }
            catch (Exception e){
                throw new Exception("在更新id为"+id+"，书籍名为"+name
                +"的阶段发生错误(请查看数据库中是否有该记录)"+e);
            }
            bookVo.setFlag(flag);
            if(projectVo==null){
                projectVo = new ProjectVo();
                projectVo.setMajor(bookVo.getMajor());
                projectVo.setType(bookVo.getArea());
                projectVo.setId(flag);
            }

            try {
                bookService.saveOrUpdate(bookVo);
            }
            catch (Exception e){
                throw new Exception("在更新id为"+id+"，书籍名为"+name
                        +"的阶段发生错误"+e);
            }

        }

        try{
            projectMapper.Update();
        }catch (Exception E){
            throw  new Exception("在更新书籍项目分组阶段时发生错误"+E);
        }


        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchImportCourse(String fileName, MultipartFile file) throws Exception {

        //存储的是每一行,工具类为我们设置好的模板 每行有2列
        List<Object[]> list =   ExcelUtil.importExcel(fileName,file);
        for(Object[] objects:list){
            String id = objects[0]+"";
            String name = objects[1]+"";
            String flag = objects[2]+"";
            ProjectVo projectVo ;
            if(flag.equals("null")){
                //flag默认为0
                flag = "0";
            }
            CourseVo courseVo;
            try {
                projectVo = projectMapper.selectById(flag);
                courseVo = courseService.getById(id);
            }
            catch (Exception e){
                throw new Exception("在更新id为"+id+"，课程名为"+name
                        +"的阶段发生错误(请查看数据库中是否有该记录)"+e);
            }
            courseVo.setFlag(flag);
            if(projectVo==null){
                projectVo = new ProjectVo();
                projectVo.setMajor(courseVo.getMajorClass());
                projectVo.setType(courseVo.getArea());
                projectVo.setId(flag);
            }

            try {
                courseService.saveOrUpdate(courseVo);
            }
            catch (Exception e){
                throw new Exception("在更新id为"+id+"，课程名为"+name
                        +"的阶段发生错误"+e);
            }

        }

        try{
            projectMapper.Update();
        }catch (Exception E){
            throw  new Exception("在更新课程项目分组阶段时发生错误"+E);
        }


        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchImportExpert(String fileName, MultipartFile file) throws Exception {

        //存储的是每一行,工具类为我们设置好的模板 每行有2列
        List<Object[]> list =   ExcelUtil.importExcel(fileName,file);
        for(Object[] objects:list) {
            String phone = objects[0] + "";
            String name = objects[1] + "";
            String flag = objects[2] + "";
            ProjectVo projectVo;
            if (flag.equals("null")) {
                //flag默认为0
                flag = "0";
            }
            ExpertVo expertVo;

            if (!flag.equals("0") && !flag.equals("-1")) {
                try {
                    projectVo = projectMapper.selectById(flag);
                    expertVo = expertService.getById(phone);
                } catch (Exception e) {
                    throw new Exception("在更新手机为" + phone + "名为" + name
                            + "的专家时， 发生错误(请查看数据库中是否有该记录)" + e);
                }
                expertVo.setFlag(flag);
                if (projectVo == null) {
                    throw new Exception("在更新手机为" + phone + "名为" + name
                            + "的专家时， 发生错误,请查看该专家所属分组是否存在");
                }
                expertVo.setFlag(flag);

                try {
                    expertService.saveOrUpdate(expertVo);
                }
                catch (Exception e){
                    throw new Exception("在更新手机为" + phone + "名为" + name
                            + "的专家时， 发生错误" + e);
                }

            }

        }

        return false;

    }


}
