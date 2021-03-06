package com.huawei.vcenterpluginui.services;

import com.huawei.vcenterpluginui.constant.SqlFileConstant;
import com.huawei.vcenterpluginui.dao.SystemDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hyuan on 2017/5/10.
 */
public class SystemServiceImpl implements SystemService {

    private static final Log LOGGER = LogFactory.getLog(SystemServiceImpl.class);

    private SystemDao systemDao;

    private VCenterInfoService vCenterInfoService;

    @Override
    public void initDB() {
        try {
            systemDao.checkExistAndCreateTable(SqlFileConstant.HW_ESIGHT_HOST, SqlFileConstant.HW_ESIGHT_HOST_SQL);
            systemDao.checkExistAndCreateTable(SqlFileConstant.HW_ESIGHT_TASK, SqlFileConstant.HW_ESIGHT_TASK_SQL);
            systemDao.checkExistAndCreateTable(SqlFileConstant.HW_TASK_RESOURCE, SqlFileConstant.HW_TASK_RESOURCE_SQL);
            systemDao.checkExistAndCreateTable(SqlFileConstant.HW_ESIGHT_HA_SERVER, SqlFileConstant
                    .HW_ESIGHT_HA_SERVER_SQL);
            systemDao.checkExistAndCreateTable(SqlFileConstant.HW_VCENTER_INFO, SqlFileConstant.HW_VCENTER_INFO_SQL);
            systemDao.checkExistAndCreateTable(SqlFileConstant.HW_SERVER_DEVICE_DETAIL, SqlFileConstant
                    .HW_SERVER_DEVICE_DETAIL_SQL);
            systemDao.checkExistAndCreateTable(SqlFileConstant.HW_ALARM_DEFINITION, SqlFileConstant
                .HW_ALARM_DEFINITION_SQL);

            systemDao.checkExistTableColumnAnd(SqlFileConstant.HW_ESIGHT_HOST,
              SqlFileConstant.HW_ESIGHT_HOST_SYSTEM_ID, SqlFileConstant.HW_ESIGHT_HOST_ALTER_SQL);
            systemDao.checkExistTableColumnAnd(SqlFileConstant.HW_VCENTER_INFO,
                SqlFileConstant.HW_VCENTER_INFO_STATE, SqlFileConstant.HW_VCENTER_INFO_STATE_ALTER_SQL);
            systemDao.checkExistTableColumnAnd(SqlFileConstant.HW_ESIGHT_HA_SERVER,
                SqlFileConstant.COLUMN_ESIGHT_SERVER_PARENT_DN, SqlFileConstant.COLUMN_ESIGHT_SERVER_PARENT_DN_SQL);

            // 20180823: add PUSH_EVENT(HW_VCENTER_INFO) column
            systemDao.checkExistTableColumnAnd(SqlFileConstant.HW_VCENTER_INFO,
                SqlFileConstant.HW_VCENTER_INFO_PUSHEVENT, SqlFileConstant.HW_VCENTER_INFO_PUSHEVENT_ALTER_SQL);
            systemDao.checkExistTableColumnAnd(SqlFileConstant.HW_VCENTER_INFO,
                SqlFileConstant.HW_VCENTER_INFO_PUSHEVENTLEVEL, SqlFileConstant.HW_VCENTER_INFO_PUSHEVENTLEVEL_ALTER_SQL);

            // 20180825: add alarm definition columns
            systemDao.checkExistTableColumnAnd(SqlFileConstant.HW_ALARM_DEFINITION,
                SqlFileConstant.HW_ALARM_DEFINITION_SEVERITY,
                SqlFileConstant.HW_ALARM_DEFINITION_SEVERITY_ALTER_SQL);
            systemDao.checkExistTableColumnAnd(SqlFileConstant.HW_ALARM_DEFINITION,
                SqlFileConstant.HW_ALARM_DEFINITION_EVENTTYPE,
                SqlFileConstant.HW_ALARM_DEFINITION_EVENTTYPE_ALTER_SQL);
            systemDao.checkExistTableColumnAnd(SqlFileConstant.HW_ALARM_DEFINITION,
                SqlFileConstant.HW_ALARM_DEFINITION_DESCRIPTION,
                SqlFileConstant.HW_ALARM_DEFINITION_DESCRIPTION_ALTER_SQL);

            // 20180906: add esight columns for HA-provider state and Alarm-definition state
            systemDao.checkExistTableColumnAnd(SqlFileConstant.HW_ESIGHT_HOST,
                SqlFileConstant.HW_ESIGHT_HOST_HA_PROVIDER,
                SqlFileConstant.HW_ESIGHT_HOST_HA_PROVIDER_ALTER_SQL);
            systemDao.checkExistTableColumnAnd(SqlFileConstant.HW_ESIGHT_HOST,
                SqlFileConstant.HW_ESIGHT_HOST_ALARM_DEFINITION,
                SqlFileConstant.HW_ESIGHT_HOST_ALARM_DEFINITION_ALTER_SQL);

            // 20181112: esight server certificate thumbprints
            systemDao.checkExistAndCreateTable(SqlFileConstant.HW_ESIGHT_THUMBPRINT,
                SqlFileConstant.HW_ESIGHT_THUMBPRINT_SQL);

            LOGGER.info("Removing HA data...");
            vCenterInfoService.deleteHASyncAndDeviceData();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void setSystemDao(SystemDao systemDao) {
        this.systemDao = systemDao;
    }

    public void setvCenterInfoService(VCenterInfoService vCenterInfoService) {
        this.vCenterInfoService = vCenterInfoService;
    }
}
