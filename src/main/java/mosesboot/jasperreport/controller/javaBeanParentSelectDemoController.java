package mosesboot.jasperreport.controller;

import mosesboot.jasperreport.util.DocType;
import mosesboot.user.entity.Depart;
import mosesboot.user.entity.Person;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * javaben数据源
 * 父子类模板下载demo  无table模式
 *  xls文件下载
 * @author wangwei
 * @version 1.0
 * @date 2020/12/14 17:46
 */
@Controller
public class javaBeanParentSelectDemoController {

    @GetMapping("/jasper/javabean/departList")
    public  void departList( HttpServletRequest request,
                             HttpServletResponse response) throws  Exception{
        //封装数据源
        List<Depart> departList = new ArrayList<Depart>();
        List<Person> personList = null;
        Depart depart = new Depart("测试一", "开发部");
        personList = new ArrayList<Person>();
        personList.add(new Person("小博", "22", "男", "123456"));
        personList.add(new Person("张三", "21", "男", "321456"));
        personList.add(new Person("李四", "24", "女", "654321"));
        personList.add(new Person("王五", "23", "男", "123456"));
        depart.setPersonList(personList);
        departList.add(depart);
        depart = new Depart("测试二", "研发部");
        personList = new ArrayList<Person>();
        personList.add(new Person("小博", "22", "男", "123456"));
        personList.add(new Person("李四", "24", "女", "654321"));
        depart.setPersonList(personList);
        departList.add(depart);

        ClassPathResource resource = new ClassPathResource("jaspers/Main_Department_List.jasper");
        String path = this.getClass().getClassLoader().getResource("jaspers/Main_Department_List.jasper").getPath();
        String path1 = this.getClass().getClassLoader().getResource("jaspers/Main_Department_List.jrxml").getPath();
        JasperCompileManager.compileReportToFile(path1,path);

        String path2 = this.getClass().getClassLoader().getResource("jaspers/Sub_Department_Person_List.jasper").getPath();
        String path3 = this.getClass().getClassLoader().getResource("jaspers/Sub_Department_Person_List.jrxml").getPath();
        JasperCompileManager.compileReportToFile(path3,path2);


        InputStream is = resource.getInputStream();
        Map<String, Object> parameters = new HashMap<String, Object>();
        try {
            //设置子报表项目路径
            String root_path = this.getClass().getClassLoader().getResource("jaspers/").getPath();
            parameters.put("SUBREPORT_DIR", root_path);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
            JRDataSource dataSource = new JRBeanCollectionDataSource(departList);
            JasperPrint jasperPrint  = JasperFillManager.fillReport(jasperReport, parameters,dataSource);
            if (null != jasperPrint) {
                FileBufferedOutputStream fbos = new FileBufferedOutputStream();
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fbos);
                exporter
                        .setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                try {
                    exporter.exportReport();
                    fbos.close();
                    if (fbos.size() > 0) {
                        response.setContentLength(fbos.size());
                        DocType docType = DocType.fromTypeName("xls");
                        response.setHeader("Content-Disposition", "attachment; filename=\""
                                + URLEncoder.encode("测试", "UTF-8") + docType.getTypeSuffix() + "\"");
                        ServletOutputStream ouputStream = response
                                .getOutputStream();
                        try {
                            fbos.writeData(ouputStream);
                            fbos.dispose();
                            ouputStream.flush();
                        } finally {
                            if (null != ouputStream) {
                                ouputStream.close();
                            }
                        }
                    }
                } catch (JRException e1) {
                    e1.printStackTrace();
                } finally {
                    if (null != fbos) {
                        fbos.close();
                        fbos.dispose();
                    }
                }
            }

        } catch (JRException e) {
            e.printStackTrace();
        }

    }
}
