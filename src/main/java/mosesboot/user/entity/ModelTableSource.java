package mosesboot.user.entity;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author wangwei
 * @version 1.0
 * @description
 * @date 2020/12/15 10:39
 */
public class ModelTableSource {
    /**
     * 注入table组件的数据源
     */
    private JRBeanCollectionDataSource dataTable;


    public JRBeanCollectionDataSource getDataTable() {
        return dataTable;
    }

    public void setDataTable(JRBeanCollectionDataSource dataTable) {
        this.dataTable = dataTable;
    }
}
