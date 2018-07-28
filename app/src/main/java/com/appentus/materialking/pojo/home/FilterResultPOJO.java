package com.appentus.materialking.pojo.home;

import com.google.gson.annotations.SerializedName;

public class FilterResultPOJO {
    @SerializedName("Filters")
    FilterPOJO fileFilterPOJO;

    public FilterPOJO getFileFilterPOJO() {
        return fileFilterPOJO;
    }

    public void setFileFilterPOJO(FilterPOJO fileFilterPOJO) {
        this.fileFilterPOJO = fileFilterPOJO;
    }
}
