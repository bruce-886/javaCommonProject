package com.example.projectCommon.Model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class ReadRecord {

    @CsvBindByName(column = "Tbl_updt")
    @CsvBindByPosition(position = 0)
    private String updateTime;

    @CsvBindByName(column = "book_id")
    @CsvBindByPosition(position = 1)
    private String bookId;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "MXRecord{" +
                "updateTime='" + updateTime + '\'' +
                ", bookId='" + bookId + '\'' +
                '}';
    }
}
