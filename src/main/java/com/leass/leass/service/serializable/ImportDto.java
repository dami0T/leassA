package com.leass.leass.service.serializable;

import com.leass.leass.service.agreement.AgreementDto;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

public class ImportDto implements Serializable {

    private Locale locale;

    private AgreementDto agreementDto;

    private Long userId;

    private Boolean saveEntity;

    private Integer sheetIndex;

    private Map<String, Integer> mappingFieldsMap;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public AgreementDto getAgreementDto() {
        return agreementDto;
    }

    public void setAgreementDto(AgreementDto agreementDto) {
        this.agreementDto = agreementDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getSaveEntity() {
        return saveEntity;
    }

    public void setSaveEntity(Boolean saveEntity) {
        this.saveEntity = saveEntity;
    }

    public Integer getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public Map<String, Integer> getMappingFieldsMap() {
        return mappingFieldsMap;
    }

    public void setMappingFieldsMap(Map<String, Integer> mappingFieldsMap) {
        this.mappingFieldsMap = mappingFieldsMap;
    }
}
