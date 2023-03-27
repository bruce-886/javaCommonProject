package com.example.projectCommon.Model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class MXRecord {

    @CsvBindByName(column = "Tbl_updt")
    @CsvBindByPosition(position = 0)
    private String updateTime;

    @CsvBindByName(column = "eqp_id")
    @CsvBindByPosition(position = 1)
    private String eqpId;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEqpId() {
        return eqpId;
    }

    public void setEqpId(String eqpId) {
        this.eqpId = eqpId;
    }

    @Override
    public String toString() {
        return "MXRecord{" +
                "updateTime='" + updateTime + '\'' +
                ", eqpId='" + eqpId + '\'' +
                '}';
    }
}
